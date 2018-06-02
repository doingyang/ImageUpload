package com.ydy.gallery.bean;

import java.io.Serializable;

/**
 * @author ydy
 */
public class ImageItem implements Serializable, Comparable<ImageItem> {

    /**
     * Image id.
     */
    private int id;
    /**
     * Image path.
     */
    private String path;
    /**
     * Image name.
     */
    private String name;
    /**
     * The time to be added to the library.
     */
    private long addTime;

    private boolean selected;

    public ImageItem(int id, String path, String name, long addTime) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.addTime = addTime;
    }

    public ImageItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int compareTo(ImageItem o) {
        long time = o.getAddTime() - getAddTime();
        if (time > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else if (time < -Integer.MAX_VALUE) {
            return -Integer.MAX_VALUE;
        }
        return (int) time;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", addTime=" + addTime +
                ", selected=" + selected +
                '}';
    }
}
