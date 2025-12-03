package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

public interface AlbumMapper extends BaseMapper<Album> {

    @Insert("insert ignore into album(id, album_name, artists, release_time, cover_url, description) " +
            "values (#{id}, #{albumName}, #{artists}, #{releaseTime}, #{coverUrl}, #{description})")
    void insertIgnore(Album album);

    @Update("update album " +
            "set collection_count = collection_count + 1 " +
            "where id = #{id}")
    void increaseCollectionCount(Long id);

    @Update("update album " +
            "set collection_count = collection_count - 1 " +
            "where id = #{id}")
    void decreaseCollectionCount(Long id);

    @Update("update album " +
            "set rating_count = rating_count + 1 " +
            "where id = #{id}")
    void increaseRatingCount(Long id);

    @Update("update album " +
            "set comment_count = comment_count + 1 " +
            "where id = #{id}")
    void increaseCommentCount(Long id);

    @Update("update album " +
            "set comment_count = comment_count - 1 " +
            "where id = #{id}")
    void decreaseCommentCount(Long id);

}
