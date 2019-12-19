package com.yunhua.czc.zuulfenxiang.gatewayconfig.util;


import com.github.pagehelper.PageInfo;
import com.yunhuakeji.component.base.bean.dto.api.response.PageResponse;

import java.io.Serializable;

/**
 * @author chenzhicong
 * @time 2019/5/30 18:47
 * @description
 */
public class PageUtil {
    /**
      * 避免重复代码,通过pageInfo获取分页返回对象
      */
    public static <T extends Serializable,E extends PageResponse<T>> E info2BasePageResponse(PageInfo<T> info, E response){
        response.setList(info.getList());
        response.setPageNum(info.getPageNum());
        response.setPageSize(info.getPageSize());
        response.setTotal(info.getTotal());
        return response;
    }

    /**
      * 避免重复代码，通过info获取一个没有list但其他相同的分页返回对象，
      *  因为有时候会对使用分页插件查出来的实体转换为vo。
      */
    public static <T extends Serializable,E extends PageResponse<T>> PageResponse<T> getInitPageFromPageInfoNotList(PageInfo<E> info, E response){
        response.setPageNum(info.getPageNum());
        response.setPageSize(info.getPageSize());
        response.setTotal(info.getTotal());
        return response;
    }


}
