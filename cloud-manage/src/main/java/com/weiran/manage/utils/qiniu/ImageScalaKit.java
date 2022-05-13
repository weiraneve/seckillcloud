package com.weiran.manage.utils.qiniu;


import com.weiran.manage.config.qiniu.QiNiuProperties;
import com.weiran.common.exception.CustomizeException;
import com.weiran.common.enums.ResponseEnum;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.model.DefaultPutRet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageScalaKit {

    private final QiNiuUploadKit qiNiuUploadKit;
    private final QiNiuProperties qiNiuProperties;

    // 上传
    public ImageKit upload(MultipartFile file, String prefix) {
        ImageKit imageKit;
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String extension = fileExtension(file);
            DefaultPutRet ret = qiNiuUploadKit.upload(file.getBytes(), prefix + "/" + uuid + extension);
            imageKit = initResult(true, ret);
            return imageKit;
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomizeException(ResponseEnum.IMAGE_UPLOAD_FAIL);
        }

    }

    // 删除
    public boolean delete(String imgUrl) {
        String key = ImageKit.getKey(imgUrl, qiNiuProperties.getDomain());
        try {
            qiNiuUploadKit.delete(key);
            return true;
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ImageKit initResult(boolean isSuccess, DefaultPutRet ret) {
        ImageKit imageKit = new ImageKit();
        imageKit.setHash(ret.hash);
        imageKit.setKey(ret.key);
        imageKit.setUrl(qiNiuProperties.getDomain() + ret.key);
        return imageKit;
    }

    // 获得文件后缀名 如.jpg
    private String fileExtension(MultipartFile file) {
        int begin = file.getOriginalFilename().indexOf(".");
        if (begin == -1 || file.getOriginalFilename().length() == 0) {
            return "";
        }
        return file.getOriginalFilename().substring(begin);
    }

}
