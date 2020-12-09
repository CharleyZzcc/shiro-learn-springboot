package com.lzc.shiro.config;

import com.lzc.shiro.dto.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * shiro异常封装处理
 * @author lzc
 * @date 2020/12/6 22:12
 * See More: https://github.com/charleyzzcc, https://gitee.com/charleyzz
 */
@RestControllerAdvice
public class ShiroAdvice {

    /**
     * shiro权限异常直接包装成404
     * @return com.lzc.shiro.dto.Result
     * @author lzc
     * @date 2020/12/6 22:41
     */
    @ExceptionHandler(AuthorizationException.class)
    public Result authorizationExceptionHandler(AuthorizationException e) {
        // 这里可以改用日志来记录错误信息
        System.out.println(e.getMessage());
        return Result.notFound();
    }
}
