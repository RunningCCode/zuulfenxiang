package com.yunhua.czc.zuulfenxiang.domain.api.response;

import com.yunhua.czc.zuulfenxiang.domain.entity.GatewayApiRoute;
import com.yunhuakeji.component.base.bean.dto.api.response.PageResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chenzhicong
 * @time 2019/11/24 3:14
 * @description
 */
@Getter
@Setter
@ToString
@ApiModel("获取可用路由请求")
public class GetAvailableRouteResponse extends PageResponse<GatewayApiRoute> {
}
