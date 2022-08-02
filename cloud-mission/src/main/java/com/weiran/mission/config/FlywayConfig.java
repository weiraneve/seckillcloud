package com.weiran.mission.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 数据库初始化 flyway配置类
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
public class FlywayConfig {

    private final DataSource dataSource;

    @Value("${spring.flyway.locations}")
    private String SQL_LOCATION;

    @Value("${spring.flyway.table}")
    private String VERSION_TABLE;

    @Value("${spring.flyway.baseline-on-migrate}")
    private boolean BASELINE_ON_MIGRATE;

    @Value("${spring.flyway.out-of-order}")
    private boolean OUT_OF_ORDER;

    @Value("${spring.flyway.validate-on-migrate}")
    private boolean VALIDATE_ON_MIGRATE;

    @PostConstruct
    public void migrateFlyway() {
        log.info("调用数据库生成工具");
        SQL_LOCATION = SQL_LOCATION.split("/")[0]; // 将路径转换
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        Map<String, DataSource> dataSources = ds.getDataSources();
        try {
            dataSources.forEach((k, v) -> {
                log.info("正在执行多数据源生成数据库文件 " + k);
                Flyway flyway = Flyway.configure()
                        .dataSource(v)
                        .locations(SQL_LOCATION + "/" + k)
                        .baselineOnMigrate(BASELINE_ON_MIGRATE)
                        .table(VERSION_TABLE)
                        .outOfOrder(OUT_OF_ORDER)
                        .validateOnMigrate(VALIDATE_ON_MIGRATE)
                        .load();
                flyway.migrate();
            });
        }  catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }

}
