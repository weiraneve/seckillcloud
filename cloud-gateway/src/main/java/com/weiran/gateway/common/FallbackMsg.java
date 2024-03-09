package com.weiran.gateway.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class FallbackMsg<T> implements Serializable {
    private static final long serialVersionUID = -1177183613782210351L;
    private int code;
    private String msg;
    private T data;
}
