package com.myapplicationdev.android.finalminibucketlist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_data")
// Todo: crating the the database table and its data
public class MyData {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    public String description;
    private String rating;
    private byte[] image;

    public MyData(String title, String description, String rating, byte[] image) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
