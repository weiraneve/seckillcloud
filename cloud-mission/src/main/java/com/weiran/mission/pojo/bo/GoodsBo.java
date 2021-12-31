package com.weiran.mission.pojo.bo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Api("商品业务对象")
public class GoodsBo {

	@ApiModelProperty("商品id")
	private Long id;

	@ApiModelProperty("商品名字")
	private String goodsName;

	@ApiModelProperty("商品标题")
	private String goodsTitle;

	@ApiModelProperty("商品图片存放位置")
	private String goodsImg;

	@ApiModelProperty("商品原本价")
	private BigDecimal goodsPrice;

	@ApiModelProperty("商品库存")
	private Integer goodsStock;

	@ApiModelProperty("生成时间")
	private Date createDate;

	@ApiModelProperty("更新时间")
	private Date updateDate;

	@ApiModelProperty("商品细节")
	private String goodsDetail;

	@ApiModelProperty("秒杀价")
	private BigDecimal seckillPrice;

	@ApiModelProperty("商品秒杀库存")
	private Integer stockCount;

	@ApiModelProperty("秒杀开始时间")
	private Date startDate;

	@ApiModelProperty("秒杀结束时间")
	private Date endDate;

}
