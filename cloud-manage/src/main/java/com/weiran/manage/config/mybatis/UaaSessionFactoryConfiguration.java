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
 * 持久层uaa包配置
 */
@Configuration
@MapperScan(basePackages = "com.weiran.manage.mapper.uaa", sqlSessionFactoryRef = "uaaSqlSessionFactory")
public class UaaSessionFactoryConfiguration {

    @Bean(name = "uaaDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.uaa")
    public DataSource uaaDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 默认使用此事务管理器  如果不想使用 则使用 @Transactional(transactionManager = "transactionManagerWeb") 来指定其他的事务处理器
     */
    @Bean(name = "transactionManagerUaa")
    @Qualifier(value = "uaa")
    @Primary
    public DataSourceTransactionManager transactionManagerUaa() {
        return new DataSourceTransactionManager(uaaDataSource());
    }

    @Bean(name = "uaaSqlSessionFactory")
    public SqlSessionFactory activitySqlSessionFactory(@Qualifier("uaaDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/uaa/**.xml"));
        sfb.setTypeAliasesPackage("com.weiran.manage.dto.uaa");
        sfb.setVfs(SpringBootVFS.class);
        SqlSessionFactory factory = sfb.getObject();
        assert factory != null;
        factory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return factory;
    }

}
