package com.yunhua.czc.zuulfenxiang.gatewayconfig.facade;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunhua.czc.zuulfenxiang.domain.api.request.PostRoutesRequest;
import com.yunhua.czc.zuulfenxiang.domain.api.response.GetAvailableRouteResponse;
import com.yunhua.czc.zuulfenxiang.domain.entity.GatewayApiRoute;
import com.yunhua.czc.zuulfenxiang.gatewayconfig.service.GatewayApiRouteService;
import com.yunhua.czc.zuulfenxiang.gatewayconfig.util.PageUtil;
import com.yunhuakeji.component.base.bean.dto.api.request.BasePageRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenzhicong
 * @time 2019/11/24 0:06
 * @description
 */
@Component
@Log4j2
public class RoutesFacade {
    @Autowired
    private GatewayApiRouteService gatewayApiRouteService;
    public void postRoutes(PostRoutesRequest request){
        GatewayApiRoute gatewayApiRoute = new GatewayApiRoute();
        BeanUtils.copyProperties(request,gatewayApiRoute);
        log.info(gatewayApiRoute);
        gatewayApiRouteService.insertSelective(gatewayApiRoute);
    }

    public GetAvailableRouteResponse getAvailableRoute(BasePageRequest request) {
        PageHelper.startPage(request.getPageNum(),request.getPageSize());
        List<GatewayApiRoute> list = gatewayApiRouteService.getAvailableRoute();
        PageInfo<GatewayApiRoute> info = new PageInfo<>(list);
        return PageUtil.info2BasePageResponse(info,new GetAvailableRouteResponse());
    }
}
