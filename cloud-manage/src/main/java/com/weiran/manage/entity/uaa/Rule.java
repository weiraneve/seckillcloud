package com.weiran.manage.entity.uaa;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Rule {

    // 主键
    private Long id;

    // 规定逾期年份
    private Integer exceedYear;

    // 规定逾期次数
    private Integer exceedCount;

    // 规定逾期金额
    private Integer exceedMoney;

    // 规定逾期天数之内还清
    private Integer exceedDay;

    // 限定客户年龄
    private Integer limitAge;

    // 创建时间
    private LocalDateTime createdAt;

    // 更新时间
    private LocalDateTime updatedAt;

}