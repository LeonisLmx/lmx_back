package com.lmx.blog.model;

import java.util.Date;

public class Tag {

    private Long id;
    private Long articleDescriptionId;
    private String name;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleDescriptionId() {
        return articleDescriptionId;
    }

    public void setArticleDescriptionId(Long articleDescriptionId) {
        this.articleDescriptionId = articleDescriptionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
