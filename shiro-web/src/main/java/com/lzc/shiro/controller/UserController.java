package com.lzc.shiro.controller;

import com.lzc.shiro.dto.Result;
import com.lzc.shiro.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制层
 * @author lzc
 * @date 2020/12/5 22:20
 */
@Api(tags = "用户控制层")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("未登录统一消息")
    @GetMapping("/msg")
    public Result msg() {
        return Result.notLogin();
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO dto) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(dto.getUsername(), dto.getPassword());
        // 开启记住我功能，需要把过滤器的"/**"限定为user而不是authc，但这样会降低系统安全性，为了安全性考虑的话，可以不用该功能
        // token.setRememberMe(dto.getRememberMe());
        subject.login(token);

        if (subject.hasRole("admin")) {
            return Result.success("登录成功：有admin角色权限");
        }
        return Result.success("登录成功");
    }

    @ApiOperation("注销")
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success("注销成功");
    }

    @ApiOperation("测试登录后的请求")
    @GetMapping("/index")
    public Result index() {
        return Result.success("测试成功");
    }

    @ApiOperation("测试admin角色")
    @RequiresRoles("admin")
    @GetMapping("/testRole")
    public Result testRole() {
        return Result.success("有admin角色权限");
    }

    @ApiOperation("测试用户权限")
    @RequiresPermissions(value = {"user:select", "user:delete"}, logical = Logical.OR)
    @GetMapping("/testPermission")
    public Result testPermission() {
        return Result.success("有用户更新或删除权限");
    }

}
