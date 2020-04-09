package com.jim.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 将阿里连接池的驱动注册到spring容器中
 */
@Configuration
public class DruidConfig {
    //加入初始化init和close，不然会进入死循环
    @Bean(destroyMethod = "close",initMethod = "init")
    //引入application.yml中前缀"spring.datasource"的相关配置
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource(){
        return new DruidDataSource();
    }
}
