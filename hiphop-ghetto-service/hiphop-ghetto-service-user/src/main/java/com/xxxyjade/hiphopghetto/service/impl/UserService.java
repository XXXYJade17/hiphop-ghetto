package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCache;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.enums.AccountType;
import com.xxxyjade.hiphopghetto.enums.BaseCode;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameworkException;
import com.xxxyjade.hiphopghetto.util.JwtUtil;
import com.xxxyjade.hiphopghetto.util.PasswordUtil;
import com.xxxyjade.hiphopghetto.util.UserBloomFilter;
import com.xxxyjade.hiphopghetto.mapper.UserMapper;
import com.xxxyjade.hiphopghetto.pojo.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.UserUpdateDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.xxxyjade.hiphopghetto.service.IUserService;
import com.xxxyjade.hiphopghetto.pojo.vo.UserAuthVO;
import com.xxxyjade.hiphopghetto.pojo.vo.UserVO;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserBloomFilter userBloomFilter;
    private final UserMapper userMapper;

    /**
     * 用户注册
     * @param userRegisterDTO 用户注册信息
     * @return UserVO
     */
    public UserAuthVO register(UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO.getEmail().isBlank() && userRegisterDTO.getPhone().isBlank()) {
            // 邮箱、手机号均不存在
            throw new HipHopGhettoFrameworkException(BaseCode.ARGUMENT_ERROR);
        }

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            // 两次密码输入不一致
            throw new HipHopGhettoFrameworkException(BaseCode.PASSWORDS_DIFFERENT);
        }

        if (userBloomFilter.mightExist(userRegisterDTO.getUsername())) {
            Boolean exist = userMapper.existByUsername(userRegisterDTO.getUsername());
            if (Boolean.TRUE.equals(exist)) {
                // 用户已存在
                throw new HipHopGhettoFrameworkException(BaseCode.USER_EXIST);
            }
        }

        User user = User.builder()
                .username(userRegisterDTO.getUsername())
                .phone(userRegisterDTO.getPhone())
                .email(userRegisterDTO.getEmail())
                .password(PasswordUtil.encrypt(userRegisterDTO.getPassword()))
                .nickname(userRegisterDTO.getUsername())
                .build();

        // 插入数据
        userMapper.insert(user);

        userBloomFilter.put(
                "user::account=" + userRegisterDTO.getUsername(),
                "user::account=" + userRegisterDTO.getPhone(),
                "user::account=" + userRegisterDTO.getEmail()
        );

        // 构造返回VO
        return UserAuthVO.builder()
                .id(user.getId())
                .token(JwtUtil.createAuthJwt(user.getId()))    // 生成令牌
                .build();
    }

    /**
     * 用户登录
     * @param userLoginDTO 用户登录信息
     * @return UserVO
     */
    public UserAuthVO login(UserLoginDTO userLoginDTO) {
        // 判断账户类型
        String account = userLoginDTO.getAccount();
        AccountType accountType = AccountType.getAccountType(account);
        User user = new User();
        switch (accountType) {
            case EMAIL -> user.setEmail(account);
            case PHONE -> user.setPhone(account);
            case USERNAME -> user.setUsername(account);
        }

        if (Boolean.FALSE.equals(userBloomFilter.mightExist("user::account=" + account))) {
            // 用户不存在
            throw new HipHopGhettoFrameworkException(BaseCode.USER_NOT_FOUND);
        }

        user = userMapper.selectOne(new QueryWrapper<>(user));

        if (user == null) {
            // 用户不存在
            throw new HipHopGhettoFrameworkException(BaseCode.USER_NOT_FOUND);
        }

        if (Boolean.FALSE.equals(PasswordUtil.verify(userLoginDTO.getPassword(), user.getPassword()))) {
            // 密码验证错误
            throw new HipHopGhettoFrameworkException(BaseCode.VERIFY_ERROR);
        }

        return UserAuthVO.builder()
                .id(user.getId())
                .token(JwtUtil.createAuthJwt(user.getId()))
                .build();
    }

    /**
     * 根据 Id 获取用户信息
     * @param id 用户 Id
     * @return UserInfoVO
     */
    @ThreeLevelCache(
            key = "'user::id=' + #id"
    )
    public UserVO info(Long id) {
        if (!userBloomFilter.mightExist("user::id=" + id)) {
            // 用户不存在
            throw new HipHopGhettoFrameworkException(BaseCode.USER_NOT_FOUND);
        }

        User user = userMapper.selectById(id);

        if (user == null) {
            // 用户不存在
            throw new HipHopGhettoFrameworkException(BaseCode.USER_NOT_FOUND);
        }

        UserVO userVO = UserVO.builder()
                .nickname(user.getNickname())
                .sex(user.getSex())
                .avatar(user.getAvatar())
                .description(user.getDescription())
                .birthday(user.getBirthday())
                .build();

        return userVO;
    }

    /**
     * 更新用户信息
     * @param userUpdateDTO 用户信息
     * @return 是否更新成功
     */
    @ThreeLevelCacheEvict(
            key = "user::id=" + "#id",
            dependOnResult = true
    )
    public Boolean update(UserUpdateDTO userUpdateDTO) {
        User user = User.builder()
                .id(userUpdateDTO.getId())
                .username(userUpdateDTO.getUsername())
                .phone(userUpdateDTO.getPhone())
                .email(userUpdateDTO.getEmail())
                .password(PasswordUtil.encrypt(userUpdateDTO.getPassword()))
                .nickname(userUpdateDTO.getNickname())
                .sex(userUpdateDTO.getSex())
                .avatar(userUpdateDTO.getAvatar())
                .background(userUpdateDTO.getBackground())
                .description(userUpdateDTO.getDescription())
                .birthday(userUpdateDTO.getBirthday())
                .build();
        int update = userMapper.updateById(user);
        return update > 0;
    }

}