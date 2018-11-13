package com.lmx.blog.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;

/**
 * 配置DataSource
 * @author 刘明新
 * @date 2018/8/6 下午5:01
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.lmx.blog.mapper")
public class DataSourceConfig {

    /**
     * prefix地址是yml文件的目录地址
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DruidDataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        /*dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(180);
        dataSource.setLogAbandoned(true);*/
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(masterDataSource());
        factory.setConfigLocation(new ClassPathResource("/mybatis-config.xml"));
        return factory;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }


    /*@Bean(name = "wallConfig")
    WallConfig wallFilterConfig(){
        WallConfig wc = new WallConfig();
        wc.setMultiStatementAllow(true);
        return wc;
    }*/
    /*@Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }*/
}
