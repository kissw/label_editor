package com.me.cica.coco.data.json;

import com.google.gson.annotations.SerializedName;

public class ImageEntry {
    //"id" : int, "width" : int, "height" : int, "file_name" : str, "license" : int, "flickr_url" : str, "coco_url" : str, "date_captured" : datetime,
    @SerializedName("id")
    private long id;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;
    @SerializedName("file_name")
    private String fileName;
    @SerializedName("license")
    private int license;
    @SerializedName("flickr_url")
    private String flickrUrl;
    @SerializedName("coco_url")
    private String cocoUrl;
    @SerializedName("date_captured")
    private String dateCaptured;

    public ImageEntry(final long id, final int width, final int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public long getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLicense() {
        return license;
    }

    public String getFlickrUrl() {
        return flickrUrl;
    }

    public String getCocoUrl() {
        return cocoUrl;
    }

    public String getDateCaptured() {
        return dateCaptured;
    }

    @Override
    public String toString() {
        return "ImageEntry{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", fileName='" + fileName + '\'' +
                ", license=" + license +
                ", flickrUrl='" + flickrUrl + '\'' +
                ", cocoUrl='" + cocoUrl + '\'' +
                ", dateCaptured='" + dateCaptured + '\'' +
                '}';
    }
}
