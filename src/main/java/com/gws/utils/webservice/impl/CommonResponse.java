package com.gws.utils.webservice.impl;

import lombok.Data;

import java.io.Serializable;

/**
 * api请求统一响应数据类型
 * Created by yangjh on 7/18/16.
 */
@Data
public class CommonResponse implements Serializable {

    private Long requestId;

    private String code;

    private String message;
    
}
