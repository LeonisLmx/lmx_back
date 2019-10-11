package com.lmx.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.lmx.blog.annotation.RedisCache;
import com.lmx.blog.common.HttpClientRequest;
import com.lmx.blog.common.Response;
import com.lmx.blog.config.redis.RedisExecutor;
import com.lmx.blog.model.SendMessage;
import com.lmx.blog.service.EmailService;
import com.lmx.blog.serviceImpl.ArticleDetailSercice;
import com.lmx.blog.serviceImpl.Commonservice;
import com.lmx.blog.serviceImpl.JuejinCrawerService;
import org.joda.time.DateTime;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.*;

@RequestMapping("/user")
@RestController
public class UserController {

    private static int produceTaskMaxNumber = 10;

    private static int pageSize = 20;

    @Autowired private JuejinCrawerService juejinCrawerService;

    @Autowired private ArticleDetailSercice articleDetailSercice;

    @Autowired private EmailService emailService;

    @Autowired private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${api.key}")
    private String key;

    @Value("${api.ipUrl}")
    private String ipUrl;

    private static Map<String,Object> map = new HashMap<>();

    @RequestMapping("/test")
    public Response test(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(6),new ThreadPoolExecutor.DiscardOldestPolicy());
        for(int i = 1;i <= produceTaskMaxNumber;i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        juejinCrawerService.getArticle();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
        return Response.ok(true);
    }

    @RequestMapping("/achieve")
    public Response achieve(){
        System.out.println("方法执行-----------");
        return Response.ok(articleDetailSercice.getPrimaryKeyById(1L));
    }

    @RedisCache(type = Response.class)
    @RequestMapping("/getList")
    public Response getListArticles(Integer pageNum,Integer isOrigin,String text){
        System.out.println("执行了----------------------------------");
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> lists = articleDetailSercice.queryAllArticles(isOrigin,text,null);
        PageInfo page = new PageInfo(lists);
        return Response.ok(page);
    }

    @RedisCache(type = Response.class)
    @RequestMapping("/sendEmail")
    public Response sendEmailToAuthor(String content, HttpServletRequest request){
        // 获取当前的IP地址，防止恶意刷IP地址
        String ip = Commonservice.getIp(request);
        if(RedisExecutor.getByKey(ip) != null){
            return Response.ok(false);
        }
        if(!request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setContent(content);
            sendMessage.setFromIp(request.getRemoteAddr());
            sendMessage.setCreateTime(new Date());
            // 记录发送的消息
            amqpTemplate.convertAndSend("send_message",sendMessage);
        }
        RedisExecutor.addKeyAndExpireTimes(ip,content,5L,TimeUnit.MINUTES);
        ExecutorService singPool = Executors.newSingleThreadExecutor();
        singPool.execute(new Runnable() {
            @Override
            public void run() {
                emailService.sendSimpleEmail("18262388063@sina.cn","游客来件",content);
            }
        });
        return Response.ok(true);
    }

    @RequestMapping("/get_address")
    public Response getAddress(HttpServletRequest request) throws Exception {
        Gson gson = new Gson();
        DateTime dateTime = new DateTime();
        String ip = Commonservice.getIp(request);
        Boolean flag = redisTemplate.opsForZSet().add(dateTime.year().get() + "/" + dateTime.monthOfYear().get() + "/" + dateTime.dayOfMonth().get(),ip,dateTime.getMillis());
        String result = HttpClientRequest.Get(ipUrl + ip + "&key=" + key);
        Map<String,Object> bMap = gson.fromJson(result,Map.class);
        Map<String,Object> returnMap = new HashMap<>();
        if(flag) {
            StringBuilder sb = new StringBuilder();
            Long count = redisTemplate.opsForZSet().rank(dateTime.year().get() + "/" + dateTime.monthOfYear().get() + "/" + dateTime.dayOfMonth().get(),ip);
            Map<String,Object> resultMap = gson.fromJson(gson.toJson(bMap.get("result")),Map.class);
            sb.append(resultMap.get("Country")).append("-").append(resultMap.get("Province")).append("-").append(resultMap.get("City"))
                    .append("-")
                    .append(resultMap.get("Isp"))
                    .append("的用户！您好，您是当天第").append(count + 1).append("位访问用户");
            String ipResult = HttpClientRequest.Get("http://ip-api.com/json/" + ip);
            Map<String,Object> ipResultMap = gson.fromJson(ipResult,Map.class);
            Point point = new Point(Double.valueOf(ipResultMap.get("lon").toString()),Double.valueOf(ipResultMap.get("lat").toString()));
            redisTemplate.opsForGeo().add("location",point, ip);
            Metric metric = new Metric() {
                @Override
                public double getMultiplier() {
                    return 6;        // 返回的数量
                }

                @Override
                public String getAbbreviation() {
                    return "km";    // 距离单位
                }
            };
            Distance distance = new Distance(20,metric);     // value代表距离限制
            RedisGeoCommands.GeoRadiusCommandArgs arg = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();
            arg = arg.includeDistance();
            arg = arg.sortAscending();
            GeoResults geoResults = redisTemplate.opsForGeo().geoRadiusByMember("location",ip,distance,arg);
            Iterator<GeoResult<RedisGeoCommands.GeoLocation>> it = geoResults.iterator();
            List<Object> list = new LinkedList<>();
            while (it.hasNext()){
                GeoResult<RedisGeoCommands.GeoLocation> geoResult = it.next();
                if(!geoResult.getContent().getName().equals(ip)){
                    if(list.size() == 5){
                        break;
                    }
                    list.add(geoResult.getContent().getName().toString() + "_" + geoResult.getDistance().getValue() + "km");
                }
            }
            returnMap.put("message",sb);
            returnMap.put("near",list);
            returnMap.put("flag",true);
            return Response.ok(returnMap);
        }else{
            returnMap.put("flag",false);
            returnMap.put("message","已经签到过了!");
            return Response.ok(returnMap);
        }
    }

    @RedisCache(type = Response.class)
    @RequestMapping("/sendEmail_wangjia")
    public Response sendEmailToWangJia(String content, HttpServletRequest request){
        // 获取当前的IP地址，防止恶意刷IP地址
        String ip = Commonservice.getIp(request);
        if(!request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setContent(content);
            sendMessage.setFromIp(request.getRemoteAddr());
            sendMessage.setCreateTime(new Date());
            // 记录发送的消息
            amqpTemplate.convertAndSend("send_message",sendMessage);
        }
        ExecutorService singPool = Executors.newSingleThreadExecutor();
        singPool.execute(new Runnable() {
            @Override
            public void run() {
                emailService.sendSimpleEmail("18262388063@sina.cn","王佳的邮件",content);
            }
        });
        return Response.ok(true);
    }
}
