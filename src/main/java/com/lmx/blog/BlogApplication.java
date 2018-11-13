package com.lmx.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableScheduling
public class BlogApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    // 服务器内存不够。es无法运行
    /*@Bean
    public TransportClient transportClient() throws IOException {
        Settings setting = Settings.builder()
                //.put("cluster.name", es_cluster)//指定集群名称
                .put("client.transport.sniff", true)//启动嗅探功能
                .build();
        TransportClient transportClient = new PreBuiltTransportClient(setting)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("101.132.137.156"), 9300));
        return transportClient;
    }*/
}
