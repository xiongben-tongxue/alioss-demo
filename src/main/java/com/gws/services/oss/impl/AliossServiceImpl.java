package com.gws.services.oss.impl;

import com.aliyun.oss.OSSClient;
import com.gws.GwsWebApplication;
import com.gws.services.oss.AliossService;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.UUID;

/**
 * 阿里Oss实现类
 */
@Configuration
@Service
public class AliossServiceImpl implements AliossService{

    static Logger logger = Logger.getLogger(GwsWebApplication.class);

    @Value("${oss.endpoint}")
    private String ossEndpoint;

    @Value("${oss.accessKeyId}")
    private String ossAccessKeyId;

    @Value("${oss.accessKeySecret}")
    private String ossAccessKeySecret;

    @Value("${oss.cdn.https.host}")
    private String cdnHttpsHost;

    private String http = "http://";

    private static String firstKey = "my-first-key";

    @PostConstruct
    public void init() {
        logger.info("ossClient Started");
    }

    /**
     * 上传单个文件
     *
     * @param file   文件
     * @param bucket 存储空间
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file, String bucket) {

        if (file.isEmpty()) {
            return null;
        }

        StringBuilder key = new StringBuilder();

        String fileName = file.getOriginalFilename();
        String postfix = getPostfix(fileName);

        if (!StringUtils.isEmpty(postfix)) {
            key.append(postfix).append("/");
        }
        key.append(UUID.randomUUID().toString());
        if (!StringUtils.isEmpty(postfix)) {
            key.append(".").append(postfix);
        }
        String fixKey = String.valueOf(key);
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(ossEndpoint, ossAccessKeyId, ossAccessKeySecret);

        try {
            ossClient.putObject(bucket, fixKey, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭client
        ossClient.shutdown();

        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer downCdnHttpsHost = stringBuffer.append(http).append(bucket).append(cdnHttpsHost);

        return new StringBuffer().append(downCdnHttpsHost).append("/").append(key).toString();
    }

    private String getPostfix (String file){
        if (null == file) {
            return "";
        }
        int postfixIdx = file.lastIndexOf(".");
        if (-1 == postfixIdx) {
            return "";
        } else {
            return file.substring(postfixIdx + 1);
        }
    }
}
