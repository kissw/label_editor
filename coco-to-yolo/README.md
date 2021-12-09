Creates a Darknet YOLO compatible image list and label files from a COCO dataset annotations file.

Arguments to the application (in order):
- 1. Path to the COCO dataset annotations JSON file.
- 2. Absolute path to the COCO dataset images. This path will be used in the generated image list.
- 3. Comma-separated list of category names to be included in the output. COCO features 80 classes, but you could specify a subset of those. Spaces are disallowed before and after commas.
- 4. Path/directory to which the generated label files and the image list should be output.

It should be run for both the train and val datasets separately. For instance:

```
java -jar cocotoyolo.jar "coco/annotations/instances_train2017.json" "/usr/home/madmax/coco/images/train2017/" "car,truck,bus" "coco/yolo"
java -jar cocotoyolo.jar "coco/annotations/instances_val2017.json" "/usr/home/madmax/coco/images/val2017/" "car,truck,bus" "coco/yolo"
```

A pre-built JAR file can be found here: http://commecica.com/wp-content/uploads/2018/07/cocotoyolo.jar

Note that JDK 8 or higher is necessary to compile the utility and JRE 8 or higher is necessary to execute it.