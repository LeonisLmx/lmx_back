package com.lmx.blog.model.result;

import java.io.Serializable;

public class ZsetTestModel implements Serializable {

    private static final long serialVersionUID = -2804154730417514825L;
    private String author;

    private Integer count;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
