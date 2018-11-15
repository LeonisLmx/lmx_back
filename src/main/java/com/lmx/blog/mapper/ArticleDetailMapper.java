package com.lmx.blog.mapper;

import com.lmx.blog.model.ArticleDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ArticleDetailMapper {

    @Select({
            "select * from article_detail where id = #{id}"
    })
    ArticleDetail selectPrimaryKey(@Param("id")Long id);

    @Update({
            "update article_detail set",
            "read_num = #{entity.readNum}",
            "where id = #{entity.id}"
    })
    int update(@Param("entity")ArticleDetail entity);
}
