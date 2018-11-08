package com.lmx.blog.model;

import java.util.Date;

public class Tag {

    private Long id;
    private String name;
    private Date createTime;
    private Long xuehuaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getXuehuaId() {
        return xuehuaId;
    }

    public void setXuehuaId(Long xuehuaId) {
        this.xuehuaId = xuehuaId;
    }
}
