package com.gws.services.oss.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.gws.GwsWebApplication;
import com.gws.services.oss.AliossService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

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

        Map<String,Object> params = new HashMap<>();
        params.put("file", JSONObject.toJSONString(file));
        params.put("bucket",bucket);

        return null;

    }

    /**
     * 流式下载
     *
     * @param key
     * @return
     */
    @Override
    public InputStream downByStream(String bucket,String key) throws IOException {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(ossEndpoint, ossAccessKeyId, ossAccessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucket, key);

        // 读Object内容
        System.out.println("Object content:");
        InputStream inputStream = ossObject.getObjectContent();
        //关闭流
        inputStream.close();
        // 关闭client
        ossClient.shutdown();
        return inputStream;
    }

    /**
     * 批量上传
     * 上传多个文件
     *
     * @param files
     * @param bucket
     * @return
     */
    @Override
    public List<String> uploadFiles(MultipartFile[] files, String bucket) {

        if (null == files || StringUtils.isEmpty(bucket)){
            return Collections.EMPTY_LIST;
        }

        List<String> result = new ArrayList<>();

        for (MultipartFile file : files){
            String download = uploadFile(file,bucket);
            result.add(download);
        }

        return CollectionUtils.isEmpty(result) ? Collections.EMPTY_LIST : result;
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
