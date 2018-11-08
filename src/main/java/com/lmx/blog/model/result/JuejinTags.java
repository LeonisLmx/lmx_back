package com.lmx.blog.model.result;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * @author 刘明新
 * @date 2018/11/8 下午4:59
 */
public class JuejinTags {

    private Long ngxCachedTime;
    private Boolean ngxCached;
    private String title;

    public Long getNgxCachedTime() {
        return ngxCachedTime;
    }

    public void setNgxCachedTime(Long ngxCachedTime) {
        this.ngxCachedTime = ngxCachedTime;
    }

    public Boolean getNgxCached() {
        return ngxCached;
    }

    public void setNgxCached(Boolean ngxCached) {
        this.ngxCached = ngxCached;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
