package com.me.cica.bbox;

import java.util.Objects;

public final class YoloBBox {
    private final int categoryId;
    private final float xRatio;
    private final float yRatio;
    private final float wRatio;
    private final float hRatio;

    public YoloBBox(int categoryId, float xRatio, float yRatio, float wRatio, float hRatio) {
        this.categoryId = categoryId;
        this.xRatio = xRatio;
        this.yRatio = yRatio;
        this.wRatio = wRatio;
        this.hRatio = hRatio;
    }

    int getCategoryId() {
        return categoryId;
    }

    float getXRatio() {
        return xRatio;
    }

    float getYRatio() {
        return yRatio;
    }

    float getWRatio() {
        return wRatio;
    }

    float getHRatio() {
        return hRatio;
    }

    @Override
    public String toString() {
        return String.valueOf(categoryId) + " " + xRatio + " " + yRatio + " " + wRatio + " " + hRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YoloBBox yoloBBox = (YoloBBox) o;
        return categoryId == yoloBBox.categoryId &&
                Float.compare(yoloBBox.xRatio, xRatio) == 0 &&
                Float.compare(yoloBBox.yRatio, yRatio) == 0 &&
                Float.compare(yoloBBox.wRatio, wRatio) == 0 &&
                Float.compare(yoloBBox.hRatio, hRatio) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, xRatio, yRatio, wRatio, hRatio);
    }
}
