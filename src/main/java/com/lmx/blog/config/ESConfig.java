package com.lmx.blog.config;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ESConfig {

    /*private TransportClient transportClient;
    //集群名称
    //String es_cluster = "es_cluster";
    //集群连接IP地址
    String es_ip = "101.132.137.156";
    //集群连接端口号
    Integer es_port = 9300;



    public ESConfig() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    *//**
     * @throws IOException
     * 初始化ES客户端连接
     *//*
    public void init() throws IOException {
        *//**
         * 1:通过 setting对象来指定集群配置信息
         *//*
        Settings setting = Settings.builder()
                //.put("cluster.name", es_cluster)//指定集群名称
                .put("client.transport.sniff", true)//启动嗅探功能
                .build();

        *//**
         * 2：创建客户端
         * 通过setting来创建，若不指定则默认链接的集群名为elasticsearch
         * 链接使用TCP协议即9300
         *//*

        transportClient = new PreBuiltTransportClient(setting)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(es_ip), es_port));

        *//**
         * 3：查看集群信息
         * 注意我的集群结构是：
         *   131的elasticsearch.yml中指定为主节点不能存储数据，
         *   128的elasticsearch.yml中指定不为主节点只能存储数据。
         * 所有控制台只打印了192.168.79.128,只能获取数据节点
         *
         *//*
        List<DiscoveryNode> connectedNodes = transportClient.connectedNodes();
        for(DiscoveryNode node : connectedNodes)
        {
            System.out.println(node.getHostAddress());
        }
    }*/

}
