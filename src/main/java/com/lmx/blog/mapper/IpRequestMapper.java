package com.lmx.blog.mapper;

import com.lmx.blog.model.IpRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface IpRequestMapper {

    @Insert({
            "insert into ip_request",
            "(ip_address,route)",
            "values",
            "(#{ipRequest.ipAddress},#{ipRequest.route})"
    })
    int insert(@Param("ipRequest")IpRequest ipRequest);
}
