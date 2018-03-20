package com.gws.controllers.oss;

import com.gws.controllers.BaseController;
import com.gws.controllers.JsonResult;
import com.gws.services.oss.AliossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * ucloud的处理文件的接口
 */
@RestController
@RequestMapping("/oss/file/")
public class OssController extends BaseController {

    @Autowired
    private AliossService aliossService;

    /**
     * 单文件上传
     * @param file
     * @param bucket
     * @return
     */
    @RequestMapping("uploadFile")
    public JsonResult uploadFile(@RequestParam("file") MultipartFile file, String bucket){

        String result = aliossService.uploadFile(file, bucket);

        return success(result);
    }

    /**
     *
     * OSSObject实例包含文件所在的存储空间（Bucket）、文件的名称、Object Metadata以及一个输入流；
     * 现将流返回。
     * 通过操作输入流将文件的内容读取到文件或者内存中。而Object Metadata包含ETag、HTTP Header及自定义的元信息；
     * @param bucket
     * @param key
     * @return
     */
    @RequestMapping("downByStream")
    public JsonResult downByStream(String bucket,String key) throws IOException {

        InputStream result = aliossService.downByStream(bucket, key);

        return success(result);
    }

    /**
     * 文件的批量上传
     * @param files
     * @param bucket
     * @return
     */
    @RequestMapping("uploadFiles")
    public JsonResult uploadFiles(@RequestParam("files") MultipartFile[] files, String bucket){

        List<String> result = aliossService.uploadFiles(files,bucket);

        return success(result);
    }


}
