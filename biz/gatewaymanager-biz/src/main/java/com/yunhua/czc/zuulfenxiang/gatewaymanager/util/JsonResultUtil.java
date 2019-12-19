package com.yunhua.czc.zuulfenxiang.gatewaymanager.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunhuakeji.component.base.bean.dto.api.JsonResult;
import com.yunhuakeji.component.base.bean.dto.api.response.BaseResponse;
import com.yunhuakeji.component.base.enums.BaseResultCodeEnum;

/**
 * @author chenzhicong
 * @time 2019/12/16 10:26
 * @description
 */
public class JsonResultUtil {
    @JsonIgnore
    public static boolean isSuccess(JsonResult result){
        return result.getCode().equals(BaseResultCodeEnum.SUCCESS.getCode()) ? true : false;
    }
}
