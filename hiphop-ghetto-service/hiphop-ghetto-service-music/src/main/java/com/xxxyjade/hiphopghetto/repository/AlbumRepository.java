package com.xxxyjade.hiphopghetto.repository;

import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.vo.AlbumDetailVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface AlbumRepository extends MongoRepository<Album, String> {

    @Query(value = "{}", fields = "{_id: 1, name: 1, artists: 1, publishTime: 1, coverUrl: 1, 'stats.avgScore': 1}")
    Page<Album> page(Pageable pageable);

    @Aggregation(pipeline = {
            // 步骤1：筛选指定ID的专辑
            "{ '$match': { '_id': ?0 } }",
            // 步骤2：关联song集合（左外连接）
            "{ '$lookup': { " +
                    "'from': 'song', " +          // 关联的song集合
                    "'localField': '_id', " +     // album的主键（对应song的albumId）
                    "'foreignField': 'albumId', " +// song的albumId字段
                    "'as': 'songs' " +            // 关联结果别名（映射到DTO的songs字段）
                    "}" +
            "}"
    })
    Optional<AlbumDetailVO> detail(String albumId);

}
