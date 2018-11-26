package com.lmx.blog.mapper;

import com.lmx.blog.model.SendMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SendMessageMapper {

    @Insert({
            "insert into send_message",
            "(content,from_ip,create_time)",
            "values",
            "(#{entity.content},#{entity.fromIp},#{entity.createTime})"
    })
    int insert(@Param("entity")SendMessage entity);
}
