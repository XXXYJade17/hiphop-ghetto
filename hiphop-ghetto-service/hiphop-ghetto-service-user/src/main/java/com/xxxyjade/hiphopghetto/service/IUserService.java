package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.pojo.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.UserUpdateDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.UserAuthVO;
import com.xxxyjade.hiphopghetto.pojo.vo.UserVO;

public interface IUserService {
    /**
     * 用户注册
     */
    UserAuthVO register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     */
    UserAuthVO login(UserLoginDTO userLoginDTO);

    /**
     * 查询用户信息
     */
    UserVO info(Long id);

    /**
     * 更新用户信息
     * @param userUpdateDTO 用户信息
     * @return 是否更新成功
     */
    Boolean update(UserUpdateDTO userUpdateDTO);

}