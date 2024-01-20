package com.weiran.common.utils;

import javax.servlet.http.HttpServletRequest;

public class AuthUtil {

    public static String getLoginTokenByRequest(HttpServletRequest request) {
        String authInfo = request.getHeader("Authorization");
        return authInfo.split("Bearer ")[1];
    }

}
