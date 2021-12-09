package com.me.cica.bbox;

/*
    Represents a COCO dataset annotation bounding box.
    COCO uses screen coordinates (i.e., top-left is (0,0)) but YOLO expects cartesian with (x, y) representing centre of bounding box.
    Perform the conversion prior to making coordinates relative to total annotated image width and height.
 */
public final class CocoBBox {
    private final float screenX;
    private final float screenY;
    private final float w;
    private final float h;

    public CocoBBox(float screenX, float screenY, float w, float h) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.w = w;
        this.h = h;
    }

    float getCartesianX() {
        return screenX + (w / 2);
    }

    float getScreenX() {
        return screenX;
    }

    float getCartesianY() {
        return screenY + (h / 2);
    }

    float getScreenY() {
        return screenY;
    }

    float getWidth() {
        return w;
    }

    float getHeight() {
        return h;
    }

    public boolean isValid() {
        return w > 0 && h > 0;
    }

    @Override
    public String toString() {
        return "CocoBBox{" +
                "screenX=" + screenX +
                ", screenY=" + screenY +
                ", w=" + w +
                ", h=" + h +
                '}';
    }
}
