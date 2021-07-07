package com.lmx.blog.task;

import com.lmx.blog.config.redis.RedisExecutor;
import com.lmx.blog.mapper.ArticleDescriptionMapper;
import com.lmx.blog.model.ArticleDescription;
import com.lmx.blog.serviceImpl.Commonservice;
import com.lmx.blog.serviceImpl.JuejinCrawerService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘明新
 * @date 2018/11/8 下午4:46
 */
@Component
public class JuejinTask {

    private final String PULL_REDIS_LOCK = "PULL_ARTICLE";

    @Autowired
    private JuejinCrawerService juejinCrawerService;

    @Autowired
    private RedisExecutor redisExecutor;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ArticleDescriptionMapper articleDescriptionMapper;

    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 3, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.DiscardOldestPolicy());

    //@Scheduled(fixedDelay = 1800_000,initialDelay = 0)
    public void crawerJuejin(){
        String uuid = UUID.randomUUID().toString();
        Boolean flag = redisExecutor.getLock(PULL_REDIS_LOCK, uuid,30);
        if(flag) {
            for (int i = 1; i <= 10; i++) {
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            juejinCrawerService.getArticle();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            redisExecutor.releaseLock(PULL_REDIS_LOCK,uuid);
        }
    }

    // 10S的速率往令牌桶中添加UUID，只为保证唯一性
    @Scheduled(fixedDelay = 10_000,initialDelay = 0)
    public void setIntervalTimeTask(){
        // 防止 limit_list 数据量过大，默认最多200个限制
        if(redisTemplate.opsForList().size("limit_list") <= 200){
            redisTemplate.opsForList().rightPush("limit_list",UUID.randomUUID().toString());
        }
    }

    // 1h更新一次CSDN博客阅读数量
    @Scheduled(fixedDelay = 3600_000,initialDelay = 0)
    public void sychorinizedArticeByCSDN() throws IOException {
        Document document = Jsoup.connect("https://me.csdn.net/lmx125254").get();
        Elements elements = document.body().getElementsByClass("tab_page_list");
        // 会封IP。所以try catch获取异常继续执行
        try {
            elements.forEach( (n) -> {
                ArticleDescription articleDescription = articleDescriptionMapper.selectIsExitByUrl(n.getElementsByTag("a").get(0).attr("href"));
                Map<String,Integer> map = new HashMap<>();
                try {
                    map = getReadAndMessageNum(n.getElementsByTag("a").get(0).attr("href"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(articleDescription == null){
                    articleDescription = new ArticleDescription();
                    articleDescription.setArticleUrl(n.getElementsByTag("a").get(0).attr("href"));
                    articleDescription.setAuthor("刘明新");
                    articleDescription.setTitle(n.getElementsByTag("a").get(0).text());
                    articleDescription.setHot(-1);
                    articleDescription.setType("me");
                    articleDescription.setMessageNum(map.get("messageNum"));
                    articleDescription.setReadNum(Integer.valueOf(n.getElementsByTag("em").text()));
                    articleDescription.setCreateTime(new Date());
                    articleDescription.setGoodNum(map.get("goodNum"));
                    articleDescription.setAuthorUrl("https://blog.csdn.net/lmx125254");
                    articleDescription.setIsOrigin(1);
                    articleDescription.setModifyTime(articleDescription.getCreateTime());
                    articleDescription.setXuehuaId(Commonservice.getNextId());
                    articleDescriptionMapper.insert(articleDescription);
                }else if(Integer.valueOf(n.getElementsByTag("em").text()) - articleDescription.getReadNum() > 0){
                    articleDescription.setReadNum(Integer.valueOf(n.getElementsByTag("em").text()));
                    articleDescription.setGoodNum(map.get("goodNum"));
                    articleDescription.setMessageNum(map.get("messageNum"));
                    articleDescription.setModifyTime(new Date());
                    articleDescriptionMapper.updateByPrimaryKey(articleDescription);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Map<String,Integer> getReadAndMessageNum(String url) throws IOException {
        Map<String,Integer> map = new HashMap<>();
        Document document = Jsoup.connect(url).get();
        Element element = document.body().getElementById("spanCount");
        map.put("goodNum", StringUtils.isBlank(element.text()) ?0:Integer.valueOf(element.text()));
        Element message = document.body().getElementsByClass("tool-item-comment").get(0).getElementsByClass("count").get(0);
        map.put("messageNum", StringUtils.isBlank(message.text())?0:Integer.valueOf(message.text()));
        return map;
    }
}
