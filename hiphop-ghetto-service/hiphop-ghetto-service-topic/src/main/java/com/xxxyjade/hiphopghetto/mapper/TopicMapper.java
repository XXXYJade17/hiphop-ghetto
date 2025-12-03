package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.pojo.entity.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TopicMapper extends BaseMapper<Topic> {

    @Update("update topic " +
            "set like_count = like_count + 1 " +
            "where id = #{id}")
    void increaseLikeCount(Long id);

    @Update("update topic " +
            "set like_count = like_count - 1 " +
            "where id = #{id}")
    void decreaseLikeCount(Long id);

    @Update("update topic " +
            "set comment_count = comment_count + 1 " +
            "where id = #{id}")
    void increaseCommentCount(Long id);

    @Update("update topic " +
            "set comment_count = comment_count - 1 " +
            "where id = #{id}")
    void decreaseCommentCount(Long id);

    @Update("update topic " +
            "set view_count = view_count + 1 " +
            "where id = #{id}")
    void increaseViewCount(Long id);

}
