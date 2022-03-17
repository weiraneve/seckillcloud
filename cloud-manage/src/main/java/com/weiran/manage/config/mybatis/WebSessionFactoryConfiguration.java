package com.weiran.manage.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;


/**
 * 持久层web包配置
 */
@Configuration
@MapperScan(basePackages = "com.weiran.manage.mapper.web", sqlSessionFactoryRef = "webSqlSessionFactory")
public class WebSessionFactoryConfiguration {

    @Bean(name = "webDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.web")
    public DataSource webDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Qualifier(value = "web")
    @Bean(name = "transactionManagerWeb")
    public DataSourceTransactionManager transactionManagerWeb() {
        return new DataSourceTransactionManager(webDataSource());
    }

    @Bean(name = "webSqlSessionFactory")
    public SqlSessionFactory activitySqlSessionFactory(@Qualifier("webDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/web/**.xml"));
        sfb.setTypeAliasesPackage("com.weiran.manage.dto.web");
        sfb.setVfs(SpringBootVFS.class);
        SqlSessionFactory factory = sfb.getObject();
        Objects.requireNonNull(factory).getConfiguration().setMapUnderscoreToCamelCase(true);
        return factory;
    }

}

