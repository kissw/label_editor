package com.me.cica.coco.data.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootEntry {
    @SerializedName("info")
    private InfoEntry info;
    @SerializedName("images")
    private List<ImageEntry> images;
    @SerializedName("annotations")
    private List<AnnotationEntry> annotations;
    @SerializedName("licenses")
    private List<LicenseEntry> licenses;
    @SerializedName("categories")
    private List<CategoryEntry> categories;

    public InfoEntry getInfo() {
        return info;
    }

    public List<ImageEntry> getImages() {
        return images;
    }

    public List<AnnotationEntry> getAnnotations() {
        return annotations;
    }

    public List<LicenseEntry> getLicenses() {
        return licenses;
    }

    public List<CategoryEntry> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "RootEntry{" +
                "info=" + info +
                ", # images=" + images.size() +
                ", # annotations=" + annotations.size() +
                ", # licenses=" + licenses.size() +
                ", # categories=" + categories.size() +
                '}';
    }
}
