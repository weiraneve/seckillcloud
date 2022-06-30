package com.weiran.common.obj;


import lombok.Data;

/**
 * MQ传递的消息对象
 */
@Data
public class SeckillMessage {

	private long userId;

	private long goodsId;
}
