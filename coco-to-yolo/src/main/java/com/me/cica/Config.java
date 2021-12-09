package com.me.cica;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum Config {
    INSTANCE;

    public Path COCO_ANNOTATIONS_FILE_PATH;
    public Path COCO_IMAGES_PATH;
    public Path OUTPUT_DESTINATION_PATH;
    public List<String> INCLUDED_CATEGORY_NAMES;
    public Map<Integer, Integer> INTERNAL_CATEGORY_MAPPING;
    public String INCLUDE_ALL_CATEGORIES_FLAG = "all";

    @Override
    public String toString() {
        return "Config {" +
                "COCO_ANNOTATIONS_FILE_PATH=" + COCO_ANNOTATIONS_FILE_PATH +
                ", COCO_IMAGES_PATH=" + COCO_IMAGES_PATH +
                ", OUTPUT_DESTINATION_PATH=" + OUTPUT_DESTINATION_PATH +
                ", INCLUDED_CATEGORY_NAMES=" + INCLUDED_CATEGORY_NAMES +
                '}';
    }
}
