package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.pojo.dto.UserFollowDTO;

public interface IUserFollowService {

    void follow(UserFollowDTO userFollowDTO);

    void unfollow(UserFollowDTO userFollowDTO);

}
