//package com.weiran.test;
//
//import com.weiran.mission.rocketmq.MessageSender;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class SpringbootRocketmqApplicationTests {
//
//    @Autowired
//    private MessageSender sender;
//
//    @Test
//    public void syncSendTest() {
//        sender.syncSend();
//    }
//
//    @Test
//    public void asyncSendTest() throws Exception {
//        sender.asyncSend();
//    }
//
//    @Test
//    public void sendOneWayTest() {
//        sender.sendOneWay();
//    }
//
//    @Test
//    public void sendOneWayOrderlyTest() {
//        sender.sendOneWayOrderly();
//    }
//
//}