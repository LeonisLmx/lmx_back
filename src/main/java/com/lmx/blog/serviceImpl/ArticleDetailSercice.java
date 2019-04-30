package com.lmx.blog.serviceImpl;

import com.lmx.blog.mapper.ArticleDescriptionMapper;
import com.lmx.blog.mapper.ArticleDetailMapper;
import com.lmx.blog.model.ArticleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleDetailSercice {

    @Autowired private ArticleDetailMapper articleDetailMapper;

    @Autowired private ArticleDescriptionMapper articleDescriptionMapper;

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

    public List<Map<String,Object>> queryAllArticles(Integer isOrigin,String text,String author){
        List<Map<String,Object>> list = articleDescriptionMapper.queryAllArticles(isOrigin,text,author);
        return changeListData(list);
    }

    public List<Map<String,Object>> changeListData(List<Map<String,Object>> list){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Map<String,Object> entity:list){
            if(entity.get("tags") != null){
                entity.put("tags",Commonservice.strToList(entity.get("tags").toString()));
            }
            if(entity.get("type").equals("post")){
                entity.put("type","专栏");
            }else if(entity.get("type").equals("me")){
                entity.put("type","[原创]");
            }else{
                entity.put("type","");
            }
            entity.put("hot",entity.get("hot").equals("1")?"热":"");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(entity.get("create_time").toString(),new ParsePosition(0)));
            entity.put("time",Commonservice.getDateCompareNow(calendar));
        }
        return list;
    }
}
