package com.gws.services.oss;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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

    /**
     * 流式下载
     * @param bucket 存储空间
     * @param key 存储的key
     * @return
     */
    InputStream downByStream(String bucket, String key) throws IOException;

    /**
     *  @param bucket 存储的空间
     * @param key 存储的key
     * @param loaclPath 本地保存的文件地址
     */
    void downFileToLocalPath(String bucket, String key,String loaclPath);
}
