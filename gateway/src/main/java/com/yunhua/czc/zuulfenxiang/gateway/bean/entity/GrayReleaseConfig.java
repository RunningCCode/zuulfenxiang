package com.yunhua.czc.zuulfenxiang.gateway.bean.entity;

import com.yunhuakeji.component.base.bean.entity.base.BaseEntity;
import com.yunhuakeji.component.base.enums.entity.YesNoCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Column;

/**
 * @Description  
 * @Author  chenzhicong
 * @Date 2019-12-17 
 */

@Setter
@Getter
@ToString
@Table ( name ="gray_release_config" )
public class GrayReleaseConfig  extends BaseEntity {

	private static final long serialVersionUID =  2190425537644400490L;

   	@Column(name = "gray_release_config_id" )
	private String grayReleaseConfigId;
	/**
	  * 服务id
	  */
   	@Column(name = "service_id" )
	private String serviceId;
	/**
	  * 路由路径
	  */
   	@Column(name = "path" )
	private String path;

   	@Column(name = "enable_gray_release" )
	private YesNoCodeEnum enableGrayRelease;

}
