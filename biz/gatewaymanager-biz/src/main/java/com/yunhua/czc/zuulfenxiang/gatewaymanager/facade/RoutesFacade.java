package com.yunhua.czc.zuulfenxiang.gatewaymanager.facade;

import com.yunhua.czc.zuulfenxiang.domain.api.request.PostRoutesRequest;
import com.yunhua.czc.zuulfenxiang.domain.api.response.GetAvailableRouteResponse;
import com.yunhua.czc.zuulfenxiang.gatewaymanager.feign.RoutesClient;
import com.yunhua.czc.zuulfenxiang.gatewaymanager.util.JsonResultUtil;
import com.yunhuakeji.component.base.bean.dto.api.JsonResult;
import com.yunhuakeji.component.base.bean.dto.api.request.BasePageRequest;
import com.yunhuakeji.component.base.exception.BusinessException;
import com.yunhuakeji.component.base.util.JsonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chenzhicong
 * @time 2019/11/24 16:59
 * @description
 */
@Component
@Log4j2
public class RoutesFacade {
    @Autowired
    private RoutesClient routesClient;


    public GetAvailableRouteResponse getAvailableRoute(BasePageRequest request) {
        JsonResult<GetAvailableRouteResponse> resp = routesClient.getAvailableRoute(request);
        log.info("resp={}", JsonUtil.toJsonString(resp));
        if(JsonResultUtil.isSuccess(resp)){
            return resp.getContent();
        }else{
            throw new BusinessException(resp.getMessage());
        }
    }

    public void postRoutes(PostRoutesRequest request) {
        JsonResult<GetAvailableRouteResponse> resp = routesClient.postRoutes(request);
        log.info("resp={}", JsonUtil.toJsonString(resp));
        if(JsonResultUtil.isSuccess(resp)){
            return;
        }else{
            throw new BusinessException(resp.getMessage());
        }
    }
}
