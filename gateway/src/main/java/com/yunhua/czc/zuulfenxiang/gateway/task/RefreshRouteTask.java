package com.yunhua.czc.zuulfenxiang.gateway.task;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
@Log4j2
public class RefreshRouteTask {
	
	@Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    @Qualifier("dynamicRouteLocator")
    private RouteLocator routeLocator;

	/**
	  * 5秒刷新一次路由
	  */
    @Scheduled(fixedRate = 5000)
    public void refreshRoute() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);
    }
    
}