package com.me.cica.bbox;

import com.me.cica.coco.data.json.ImageEntry;

public enum BBoxConverter {
    INSTANCE;

    /*
        YOLO annotations are ratios that are relative to image width and height.
        This method returns a valid annotation YOLO box or an IllegalArgumentException should the resulting contain an invalid ratio.
     */
    public YoloBBox cocoToYolo(final ImageEntry image, final int categoryId, final CocoBBox boundingBox) {
        float xToImageRatio = boundingBox.getCartesianX() / image.getWidth();
        float yToImageRatio = boundingBox.getCartesianY() / image.getHeight();
        float widthToImageRatio = boundingBox.getWidth() / image.getWidth();
        float heightToImageRatio = boundingBox.getHeight() / image.getHeight();

        final YoloBBox yoloBbox = new YoloBBox(categoryId, xToImageRatio, yToImageRatio, widthToImageRatio, heightToImageRatio);

        validateYOLOAnnotation(image.getFileName(), boundingBox, yoloBbox);

        return yoloBbox;
    }

    private void validateYOLOAnnotation(final String imageName, final CocoBBox boundingBox, final YoloBBox yoloBBox) throws IllegalArgumentException {
        if (yoloBBox.getXRatio() <= 0 || yoloBBox.getYRatio() <= 0 || yoloBBox.getWRatio() <= 0 || yoloBBox.getHRatio() <= 0) {
            throw new IllegalStateException("Zero parameter identified for image '" + imageName + "'. Final YOLO bounding box is invalid. COCO: " + boundingBox + " YOLO: " + yoloBBox);
        }
    }
}
