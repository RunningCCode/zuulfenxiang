package com.yunhua.czc.zuulfenxiang.domain.api.request;

import com.yunhuakeji.component.base.bean.dto.api.request.BaseRequest;
import com.yunhuakeji.component.base.enums.entity.YesNoCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Column;

/**
 * @author chenzhicong
 * @time 2019/11/23 18:46
 * @description
 */
@ApiModel("添加路由请求")
@Getter
@Setter
public class PostRoutesRequest extends BaseRequest {
    @Column(name = "服务名称" )

    @NotBlank
    private String apiName;

    @ApiModelProperty(value = "路径" )
    private String path;

    @ApiModelProperty(value = "服务id" )
    private String serviceId;

    @ApiModelProperty(value = "服务url（若有值则不拉去服务注册表中的地址）" )
    private String url;

    @ApiModelProperty(value = "是否支持重试（前提条件ribbon+不是直接映射物理路径）" )
    private YesNoCodeEnum retryable;

    @ApiModelProperty(value = "映射是否删除访问路径前缀" )
    private YesNoCodeEnum stripPrefix;

    @ApiModelProperty(value = "是否有效" )
    private YesNoCodeEnum enabled;


}
