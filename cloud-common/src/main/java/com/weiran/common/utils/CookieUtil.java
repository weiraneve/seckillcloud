package com.weiran.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类
 */
@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = "localhost";

    private final static String COOKIE_NAME = "seckill_login_token";

    /**
     * 从请求头信息中获得Cookies，并对比cookies的name是否为设置的token，是则返回cookies的value
     *
     * HttpServletRequest 当客户端通过HTTP协议访问服务器时，HTTP请求头中的所有信息都封装在这个对象中
     */
    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookie_list = request.getCookies();
        if (cookie_list != null) {
            for (Cookie ck : cookie_list) {
//                log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if (ck.getName().equals(COOKIE_NAME)) {
//                    log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    // 跨域
    // X:domain=".hfbin.cn"
    // a:A.hfbin.cn            cookie:domain=A.hfbin.cn;path="/"
    // b:B.hfbin.cn            cookie:domain=B.hfbin.cn;path="/"
    // c:A.hfbin.cn/test/cc    cookie:domain=A.hfbin.cn;path="/test/cc"
    // d:A.hfbin.cn/test/dd    cookie:domain=A.hfbin.cn;path="/test/dd"
    // e:A.hfbin.cn/test       cookie:domain=A.hfbin.cn;path="/test"

    /**
     * 把登陆token写入response
     */
    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/"); // 代表设置在根目录
        cookie.setHttpOnly(true);
        // 单位是秒。
        // 如果这个maxAge不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        cookie.setMaxAge(60 * 60 * 24 * 365); // 如果是-1，代表永久
//        log.info("write cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
        response.addCookie(cookie);
    }

    /**
     * 删除返回客户端的response里的cookies
     */
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookie_list = request.getCookies();
        if (cookie_list != null) {
            for (Cookie ck : cookie_list) {
                if (ck.getName().equals(COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0); // 设置成0，代表删除此cookie。
                   // log.info("del cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }
}
