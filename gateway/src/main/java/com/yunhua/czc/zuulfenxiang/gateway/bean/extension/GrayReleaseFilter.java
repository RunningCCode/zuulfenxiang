package com.yunhua.czc.zuulfenxiang.gateway.bean.extension;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yunhua.czc.zuulfenxiang.gateway.bean.entity.GrayReleaseConfig;
import com.yunhua.czc.zuulfenxiang.gateway.bean.holder.GrayReleaseInfoHolder;
import com.yunhuakeji.component.base.enums.entity.YesNoCodeEnum;
//import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


/**
 * @author chenzhicong
 * @time 2019/12/17 15:31
 * @description
 */
@SuppressWarnings("unused")
@Configuration
@Log4j2
public class GrayReleaseFilter  extends ZuulFilter {

    @Autowired
    private GrayReleaseInfoHolder grayReleaseInfoHolder;

    private  SecureRandom SECURE_RANDOM = SecureRandom.getInstance("SHA1PRNG");

    public GrayReleaseFilter() throws NoSuchAlgorithmException {
    }

    /**
     * 指定该Filter执行的顺序（Filter从小到大执行）
     * DEBUG_FILTER_ORDER = 1;
     * FORM_BODY_WRAPPER_FILTER_ORDER = -1;
     * PRE_DECORATION_FILTER_ORDER = 5;
     * RIBBON_ROUTING_FILTER_ORDER = 10;
     * SEND_ERROR_FILTER_ORDER = 0;
     * SEND_FORWARD_FILTER_ORDER = 500;
     * SEND_RESPONSE_FILTER_ORDER = 1000;
     * SIMPLE_HOST_ROUTING_FILTER_ORDER = 100;
     * SERVLET_30_WRAPPER_FILTER_ORDER = -2;
     * SERVLET_DETECTION_FILTER_ORDER = -3;
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }
    /**
     * 指定该Filter的类型
     * PRE：在请求被路由之前调用，可以使用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试Log等。
     * ROUTE：将请求路由到对应的微服务，用于构建发送给微服务的请求。
     * POST：在请求被路由到对应的微服务以后执行，可用来为Response添加HTTP Header、将微服务的Response发送给客户端等。
     * ERROR：在其他阶段发生错误时执行该过滤器。
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();
        log.info("判断是否拦截：{}",requestURI);
        Map<String, GrayReleaseConfig> grayReleaseConfigs =
                grayReleaseInfoHolder.getGrayReleaseConfigs();
        for(String path : grayReleaseConfigs.keySet()) {
            if(requestURI.contains(path)) {
                GrayReleaseConfig grayReleaseConfig = grayReleaseConfigs.get(path);
                if(YesNoCodeEnum.YES.equals(grayReleaseConfig.getEnableGrayRelease())) {
                    /**
                      * 启用灰度发布
                      */
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 假设灰度发布概率为1/100
     */
    @Override
    public Object run() {
        /**
          * 由于是个高频方法所以使用ThreadLocalRandom，
          * 与在本类初始化一个普通Random的区别是
          * 用空间换效率线程间不用并发竞争随机数种子
          */
	    int randomInt = ThreadLocalRandom.current().nextInt(100);
	    log.info("randomInt={}",randomInt);
        if (randomInt == 50) {
            /**
             *
             * 往这个holder里面放入键值对的话,在ribbon进行路由时会判断eureka上面该服务
             * 的服务实例中的元数据是否与这里放入的匹配，如果匹配则路由到该实例
             *
             */
            log.info("路由到新版本");
            RibbonFilterContextHolder.getCurrentContext().add("version", "new");
        }  else {
            log.info("路由到老版本");
            RibbonFilterContextHolder.getCurrentContext().add("version", "current");
        }
        return null;
    }
}
