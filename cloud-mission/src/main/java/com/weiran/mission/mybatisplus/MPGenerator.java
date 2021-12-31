//package com.weiran.mission.mybatisplus;
//
//import com.baomidou.mybatisplus.generator.FastAutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.OutputFile;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//
//import java.sql.SQLException;
//import java.util.Collections;
//
//public class MPGenerator {
//
//    /**
//     * 数据源配置
//     */
//    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
//            .Builder("jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai", "root", "123456");
//
//    /**
//     * 执行 run
//     */
//    public static void main(String[] args) throws SQLException {
//        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
//                .globalConfig(builder -> { // 全局配置
//                    builder.author("") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride()     // 覆盖已生成文件
//                            .outputDir("generator"); // 指定输出目录
//                })
//                .packageConfig(builder -> { // 包配置
//                    builder.parent("") // 设置父包名
//                            .moduleName("module") // 设置父包模块名
//                            .entity("entity")
//                            .service("manager")
//                            .serviceImpl("manager.impl")
//                            .mapper("mapper")
//                            .controller("controller")
//                            .other("other")
//                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "generator/mapperXml")); // 设置mapperXml生成路径
//                })
//                .strategyConfig(builder -> { // 策略配置
//                    builder.addInclude("goods", "order_info", "seckill_goods", "seckill_order", "user") // 设置需要生成的表名
//                            .addTablePrefix(); // 设置过滤表前缀
//                })
//                .strategyConfig(builder -> { // 设置Entity包策略配置
//                    builder.entityBuilder()
//                            .enableLombok(); // 开启lombok模型
//                })
//                .strategyConfig(builder -> { // 设置Service包策略配置
//                    builder.serviceBuilder()
//                            .formatServiceFileName("%sManager")
//                            .formatServiceImplFileName("%sManagerImp");
//                })
//                // 模板配置、注入配置
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
//                .execute();
//    }
//}
