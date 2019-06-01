package com.lmx.blog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "article_description",type = "docs",shards = 1,replicas = 0)
public class ArticleDescription implements Serializable {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String type;

    @Field(type = FieldType.Integer)
    private Integer hot;

    @Field(type = FieldType.Text)
    private String author;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Integer)
    private Integer goodNum;

    @Field(type = FieldType.Integer)
    private Integer messageNum;

    @Field(type = FieldType.Text)
    private String articleUrl;

    @Field(type = FieldType.Text)
    private String authorUrl;

    @Field(type = FieldType.Integer)
    private Integer isOrigin;

    @Field(type = FieldType.Date)
    private Date modifyTime;

    @Field(type = FieldType.Long)
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
}