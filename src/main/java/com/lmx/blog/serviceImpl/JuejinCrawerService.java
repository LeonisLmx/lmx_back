package com.lmx.blog.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lmx.blog.common.HttpClientRequest;
import com.lmx.blog.config.redis.RedisExecutor;
import com.lmx.blog.mapper.ArticleDescriptionMapper;
import com.lmx.blog.mapper.TagMapper;
import com.lmx.blog.model.ArticleDescription;
import com.lmx.blog.model.Tag;
import com.lmx.blog.model.result.JuejinEntity;
import com.lmx.blog.model.result.JuejinTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.Vector;


/**
 * @author 刘明新
 * @date 2018/11/8 下午4:47
 * 掘金网站通过接口获取文章数据
 */
@Service
public class JuejinCrawerService {

    @Autowired private ArticleDescriptionMapper articleDescriptionMapper;

    @Autowired private TagMapper tagMapper;

    @Autowired
    private RedisExecutor redisExecutor;

    public Boolean getArticle() throws Exception{
        int a = new Random().nextInt(30);
        Float random = new Random().nextFloat();
        // 由于掘金文章是根据before取值的。所以随机生成数字
        Float f = a + random;
        String url = "https://timeline-merger-ms.juejin.im/v1/get_entry_by_rank?src=web&before=" + f + "&limit=20&category=5562b419e4b00c57d9b94ae2";
        String response = HttpClientRequest.Get(url);
        response = response.substring(response.indexOf("\"entrylist\":") + 12,response.lastIndexOf("}}"));
        Vector<JuejinEntity> reponseMap = new Gson().fromJson(response,new TypeToken<Vector<JuejinEntity>>(){}.getType());
        Vector<ArticleDescription> articleDescriptions = new Vector<>();
        Vector<ArticleDescription> updateLists = new Vector<>();
        Vector<Tag> tags = new Vector<>();
        for(JuejinEntity entity:reponseMap){
            Long xuehuaId = Commonservice.getNextId();
            // 只对重复数据加锁 ，防止数据库数据出现重复
            synchronized (entity.getOriginalUrl()){
                ArticleDescription articleDescription = new ArticleDescription();
                articleDescription.setArticleUrl(entity.getOriginalUrl());
                articleDescription.setTitle(entity.getTitle());
                articleDescription.setAuthor(entity.getUser().getUsername());
                articleDescription.setAuthorUrl("https://juejin.im/user/" + entity.getUser().getObjectId());
                articleDescription.setCreateTime(entity.getCreatedAt());
                articleDescription.setGoodNum(entity.getCollectionCount());
                articleDescription.setReadNum(entity.getViewsCount());
                articleDescription.setMessageNum(entity.getCommentsCount());
                articleDescription.setType(entity.getType());
                articleDescription.setHot(entity.getHot()?1:-1);
                articleDescription.setXuehuaId(xuehuaId);
                articleDescription.setIsOrigin(-1);
                if(articleDescriptionMapper.selectArticleIsHave(entity.getOriginalUrl()) == null) {
                    // 说明是不存在的
                    if(redisExecutor.getRankByObject("zset",entity.getAuthor()) == null){
                        redisExecutor.addZSet("zset",entity.getAuthor(),1L);
                    }else{
                        // 存在则自增加1
                        redisExecutor.addScore("zset",entity.getAuthor(),1L);
                    }
                    articleDescriptions.add(articleDescription);
                }else{
                    articleDescription.setId(articleDescriptionMapper.selectArticleIsHave(entity.getOriginalUrl()));
                    updateLists.add(articleDescription);
                }
                for(JuejinTags juejinTags:entity.getTags()){
                    Tag tag = new Tag();
                    tag.setName(juejinTags.getTitle());
                    tag.setXuehuaId(xuehuaId);
                    tag.setCreateTime(new Date());
                    tags.add(tag);
                }
            }
        }
        if(articleDescriptions.size() > 0) {
            articleDescriptionMapper.batchInsertActicle(articleDescriptions);
            tagMapper.batchInsertActicle(tags);
        }
        // 更新点赞数和留言数
        if(updateLists.size() > 0){
            articleDescriptionMapper.batchUpdate(updateLists);
        }
        return true;
    }
}
