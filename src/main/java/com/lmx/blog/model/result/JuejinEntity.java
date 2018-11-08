package com.lmx.blog.model.result;

import java.util.Date;
import java.util.List;

/**
 * @author 刘明新
 * @date 2018/11/8 下午4:54
 */
public class JuejinEntity {

    private Integer collectionCount;   //点赞数
    private Integer commentsCount;  //留言数
    private List<JuejinTags> tags; //标签
    private User user;
    private Long ngxCachedTime; //抓取到的时间
    private Date createdAt; // 文章创作时间
    private Boolean hot;
    private String originalUrl; //文章链接
    private Boolean original; // false 为专栏
    private String author;
    private String screenshot; // 首页文章图的地址
    private String content; //文章描述
    private String title; //文章名字
    private Integer viewsCount; // 浏览数

    public Integer getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(Integer collectionCount) {
        this.collectionCount = collectionCount;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public List<JuejinTags> getTags() {
        return tags;
    }

    public void setTags(List<JuejinTags> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getNgxCachedTime() {
        return ngxCachedTime;
    }

    public void setNgxCachedTime(Long ngxCachedTime) {
        this.ngxCachedTime = ngxCachedTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Boolean getOriginal() {
        return original;
    }

    public void setOriginal(Boolean original) {
        this.original = original;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }
}
