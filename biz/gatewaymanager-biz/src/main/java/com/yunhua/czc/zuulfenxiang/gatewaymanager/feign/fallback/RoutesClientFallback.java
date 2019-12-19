package com.yunhua.czc.zuulfenxiang.gatewaymanager.feign.fallback;

import com.yunhua.czc.zuulfenxiang.domain.api.request.PostRoutesRequest;
import com.yunhua.czc.zuulfenxiang.domain.api.response.GetAvailableRouteResponse;
import com.yunhua.czc.zuulfenxiang.gatewaymanager.feign.RoutesClient;
import com.yunhuakeji.component.base.bean.dto.api.JsonResult;
import com.yunhuakeji.component.base.bean.dto.api.request.BasePageRequest;
import com.yunhuakeji.component.base.util.JsonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author chenzhicong
 * @time 2019/11/24 2:28
 * @description
 */
//@Component
@Log4j2
public class RoutesClientFallback implements RoutesClient {
    @Override
    public JsonResult postRoutes(PostRoutesRequest request) {
        return new JsonResult();
    }

    @Override
    public JsonResult<GetAvailableRouteResponse> getAvailableRoute(BasePageRequest request) {
        log.info("getAvailableRoute被降级，请求：{}", JsonUtil.toJsonString(request));
        GetAvailableRouteResponse resp = new GetAvailableRouteResponse();
        resp.setPageNum(request.getPageNum());
        resp.setPageSize(request.getPageSize());
        resp.setTotal(0L);
        resp.setList(new ArrayList<>());
        return new JsonResult<GetAvailableRouteResponse>().content(resp);
    }



}
