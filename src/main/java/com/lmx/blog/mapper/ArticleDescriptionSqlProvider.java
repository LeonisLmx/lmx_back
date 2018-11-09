package com.lmx.blog.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.lmx.blog.model.ArticleDescription;

public class ArticleDescriptionSqlProvider {

    public String insertSelective(ArticleDescription record) {
        BEGIN();
        INSERT_INTO("article_description");

        if (record.getTitle() != null) {
            VALUES("title", "#{title,jdbcType=VARCHAR}");
        }

        if (record.getType() != null) {
            VALUES("type", "#{type,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthor() != null) {
            VALUES("author", "#{author,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getGoodNum() != null) {
            VALUES("good_num", "#{goodNum,jdbcType=INTEGER}");
        }
        
        if (record.getMessageNum() != null) {
            VALUES("message_num", "#{messageNum,jdbcType=INTEGER}");
        }
        
        if (record.getArticleUrl() != null) {
            VALUES("article_url", "#{articleUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorUrl() != null) {
            VALUES("author_url", "#{authorUrl,jdbcType=VARCHAR}");
        }

        if (record.getIsOrigin() != null) {
            VALUES("is_origin", "#{isOrigin,jdbcType=INTEGER}");
        }
        
        if (record.getModifyTime() != null) {
            VALUES("modify_time", "#{modifyTime,jdbcType=TIMESTAMP}");
        }

        if (record.getXuehuaId() != null) {
            VALUES("xuehua_id", "#{xuehuaId,jdbcType=TIMESTAMP}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(ArticleDescription record) {
        BEGIN();
        UPDATE("article_description");

        if (record.getTitle() != null) {
            SET("type = #{title,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            SET("type = #{type,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthor() != null) {
            SET("author = #{author,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getGoodNum() != null) {
            SET("good_num = #{goodNum,jdbcType=INTEGER}");
        }
        
        if (record.getMessageNum() != null) {
            SET("message_num = #{messageNum,jdbcType=INTEGER}");
        }
        
        if (record.getArticleUrl() != null) {
            SET("article_url = #{articleUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorUrl() != null) {
            SET("author_url = #{authorUrl,jdbcType=VARCHAR}");
        }

        if (record.getIsOrigin() != null) {
            SET("is_origin = #{isOrigin,jdbcType=INTEGER}");
        }

        if (record.getModifyTime() != null) {
            SET("modify_time = #{modifyTime,jdbcType=TIMESTAMP}");
        }

        if (record.getXuehuaId() != null) {
            SET("xuehua_id = #{xuehuaId,jdbcType=BIGINT}");
        }
        
        WHERE("id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}