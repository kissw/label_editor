package com.me.cica.coco.data.json;

import com.google.gson.annotations.SerializedName;

public class InfoEntry {
    //"year" : int, "version" : str, "description" : str, "contributor" : str, "url" : str, "date_created" : datetime,
    @SerializedName("year")
    private int year;
    @SerializedName("version")
    private String version;
    @SerializedName("description")
    private String description;
    @SerializedName("contributor")
    private String contributor;
    @SerializedName("url")
    private String url;
    @SerializedName("date_created")
    private String dateCreated; // @TODO Date object representation.

    public int getYear() {
        return year;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public String getContributor() {
        return contributor;
    }

    public String getUrl() {
        return url;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return "InfoEntry{" +
                "year=" + year +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", contributor='" + contributor + '\'' +
                ", url='" + url + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
