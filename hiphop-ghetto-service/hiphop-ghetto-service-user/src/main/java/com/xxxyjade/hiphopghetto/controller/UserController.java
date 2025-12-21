package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.pojo.entity.Subscription;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.pojo.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.service.UserService;
import com.xxxyjade.hiphopghetto.service.UserSubscriptionService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.xxxyjade.hiphopghetto.pojo.vo.UserAuthVO;
import com.xxxyjade.hiphopghetto.pojo.vo.UserVO;

@RestController
@RequestMapping("/api/users")
@Tag(name = "UserController", description = "用户相关接口")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserSubscriptionService userSubscriptionService;

    @Operation(summary  = "注册")
    @PostMapping("/register")
    public Result<UserAuthVO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("用户注册: {}",userRegisterDTO);
        return Result.success(userService.register(userRegisterDTO));
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<UserAuthVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录: {}",userLoginDTO);
        return Result.success(userService.login(userLoginDTO));
    }

    @Operation(summary = "详情")
    @GetMapping("/{id}")
    public Result<UserVO> info(@PathVariable("id") Long id) {
        log.info("用户详情: {}",id);
        return Result.success(userService.info(id));
    }

//    public Result<Void> update(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
//        log.info("用户更新: {}",userUpdateDTO);
//        userService.update(userUpdateDTO);
//        return Result.success();
//    }

    @Operation(summary = "关注")
    @PostMapping("/{id}/subscribe")
    public Result<Void> subscribe(@PathVariable("id") Long id) {
        Subscription subscription = Subscription.builder()
                .userId(ThreadUtil.getUserId())
                .subscribedId(id)
                .isSubscribed(true)
                .build();
        log.info("用户关注: {}", subscription);
        userSubscriptionService.subscribe(subscription);
        return Result.success();
    }

    @Operation(summary = "取消关注")
    @DeleteMapping("/{id}/unfollow")
    public Result<Void> unsubscribe(@PathVariable("id") Long id) {
        Subscription subscription = Subscription.builder()
                .userId(ThreadUtil.getUserId())
                .subscribedId(id)
                .isSubscribed(false)
                .build();
        log.info("用户取消关注: {}", subscription);
        userSubscriptionService.unsubscribe(subscription);
        return Result.success();
    }

}