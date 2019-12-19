package com.yunhua.czc.zuulfenxiang.gateway.bean.entity;
import com.yunhuakeji.component.base.bean.entity.base.BaseEntity;
import com.yunhuakeji.component.base.enums.entity.YesNoCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description  
 * @Author  chenzhicong
 * @Date 2019-11-23 
 */

@Setter
@Getter
@ToString
@Table ( name ="gateway_api_route_copy1" )
public class GatewayApiRoute extends BaseEntity {

	private static final long serialVersionUID =  2422793275649749395L;
	@Id
   	@Column(name = "gateway_api_route_id" )
	private String gatewayApiRouteId;

	/**
	  * 路径的样式如xxx/**
	  */
   	@Column(name = "path" )
	private String path;

   	/**
   	  * 服务id
   	  */
   	@Column(name = "service_id" )
	private String serviceId;

	/**
	 * 服务名称
	 */
	@Column(name = "api_name" )
	private String apiName;

   	/**
   	  * 要映射到的路径的具体Url,没注册服务的时候用，一般为空就行了
   	  */
   	@Column(name = "url" )
	private String url;

   	/**
   	  *  是否支持重试（前提条件ribbon+不是直接映射物理路径）
   	  */
   	@Column(name = "retryable" )
	private YesNoCodeEnum retryable;

   	/**
   	  * 是否有效
   	  */
   	@Column(name = "enabled" )
	private YesNoCodeEnum enabled;

	/**
	 * 映射的时候是否删除访问路径的前缀，比如映射的xxx/**,如果为false的话
	 * 转发的路径将加上xxx
	 */
   	@Column(name = "strip_prefix" )
	private YesNoCodeEnum stripPrefix;


}
