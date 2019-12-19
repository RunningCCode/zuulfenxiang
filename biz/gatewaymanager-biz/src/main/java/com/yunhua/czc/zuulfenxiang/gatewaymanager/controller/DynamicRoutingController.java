package com.yunhua.czc.zuulfenxiang.gatewaymanager.controller;

import com.yunhua.czc.zuulfenxiang.domain.api.request.PostRoutesRequest;
import com.yunhua.czc.zuulfenxiang.domain.api.response.GetAvailableRouteResponse;
import com.yunhua.czc.zuulfenxiang.gatewaymanager.facade.RoutesFacade;
import com.yunhuakeji.component.base.annotation.doc.ApiInfo;
import com.yunhuakeji.component.base.bean.dto.api.JsonResult;
import com.yunhuakeji.component.base.bean.dto.api.request.BasePageRequest;
import com.yunhuakeji.component.base.enums.ApiStateEnum;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author chenzhicong
 * @time 2019/11/24 16:59
 * @description
 */
@RestController
@Api(tags = "后台动态路由接口")
@RequestMapping("/routesOfDynamic")
public class DynamicRoutingController {
    @Autowired
    private RoutesFacade routesFacade;

    @ApiInfo(state = ApiStateEnum.IMPLEMENTED, name = "获取所有路由")
    @GetMapping("")
    public JsonResult<GetAvailableRouteResponse> getAvailableRoutes(@Valid BasePageRequest request){
        return new JsonResult<GetAvailableRouteResponse>().content(routesFacade.getAvailableRoute(request));
    }


    @ApiInfo(state = ApiStateEnum.IMPLEMENTED, name = "添加路由")
    @PostMapping("")
    public JsonResult postRoutes(@Valid PostRoutesRequest request){
        routesFacade.postRoutes(request);
        return new JsonResult<>();
    }





}
