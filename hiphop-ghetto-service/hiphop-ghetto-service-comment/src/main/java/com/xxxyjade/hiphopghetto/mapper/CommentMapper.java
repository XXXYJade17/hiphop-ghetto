package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.pojo.entity.Comment;
import org.apache.ibatis.annotations.Update;

public interface CommentMapper extends BaseMapper<Comment> {

    @Update("update user_comment " +
            "set like_count = like_count + 1 " +
            "where id = #{id}")
    void increaseLikeCount(Long id);

    @Update("update user_comment " +
            "set like_count = like_count - 1 " +
            "where id = #{id}")
    void decreaseLikeCount(Long id);


    @Update("update user_comment " +
            "set reply_count = reply_count + 1 " +
            "where id = #{id}")
    void increaseReplyCount(Long id);

    @Update("update user_comment " +
            "set reply_count = reply_count - 1 " +
            "where id = #{id}")
    void decreaseReplyCount(Long id);

}
