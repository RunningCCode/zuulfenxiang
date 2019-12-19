package com.yunhua.czc.zuulfenxiang.api;

import com.yunhua.czc.zuulfenxiang.domain.api.request.PostRoutesRequest;
import com.yunhua.czc.zuulfenxiang.domain.api.response.GetAvailableRouteResponse;
import com.yunhuakeji.component.base.bean.dto.api.JsonResult;
import com.yunhuakeji.component.base.bean.dto.api.request.BasePageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author chenzhicong
 * @time 2019/11/23 18:33
 * @description
 */
public interface RoutesApi {
    @PostMapping(value = "/routes",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult postRoutes(@RequestBody PostRoutesRequest request);

    @GetMapping(value = "/routes",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<GetAvailableRouteResponse> getAvailableRoute(@RequestBody BasePageRequest request);

}
