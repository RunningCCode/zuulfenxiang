package com.yunhua.czc.zuulfenxiang.gateway.bean.extension;

import com.yunhua.czc.zuulfenxiang.gateway.service.GatewayApiRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author chenzhicong
 * @time 2019/11/24 0:24
 * @description
 */
@Component
@Primary
public class SwaggerResourceProvider  implements SwaggerResourcesProvider {
    @Autowired
    private ZuulProperties properties;
    @Autowired
    private GatewayApiRouteService gatewayApiRouteService;


    @Override
    @SuppressWarnings("all")
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        Stream.concat(this.properties.getRoutes().values().stream().map(r -> r.getServiceId())
                , gatewayApiRouteService.getAvailableRoute().stream().filter(r -> {
                            if (StringUtils.isEmpty(r.getPath())) {
                                return false;
                            }
                            if (StringUtils.isEmpty(r.getServiceId()) && StringUtils.isEmpty(r.getUrl())) {
                                return false;
                            }
                            return true;
                        }
                ).map(r -> r.getServiceId())).forEach(
                s -> {
                    resources.add(swaggerResource(s, "/" + s + "/v2/api-docs", "2.0"));
                }
        );
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}
