package com.yunhua.czc.zuulfenxiang.gateway.task;

import com.yunhua.czc.zuulfenxiang.gateway.bean.holder.GrayReleaseInfoHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author chenzhicong
 * @time 2019/12/17 11:47
 * @description
 */
@Component
@Configuration
public class GrayReleaseSyncTask {

    @Autowired
    private GrayReleaseInfoHolder grayReleaseInfoHolder;



    /**
      * 5秒从数据库同步一次灰度发布表到内存
      */
    @Scheduled(fixedRate = 5000)
    public void refreshRoute() {
        grayReleaseInfoHolder.refresh();
    }

}
