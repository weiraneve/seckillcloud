package com.weiran.test;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class TestDemo {

    @org.junit.Test
    public void say() {
        System.out.println(LocalDateTime.now());
    }

    @org.junit.Test
    public void test00() {
        String str = "18077200001";
        System.out.println(str.substring(7));
    }

}
