package com.lmx.blog.service;

import com.lmx.blog.common.SnowFlakeGenerator;
import org.springframework.stereotype.Service;

/**
 * @author 刘明新
 * @date 2018/11/8 下午6:05
 */
@Service
public class Commonservice {

    // 雪花算法ID
    public static Long getNextId() {
        return new SnowFlakeGenerator(0, 0).nextId();
    }
}
