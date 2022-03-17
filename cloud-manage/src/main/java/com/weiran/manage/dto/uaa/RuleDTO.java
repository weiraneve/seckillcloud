package com.weiran.manage.dto.uaa;

import lombok.Data;

@Data
public class RuleDTO {

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

}