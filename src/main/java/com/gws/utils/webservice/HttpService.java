package com.gws.utils.webservice;

import com.gws.utils.webservice.impl.CommonResponse;

import java.util.Map;

/**
 * @author yangjh
 */
public interface HttpService {

    /**
     * 请使用这个新的来调用新的服务
     *
     * @param service
     * @param methodName
     * @param params
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends CommonResponse> T call(String service, String methodName, Map<String, Object> params, Class<T> clazz);


}
