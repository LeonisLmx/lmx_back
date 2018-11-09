package com.lmx.blog.service;

import com.lmx.blog.mapper.ArticleDetailMapper;
import com.lmx.blog.model.ArticleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class ArticleDetailSercice {

    @Autowired private ArticleDetailMapper articleDetailMapper;

    public Map<String,Object> getPrimaryKeyById(Long id){
        ArticleDetail articleDetail = articleDetailMapper.selectPrimaryKey(1L);
        Map<String,Object> map = new HashMap<>();
        try{
            map = Commonservice.convertBeanToMap(articleDetail);
        }catch (Exception e){
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("time",sdf.format(articleDetail.getCreateTime()));
        return map;
    }
}
