package com.yunhua.czc.zuulfenxiang.gateway.bean.holder;

import com.yunhua.czc.zuulfenxiang.gateway.bean.entity.GrayReleaseConfig;
import com.yunhua.czc.zuulfenxiang.gateway.helper.ServiceHelper;
import com.yunhua.czc.zuulfenxiang.gateway.service.GrayReleaseConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenzhicong
 * @time 2019/12/17 15:32
 * @description
 */
@Component
public class GrayReleaseInfoHolder {

    @Autowired
    private GrayReleaseConfigService grayReleaseConfigService;


    private  Map<String, GrayReleaseConfig> grayReleaseConfigs =
            new ConcurrentHashMap<>();


    public  Map<String, GrayReleaseConfig>  getGrayReleaseConfigs(){
        return grayReleaseConfigs;
    }


    public void refresh(){
        grayReleaseConfigs.clear();
        List<GrayReleaseConfig> results = ServiceHelper.ofService(grayReleaseConfigService)
                .selectAll(null);
        for(GrayReleaseConfig grayReleaseConfig : results) {
            grayReleaseConfigs.put(grayReleaseConfig.getPath(), grayReleaseConfig);
        }
    }









}
