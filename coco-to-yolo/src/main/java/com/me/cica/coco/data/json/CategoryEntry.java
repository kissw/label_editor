package com.me.cica.coco.data.json;

import com.google.gson.annotations.SerializedName;

public class CategoryEntry {
    // "id" : int, "name" : str, "supercategory" : str,
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("supercategory")
    private String superCategory;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSuperCategory() {
        return superCategory;
    }

    @Override
    public String toString() {
        return "CategoryEntry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", superCategory='" + superCategory + '\'' +
                '}';
    }
}
