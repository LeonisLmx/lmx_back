package com.lmx.blog.mapper;

import com.lmx.blog.model.ArticleDescription;
import com.lmx.blog.model.result.ZsetTestModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ArticleDescriptionMapper{
    @Delete({
        "delete from article_description",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into article_description (title, hot, type, author, ",
        "create_time, good_num, read_num, ",
        "message_num, article_url, ",
        "author_url,is_origin, modify_time, xuehua_id)",
        "values (#{title,jdbcType=VARCHAR},#{hot,jdbcType=INTEGER},#{type,jdbcType=VARCHAR}, #{author,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{goodNum,jdbcType=INTEGER}, #{readNum,jdbcType=INTEGER}, ",
        "#{messageNum,jdbcType=INTEGER}, #{articleUrl,jdbcType=VARCHAR}, ",
        "#{authorUrl,jdbcType=VARCHAR}, #{isOrigin,jdbcType=INTEGER},#{modifyTime,jdbcType=TIMESTAMP},#{xuehuaId,jdbcType=BIGINT})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(ArticleDescription record);

    @InsertProvider(type=ArticleDescriptionSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(ArticleDescription record);

    @Select({
        "select",
        "id, title, type, author, create_time, good_num, read_num, message_num, article_url, author_url, ",
        "is_origin,modify_time",
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
        @Result(column="read_num", property="readNum", jdbcType=JdbcType.INTEGER),
        @Result(column="message_num", property="messageNum", jdbcType=JdbcType.INTEGER),
        @Result(column="article_url", property="articleUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="author_url", property="authorUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_origin", property="isOrigin", jdbcType=JdbcType.INTEGER),
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
          "read_num = #{readNum,jdbcType=INTEGER},",
          "message_num = #{messageNum,jdbcType=INTEGER},",
          "article_url = #{articleUrl,jdbcType=VARCHAR},",
          "author_url = #{authorUrl,jdbcType=VARCHAR},",
          "is_origin = #{isOrigin,jdbcType=INTEGER},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ArticleDescription record);


    @Insert({
        "<script>",
            "insert into article_description (title,hot,type,author,create_time,good_num,read_num,message_num,article_url,is_origin,author_url,xuehua_id)",
            "values",
            "<foreach collection='list' item='entity' index='index' separator=','>",
            "(#{entity.title},#{entity.hot},#{entity.type},#{entity.author},#{entity.createTime},#{entity.goodNum},#{entity.readNum},#{entity.messageNum},#{entity.articleUrl},#{entity.isOrigin},#{entity.authorUrl},#{entity.xuehuaId})",
            "</foreach>",
        "</script>"
    })
    int batchInsertActicle(@Param("list") List<ArticleDescription> list);

    @Select({
            "select id from article_description where article_url = #{articleUrl}"
    })
    Long selectArticleIsHave(@Param("articleUrl")String articleUrl);

    @Update({
            "<script>",
            "<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\">",
            "update article_description",
            "set good_num = #{item.goodNum},read_num = #{item.readNum},message_num = #{item.messageNum},modify_time = now()",
            "where id = #{item.id}",
            "</foreach>",
            "</script>"
    })
    int batchUpdate(@Param("list")List<ArticleDescription> list);

    @Select({
            "<script>",
            "select a.*,(select GROUP_CONCAT(name) as tags from tag b where a.xuehua_id = b.xuehua_id) as tags from article_description a",
            "where 1 = 1",
            "<if test='isOrigin!=null'>and a.is_origin = #{isOrigin}</if>",
            "<if test='text != null and text != \"\"'>and a.title like '%${text}%'</if>",
            "<if test='author != null and author != \"\"'>and a.author = #{author}</if>",
            "order by",
            "<if test='isOrigin == null'>a.good_num desc,</if>",
            " a.modify_time desc,id asc",
            "</script>"
    })
    List<Map<String,Object>> queryAllArticles(@Param("isOrigin")Integer isOrigin,@Param("text")String text,@Param("author")String author);

    @Select({
            "select DISTINCT author,count(0) as count from article_description GROUP BY author order by count(0) desc"
    })
    List<ZsetTestModel> selectCount();

    @Select({
            "select * from article_description where article_url = #{url}"
    })
    ArticleDescription selectIsExitByUrl(@Param("url")String url);
}