package com.lzc.shiro.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lzc
 * @date 2020/12/5 22:27
 */
@ApiModel("用户")
@Data
public class UserDTO {

    @ApiModelProperty(value = "用户名", example = "zhangsan")
    private String username;

    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

    @ApiModelProperty(value = "记住我", example = "false")
    private Boolean rememberMe;
}
