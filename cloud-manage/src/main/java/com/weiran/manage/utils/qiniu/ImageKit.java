package com.weiran.manage.utils.qiniu;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class ImageKit {

    public static final String THUMB_SUFFIX = "-thumb";
    public static final String MIDDLE_SUFFIX = "-middle";
    private String url;
    private String key;
    private String hash;
    private int code = 0;     // 0 标识正常, 1 标识非法

    public static String getThumb(String imgUrl) {
        return imgUrl + THUMB_SUFFIX;
    }

    public static String getMiddle(String imgUrl) {
        return imgUrl + MIDDLE_SUFFIX;
    }

    public static String getOrigin(String domain, String key) {
        return domain + key;
    }

    static String getKey(String imgUrl, String domain) {
        return imgUrl.replace(domain.concat("/"), "");
    }

    public static boolean isNotImageFile(MultipartFile file) {
        return !isImageFile(file);
    }

    private static boolean isImageFile(MultipartFile file) {
        if (file == null) {
            return false;
        }
        String suffix = file.getOriginalFilename();
        if (suffix == null) {
            return false;
        }
        return suffix.contains("png") || suffix.contains("jpg") ||
                suffix.contains("jpeg") || suffix.contains("gif") || suffix.contains("bmp");
    }

}
