package com.weiran.mission.rabbitmq;

import lombok.Data;

@Data
public class SeckillMessage {

    private long userId;

    private long goodsId;
}
