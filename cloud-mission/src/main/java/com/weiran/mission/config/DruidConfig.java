package com.weiran.mission.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Druid数据库连接池配置文件
 */
@Configuration
public class DruidConfig {

    /**
     * 解决druid 日志报错：discard long time none received connection:xxx
     */
    @PostConstruct
    public void setProperties() {
        System.setProperty("druid.mysql.usePingMethod","false");
    }

    /**
     * 配置 Druid 监控界面
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> srb = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");
        // 设置控制台管理用户
        srb.addInitParameter("loginUsername","root");
        srb.addInitParameter("loginPassword","root");
        // 是否可以重置数据
        srb.addInitParameter("resetEnable","false");
        return srb;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> statFilter() {
        // 创建过滤器
        FilterRegistrationBean<WebStatFilter> frb = new FilterRegistrationBean<>(new WebStatFilter());
        // 设置过滤器过滤路径
        frb.addUrlPatterns("/*");
        // 忽略过滤的形式
        frb.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return frb;
    }
}
