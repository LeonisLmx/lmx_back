package com.lmx.blog.mapper;


import com.lmx.blog.model.Users;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UsersMapper {

    @Select({
            "select * from users"
    })
    List<Users> selectAll();
}
