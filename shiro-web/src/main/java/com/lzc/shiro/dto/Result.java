package com.lzc.shiro.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;

/**
 * 响应结果
 * @author lzc
 * @date 2020/12/6 22:00
 * See More: https://github.com/charleyzzcc, https://gitee.com/charleyzz
 */
@ApiModel("响应结果")
@Getter
@AllArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;

    private Result() {
    }

    public static Result success() {
        return new Result(String.valueOf(HttpStatus.OK.value()), "成功", Collections.emptyMap());
    }

    public static Result success(String msg) {
        return new Result(String.valueOf(HttpStatus.OK.value()), msg, Collections.emptyMap());
    }

    public static Result success(Object data) {
        return new Result(String.valueOf(HttpStatus.OK.value()), "成功", data);
    }

    public static Result failure(String msg) {
        return new Result(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), msg, Collections.emptyMap());
    }

    public static Result notLogin() {
        return new Result(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "未登录", Collections.emptyMap());
    }

    public static Result notFound() {
        return new Result(String.valueOf(HttpStatus.NOT_FOUND.value()), "NOT FOUND", Collections.emptyMap());
    }

}
