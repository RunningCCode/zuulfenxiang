package com.yunhua.czc.zuulfenxiang.gatewayconfig.service.impl;


import com.yunhua.czc.zuulfenxiang.domain.entity.GatewayApiRoute;
import com.yunhua.czc.zuulfenxiang.gatewayconfig.dao.GatewayApiRouteDao;
import com.yunhua.czc.zuulfenxiang.gatewayconfig.service.GatewayApiRouteService;
import com.yunhuakeji.component.base.enums.entity.StateEnum;
import com.yunhuakeji.component.base.enums.entity.YesNoCodeEnum;
import com.yunhuakeji.component.mybatis.service.base.impl.BaseServiceImpl;
import com.yunhuakeji.component.mybatis.util.MapperUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author chenzhicong
 * @version $Id: GatewayApiRouteServiceImpl.java, v 0.1 2019-11-23 16:41:33 YH-CZC Exp $$
 */
@Service
public class GatewayApiRouteServiceImpl extends BaseServiceImpl<GatewayApiRoute, GatewayApiRouteDao> implements GatewayApiRouteService {


    @Override
    //@RedisCache(keyPrefix = RedisCachePrifix.SERVICE_ROUTE_CACHE,ttl = 60L * 60L * 24L)
    public List<GatewayApiRoute> getAvailableRoute() {
        LinkedHashMap<String,Object> whereMap = new LinkedHashMap<>();
        whereMap.put("enabled", YesNoCodeEnum.YES);
        whereMap.put("state", StateEnum.VALID);
        Example example = MapperUtil.packageGeneralExample(whereMap,null,GatewayApiRoute.class);
        return this.selectByExample(example);
    }
}