package com.me.cica;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.me.cica.bbox.BBoxConverter;
import com.me.cica.bbox.CocoBBox;
import com.me.cica.bbox.YoloBBox;
import com.me.cica.coco.data.json.AnnotationEntry;
import com.me.cica.coco.data.json.CategoryEntry;
import com.me.cica.coco.data.json.ImageEntry;
import com.me.cica.coco.data.json.RootEntry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Main {

    public static void main(final String [] args) throws IOException {
        try {
            Config.INSTANCE.COCO_ANNOTATIONS_FILE_PATH = Paths.get(args[0]);
            Config.INSTANCE.COCO_IMAGES_PATH = Paths.get(args[1]);
            Config.INSTANCE.INCLUDED_CATEGORY_NAMES = Arrays.stream(args[2].split(",")).map(String::trim).collect(Collectors.toList());
            Config.INSTANCE.OUTPUT_DESTINATION_PATH = Paths.get(args[3]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            // Short on arguments. Print help to user.
            System.err.println("Insufficient arguments provided. See the usage below.");
            System.err.println("Usage: cocotoyolo.jar <COCO annotations file path> <absolute COCO images directory path> <CSV of included category labels> <output directory path>");
            System.exit(1);
        }

        final File resultDestination = Config.INSTANCE.OUTPUT_DESTINATION_PATH.toFile();

        handleExistingLabelDirectory(resultDestination);

        if (! resultDestination.exists()) {
            if (! resultDestination.mkdirs()) {
                System.err.println("Failed to create label destination directory at path '" + resultDestination + "'. Check directory permissions.");
                System.exit(2);
            }
        }

        try (InputStream annotationFileStream = new FileInputStream(Config.INSTANCE.COCO_ANNOTATIONS_FILE_PATH.toFile());
             JsonReader reader = new JsonReader(new InputStreamReader(annotationFileStream, StandardCharsets.UTF_8))) {
            System.out.println("Loading annotations...");
            Gson gson = new GsonBuilder().create();
            RootEntry root = gson.fromJson(reader, RootEntry.class);
            System.out.println(root);

            final Map<Integer, Integer> includedCategoryIds = getIncludedCategoryIds(Config.INSTANCE.INCLUDED_CATEGORY_NAMES, root.getCategories());

            Config.INSTANCE.INTERNAL_CATEGORY_MAPPING = includedCategoryIds;

            // Convert to a HashMap for fast look-up of file paths later.
            final Map<Long, ImageEntry> imageMap = root.getImages().stream().collect(Collectors.toMap(ImageEntry::getId, Function.identity()));

            // Convert COCO dataset annotations to YOLO format and persist them to a label file named after the image.
            root.getAnnotations().stream()
                    .filter(entry -> includedCategoryIds.containsKey(entry.getCategoryId()))
                    .forEach(entry -> writeAnnotationBoundingBox(resultDestination, entry, imageMap));

            final File imageList = Paths.get(resultDestination.getPath(), "image_list.txt").toFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(imageList, false))) {
                final Set<Long> imageIdCache = new TreeSet<>();

                // Persist the absolute path of the images that are categorised by the IDs requested by the user.
                root.getAnnotations().stream()
                        .filter(entry -> includedCategoryIds.containsKey(entry.getCategoryId()))
                        .filter(entry -> ! imageIdCache.contains(entry.getImageId()))
                        .peek(entry -> imageIdCache.add(entry.getImageId()))
                        .forEach(entry -> writeImagePathToFile(imageMap.get(entry.getImageId()).getFileName(), writer));
            }
        }
    }

    /*
        Interact with the user to resolve the case where the output directory already exists.
        The user can opt to either overwrite the existing directory or terminate execution.
     */
    private static void handleExistingLabelDirectory(File resultDestination) throws IOException {
        if (! resultDestination.exists()) {
            return;
        }

        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(resultDestination.toPath())) {
            if (! dirStream.iterator().hasNext()) {
                // Directory contains no files. User input is unnecessary.
                return;
            }
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Converted annotation label directory at given path already exists. Delete it? [y/n]");

            if (scanner.next().toLowerCase().contains("y")) {
                Files.walk(resultDestination.toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } else {
                System.out.println("User opted not to delete existing label directory. Nothing more to do.");
                System.exit(0);
            }
        }
    }

    /*
        Returns the category IDs mapped to the category names input in the annotation file.
        Not to be confused with the internal category ID, which is an arbitrary integer based on the category name position in the
        list provided as argument to the user (Config#INCLUDED_CATEGORY_NAMES).
     */
    private static Map<Integer, Integer> getIncludedCategoryIds(final List<String> categoryLabels, final List<CategoryEntry> categories) {
        final boolean includeAll = categoryLabels.get(0).toLowerCase().equals(Config.INSTANCE.INCLUDE_ALL_CATEGORIES_FLAG);

        final IntStream natural = IntStream.iterate(0, i -> i + 1);

        if (includeAll) {
            final Iterator<CategoryEntry> categoryIterator = categories.iterator();

            return natural.limit(categories.size()).boxed()
                    .collect(Collectors.toMap(n -> categoryIterator.next().getId(), Function.identity()));
        }

        final Iterator<Integer> nIterator = natural.iterator();

        return categories.stream()
                .filter(category -> categoryLabels.contains(category.getName()))
                .map(CategoryEntry::getId)
                .collect(Collectors.toMap(Function.identity(), id -> nIterator.next()));
    }

    private static void writeImagePathToFile(final String imageName, final BufferedWriter writer) {
        try {
            final Path absoluteImagePath = Paths.get(Config.INSTANCE.COCO_IMAGES_PATH.toString(), imageName);
            writer.write(absoluteImagePath.toString());
            writer.write('\n');
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void writeAnnotationBoundingBox(final File resultDestination, final AnnotationEntry entry, final Map<Long, ImageEntry> imageMap) {
        final List<Float> boundryBox = entry.getBoundryBox();
        final CocoBBox annotationBbox = new CocoBBox(boundryBox.get(0), boundryBox.get(1), boundryBox.get(2), boundryBox.get(3));

        final ImageEntry imageEntry = imageMap.get(entry.getImageId());
        final String imageName = imageEntry.getFileName();

        if (! annotationBbox.isValid()) {
            System.err.println("Bounding box annotation '" + entry + "' corresponding to image '" + imageName + " is invalid and has zero area. Omitting.");
            return;
        }

        final Path labelPath = Paths.get(resultDestination.getPath(), imageName.replace(".jpg", ".txt"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(labelPath.toFile(), true))) {
            final int internalCategoryId = Config.INSTANCE.INTERNAL_CATEGORY_MAPPING.get(entry.getCategoryId());
            final YoloBBox yoloBBox = BBoxConverter.INSTANCE.cocoToYolo(imageEntry, internalCategoryId, annotationBbox);

            writer.write(yoloBBox.toString());
            writer.write('\n');
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
