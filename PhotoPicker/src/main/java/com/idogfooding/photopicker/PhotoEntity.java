package com.idogfooding.photopicker;

import java.io.Serializable;

/**
 * PhotoEntity
 *
 * @author Charles
 */

public class PhotoEntity implements Serializable {

    public final static int TYPE_ADD = 1;
    public final static int TYPE_FILE = 2;
    public final static int TYPE_URL = 3;

    public PhotoEntity(int type) {
        this.type = type;
    }

    public PhotoEntity(int type, String path) {
        this.type = type;
        this.path = path;
    }

    private int type;
    private String path;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
