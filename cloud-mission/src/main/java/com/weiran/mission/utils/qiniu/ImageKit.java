package com.weiran.mission.utils.qiniu;

import lombok.Data;

@Data
public class ImageKit {

    public static final String THUMB_SUFFIX = "-thumb";
    public static final String MIDDLE_SUFFIX = "-middle";
    private String url;
    private String key;
    private String hash;
    private int code = 0;     // 0 标识正常, 1 标识非法

    static String getKey(String imgUrl, String domain) {
        return imgUrl.replace(domain.concat("/"), "");
    }

}
