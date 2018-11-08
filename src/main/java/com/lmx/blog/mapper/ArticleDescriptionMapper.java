package com.lmx.blog.mapper;

import com.lmx.blog.model.ArticleDescription;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface ArticleDescriptionMapper {
    @Delete({
        "delete from article_description",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into article_description (title, type, author, ",
        "create_time, good_num, ",
        "message_num, article_url, ",
        "authorUrl, modify_time)",
        "values (#{title,jdbcType=VARCHAR},#{type,jdbcType=VARCHAR}, #{author,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{goodNum,jdbcType=INTEGER}, ",
        "#{messageNum,jdbcType=INTEGER}, #{articleUrl,jdbcType=VARCHAR}, ",
        "#{authorurl,jdbcType=VARCHAR}, #{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(ArticleDescription record);

    @InsertProvider(type=ArticleDescriptionSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(ArticleDescription record);

    @Select({
        "select",
        "id, title, type, author, create_time, good_num, message_num, article_url, authorUrl, ",
        "modify_time",
        "from article_description",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="good_num", property="goodNum", jdbcType=JdbcType.INTEGER),
        @Result(column="message_num", property="messageNum", jdbcType=JdbcType.INTEGER),
        @Result(column="article_url", property="articleUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="authorUrl", property="authorurl", jdbcType=JdbcType.VARCHAR),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    ArticleDescription selectByPrimaryKey(Long id);

    @UpdateProvider(type=ArticleDescriptionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ArticleDescription record);

    @Update({
        "update article_description",
        "set type = #{type,jdbcType=VARCHAR},",
          "author = #{author,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "good_num = #{goodNum,jdbcType=INTEGER},",
          "message_num = #{messageNum,jdbcType=INTEGER},",
          "article_url = #{articleUrl,jdbcType=VARCHAR},",
          "authorUrl = #{authorurl,jdbcType=VARCHAR},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ArticleDescription record);


    @Insert({
        "<script>",
            "insert into article_description (title,type,author,create_time,good_num,message_num,article_url,author_url,xuehua_id)",
            "values",
            "<foreach collection='list' item='entity' index='index' separator=','>",
            "(#{entity.title},#{entity.type},#{entity.author},#{entity.createTime},#{entity.goodNum},#{entity.messageNum},#{entity.articleUrl},#{entity.authorUrl},#{entity.xuehuaId})",
            "</foreach>",
        "</script>"
    })
    int batchInsertActicle(@Param("list") List<ArticleDescription> list);

    @Select({
            "select count(*) from article_description where article_url = #{articleUrl}"
    })
    int selectArticleIsHave(@Param("articleUrl")String articleUrl);
}