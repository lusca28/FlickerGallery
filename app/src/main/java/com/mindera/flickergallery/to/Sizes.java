package com.mindera.flickergallery.to;

import java.util.List;

public class Sizes{
    private int canblog;
    private int canprint;
    private int candownload;
    private List<Size> size;

    public int getCanblog() {
        return canblog;
    }

    public void setCanblog(int canblog) {
        this.canblog = canblog;
    }

    public int getCanprint() {
        return canprint;
    }

    public void setCanprint(int canprint) {
        this.canprint = canprint;
    }

    public int getCandownload() {
        return candownload;
    }

    public void setCandownload(int candownload) {
        this.candownload = candownload;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }
}
