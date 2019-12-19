package com.yunhua.czc.zuulfenxiang.gatewayconfig.service;


import com.yunhua.czc.zuulfenxiang.domain.entity.GatewayApiRoute;
import com.yunhuakeji.component.mybatis.service.base.BaseService;

import java.util.List;

/**
 *
 * @author chenzhicong
 * @version $Id: GatewayApiRouteService.java, v 0.1 2019-11-23 16:41:21 YH-CZC Exp $$
 */
public interface  GatewayApiRouteService extends BaseService<GatewayApiRoute> {


    List<GatewayApiRoute> getAvailableRoute();



}