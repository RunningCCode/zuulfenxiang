package com.yunhua.czc.zuulfenxiang.gatewayconfig.client;


import com.yunhua.czc.zuulfenxiang.api.RoutesApi;
import com.yunhua.czc.zuulfenxiang.domain.api.request.PostRoutesRequest;
import com.yunhua.czc.zuulfenxiang.domain.api.response.GetAvailableRouteResponse;
import com.yunhua.czc.zuulfenxiang.gatewayconfig.facade.RoutesFacade;
import com.yunhuakeji.component.base.annotation.doc.ApiInfo;
import com.yunhuakeji.component.base.bean.dto.api.JsonResult;
import com.yunhuakeji.component.base.bean.dto.api.request.BasePageRequest;
import com.yunhuakeji.component.base.enums.ApiStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author chenzhicong
 * @time 2019/11/23 22:02
 * @description
 */

@RestController
public class RoutesClientImpl implements RoutesApi {
    @Autowired
    private RoutesFacade routesFacade;

    //@ApiInfo(state = ApiStateEnum.IMPLEMENTED, name = "添加路由")
    @Override
    public JsonResult postRoutes(@RequestBody PostRoutesRequest request) {
        routesFacade.postRoutes(request);
        return new JsonResult();
    }
    //@ApiInfo(state = ApiStateEnum.IMPLEMENTED, name = "获取路由")
    @Override
    public JsonResult<GetAvailableRouteResponse> getAvailableRoute(@RequestBody BasePageRequest request) {
        return  new JsonResult<GetAvailableRouteResponse>().content(routesFacade.getAvailableRoute(request));
    }




}
