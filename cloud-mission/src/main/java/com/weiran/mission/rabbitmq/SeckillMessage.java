package com.weiran.mission.rabbitmq;


import com.weiran.mission.entity.User;
import lombok.Data;

/**
 * RabbitMQ传递的消息对象
 */
@Data
public class SeckillMessage {

	private User user;

	private long goodsId;
}
