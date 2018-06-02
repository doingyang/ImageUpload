package com.ydy.gallery.bean;

/**
 * @author ydy
 */
public class FolderClickEvent {

    private int position;
    private ImageFolder folder;

    public FolderClickEvent(int position, ImageFolder folder) {
        this.position = position;
        this.folder = folder;
    }

    public ImageFolder getFolder() {
        return folder;
    }

    public int getPosition() {
        return position;
    }

}
