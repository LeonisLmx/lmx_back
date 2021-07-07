package com.lmx.blog.model;

import java.io.Serializable;
import java.util.Date;

/**
 * ES entity
 */
public class ArticleDescription implements Serializable {
    private Long id;

    private String title;

    private String type;

    private Integer hot;

    private String author;

    private Date createTime;

    private Integer goodNum;

    private Integer readNum;

    private Integer messageNum;

    private String articleUrl;

    private String authorUrl;

    private Integer isOrigin;

    private Date modifyTime;

    private Long xuehuaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public Integer getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(Integer messageNum) {
        this.messageNum = messageNum;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public Integer getIsOrigin() {
        return isOrigin;
    }

    public void setIsOrigin(Integer isOrigin) {
        this.isOrigin = isOrigin;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getXuehuaId() {
        return xuehuaId;
    }

    public void setXuehuaId(Long xuehuaId) {
        this.xuehuaId = xuehuaId;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    @Override
    public String toString() {
        return "ArticleDescription{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", hot=" + hot +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", goodNum=" + goodNum +
                ", readNum=" + readNum +
                ", messageNum=" + messageNum +
                ", articleUrl='" + articleUrl + '\'' +
                ", authorUrl='" + authorUrl + '\'' +
                ", isOrigin=" + isOrigin +
                ", modifyTime=" + modifyTime +
                ", xuehuaId=" + xuehuaId +
                '}';
    }
}