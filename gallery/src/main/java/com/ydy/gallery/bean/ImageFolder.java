package com.ydy.gallery.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author ydy
 */
public class ImageFolder implements Serializable {

    private int id;
    /**
     * Folder name.
     */
    private String name;
    /**
     * Image list in folder.
     */
    private ArrayList<ImageItem> images = new ArrayList<>();
    /**
     * checked.
     */
    private boolean isChecked;

    public ImageFolder() {
        super();
    }

    public ImageFolder(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ImageItem> getImages() {
        return images;
    }

    public void addPhoto(ImageItem photo) {
        this.images.add(photo);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
