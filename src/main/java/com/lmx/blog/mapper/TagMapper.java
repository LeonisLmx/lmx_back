package com.lmx.blog.mapper;

import com.lmx.blog.model.ArticleDescription;
import com.lmx.blog.model.Tag;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface TagMapper {
    @Delete({
        "delete from tag",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tag (xuehua_id, name, ",
        "create_time)",
        "values (#{xuehuaId,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(Tag record);

    @InsertProvider(type=TagSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(Tag record);

    @Select({
        "select",
        "id, name, create_time, xuehua_id",
        "from tag",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="xuehua_id", property="articleDescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Tag selectByPrimaryKey(Long id);

    @UpdateProvider(type=TagSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Tag record);

    @Update({
        "update tag",
        "set xuehua_id = #{xuehuaId,jdbcType=BIGINT},",
          "name = #{name,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Tag record);

    @Insert({
            "<script>",
            "insert into tag (name,create_time,xuehua_id,modify_time)",
            "values",
            "<foreach collection='list' item='entity' index='index' separator=','>",
            "(#{entity.name},#{entity.createTime},#{entity.xuehuaId},now())",
            "</foreach>",
            "</script>"
    })
    int batchInsertActicle(@Param("list") List<Tag> list);
}