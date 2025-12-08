package com.xxxyjade.hiphopghetto.repository;

import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface SongRepository extends MongoRepository<Song, String> {

    @Query(value = "{}", fields = "{_id: 1, name: 1, albumId: 1,albumName: 1, artists: 1, publishTime: 1, coverUrl: 1, 'stats.avgScore': 1}")
    Page<Song> page(Pageable pageable);


    @Query("{'_id': ?0}")
    @Update("{$inc: {'stats.collectionCount': 1}}")
    void increaseCollectionCount(String id);

    @Query("{'_id': ?0}")
    @Update("{$inc: {'stats.collectionCount': -1}}")
    void decreaseCollectionCount(String id);

    @Query("{'_id': ?0}")
    @Update("{$inc: {'stats.ratingCount': 1}}")
    void increaseRatingCount(String id);

    @Query("{'_id': ?0}")
    @Update("{$inc: {'stats.commentCount': 1}}")
    void increaseCommentCount(String id);

    @Query("{'_id': ?0}")
    @Update("{$inc: {'stats.commentCount': -1}}")
    void decreaseCommentCount(String id);

}
