package com.yunhua.czc.zuulfenxiang.gateway.bean.extension;

import com.yunhua.czc.zuulfenxiang.gateway.bean.entity.GatewayApiRoute;
import com.yunhua.czc.zuulfenxiang.gateway.service.GatewayApiRouteService;
import com.yunhuakeji.component.base.enums.entity.YesNoCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author chenzhicong
 * @time 2019/11/23 15:04
 * @description 动态路由定位器
 */
@Log4j2
@Component

public class DynamicRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {


    private ZuulProperties properties;
    private ServerProperties server;
    private GatewayApiRouteService gatewayApiRouteService;

    @Autowired
    public DynamicRouteLocator(ServerProperties server, ZuulProperties properties, GatewayApiRouteService gatewayApiRouteService) {
        super(server.getServletPrefix(), properties);
        this.properties = properties;
        this.server = server;
        this.gatewayApiRouteService = gatewayApiRouteService;
    }


    @Override
    public void refresh() {
        doRefresh();
    }

    /**
     * 定时获取路由表的方法
     */
    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        // 加载application.yml中的路由表
        routesMap.putAll(super.locateRoutes());
        // 加载db中的路由表
        routesMap.putAll(locateRoutes4DB());
        // 统一处理一下路由path的格式
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    private Map<String, ZuulProperties.ZuulRoute> locateRoutes4DB() {
        List<GatewayApiRoute> gatewayApiRoutes = gatewayApiRouteService.getAvailableRoute();
        Map<String, ZuulProperties.ZuulRoute> routes = gatewayApiRoutes.stream().filter(r -> {
                    if (StringUtils.isEmpty(r.getPath())) {
                        return false;
                    }
                    if (StringUtils.isEmpty(r.getServiceId()) && StringUtils.isEmpty(r.getUrl())) {
                        return false;
                    }
                    return true;
                }
        ).map(r -> {
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            zuulRoute.setId(r.getPath());
            zuulRoute.setPath(r.getPath());
            zuulRoute.setRetryable(YesNoCodeEnum.YES.equals(r.getRetryable()));
            zuulRoute.setServiceId(r.getServiceId());
            zuulRoute.setUrl(r.getUrl());
            zuulRoute.setStripPrefix(YesNoCodeEnum.YES.equals(r.getStripPrefix()));
            return zuulRoute;
        }).collect(Collectors.toMap(z -> z.getPath(), Function.identity()));
        return routes;
    }


}
