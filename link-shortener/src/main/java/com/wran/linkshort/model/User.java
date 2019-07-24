package com.wran.linkshort.model;

import java.io.Serializable;

public class User implements Serializable {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
