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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 持久层admin包配置
 */
@Configuration
@MapperScan(basePackages = "com.weiran.manage.mapper.admin", sqlSessionFactoryRef = "adminSqlSessionFactory")
public class AdminSessionFactoryConfiguration {

    @Bean(name = "adminDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.admin")
    public DataSource adminDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 默认使用此事务管理器  如果不想使用 则使用 @Transactional(transactionManager = "transactionManagerWeb") 来指定其他的事务处理器
     */
    @Bean(name = "transactionManagerAdmin")
    @Qualifier(value = "admin")
    @Primary
    public DataSourceTransactionManager transactionManagerAdmin() {
        return new DataSourceTransactionManager(adminDataSource());
    }

    @Bean(name = "adminSqlSessionFactory")
    public SqlSessionFactory activitySqlSessionFactory(@Qualifier("adminDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/admin/**.xml"));
        sfb.setTypeAliasesPackage("com.weiran.manage.dto.admin");
        sfb.setVfs(SpringBootVFS.class);
        SqlSessionFactory factory = sfb.getObject();
        assert factory != null;
        factory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return factory;
    }

}
