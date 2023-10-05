package com.weiran.mission.utils.qiniu;


import com.qiniu.common.QiniuException;
import com.qiniu.storage.model.DefaultPutRet;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.validation.CustomValidation;
import com.weiran.mission.config.qiniu.QiNiuProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageScalaKit {

    private final QiNiuUploadKit qiNiuUploadKit;
    private final QiNiuProperties qiNiuProperties;

    // 上传
    public ImageKit upload(MultipartFile file, String prefix) {
        ImageKit imageKit = null;
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String extension = fileExtension(file);
            DefaultPutRet ret = qiNiuUploadKit.upload(file.getBytes(), prefix + "/" + uuid + extension);
            imageKit = initResult(ret);
        } catch (IOException e) {
            log.error(e.toString());
            CustomValidation.invalid(ResponseEnum.IMAGE_UPLOAD_FAIL);
        }
        return imageKit;
    }

    // 删除
    public void delete(String imgUrl) {
        String key = ImageKit.getKey(imgUrl, qiNiuProperties.getDomain());
        try {
            qiNiuUploadKit.delete(key);
        } catch (QiniuException e) {
            log.error(e.toString());
        }
    }

    private ImageKit initResult(DefaultPutRet ret) {
        ImageKit imageKit = new ImageKit();
        imageKit.setHash(ret.hash);
        imageKit.setKey(ret.key);
        imageKit.setUrl(qiNiuProperties.getDomain() + ret.key);
        return imageKit;
    }

    // 获得文件后缀名 如.jpg
    private String fileExtension(MultipartFile file) {
        int begin = Objects.requireNonNull(file.getOriginalFilename()).indexOf(".");
        if (begin == -1 || file.getOriginalFilename().length() == 0) {
            return "";
        }
        return file.getOriginalFilename().substring(begin);
    }

}
