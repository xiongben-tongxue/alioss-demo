package com.gws.controllers.oss;

import com.gws.controllers.BaseController;
import com.gws.controllers.JsonResult;
import com.gws.services.oss.AliossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

}
