package com.yunhua.czc.zuulfenxiang.gatewaymanager.feign;
import com.yunhua.czc.zuulfenxiang.api.RoutesApi;
import com.yunhua.czc.zuulfenxiang.gatewaymanager.feign.fallback.RoutesClientFallback;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author chenzhicong
 * @time 2019/11/24 14:54
 * @description
 */
@FeignClient(value = "gatewayconfig",fallback = RoutesClientFallback.class)
public interface RoutesClient extends RoutesApi {
}
