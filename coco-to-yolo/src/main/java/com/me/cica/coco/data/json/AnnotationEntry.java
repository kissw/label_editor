package com.me.cica.coco.data.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnnotationEntry {
    // "id" : int, "image_id" : int, "category_id" : int, "segmentation" : RLE or [polygon], "area" : float, "bbox" : [x,y,width,height], "iscrowd" : 0 or 1,
    @SerializedName("id")
    private long id;
    @SerializedName("image_id")
    private long imageId;
    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("segmentation")
    private transient List<Float> segmentation; // @TODO Handle segmentation correctly (either RLE or polygon, depending on isCrowd value).
    @SerializedName("area")
    private float area;
    @SerializedName("bbox")
    private List<Float> boundryBox;
    @SerializedName("iscrowd")
    private int isCrowd;

    public long getId() {
        return id;
    }

    public long getImageId() {
        return imageId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public List<Float> getSegmentation() {
        return segmentation;
    }

    public float getArea() {
        return area;
    }

    public List<Float> getBoundryBox() {
        return boundryBox;
    }

    public int getIsCrowd() {
        return isCrowd;
    }

    @Override
    public String toString() {
        return "AnnotationEntry{" +
                "id=" + id +
                ", imageId=" + imageId +
                ", categoryId=" + categoryId +
                ", segmentation=" + segmentation +
                ", area=" + area +
                ", boundryBox=" + boundryBox +
                ", isCrowd=" + isCrowd +
                '}';
    }
}