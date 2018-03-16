package com.gws.services.oss;

import org.springframework.web.multipart.MultipartFile;

/**
 * 阿里OssService实现类
 */

public interface AliossService {

    /**
     * 上传单个文件
     * @param file 文件
     * @param bucket 存储空间
     * @return
     */
    String uploadFile(MultipartFile file, String bucket);
}
