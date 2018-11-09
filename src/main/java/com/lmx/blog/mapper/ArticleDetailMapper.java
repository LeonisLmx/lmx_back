package com.lmx.blog.mapper;

import com.lmx.blog.model.ArticleDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ArticleDetailMapper {

    @Select({
            "select * from article_detail where id = #{id}"
    })
    ArticleDetail selectPrimaryKey(@Param("id")Long id);
}
