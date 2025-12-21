package com.xxxyjade.hiphopghetto.config;

import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;

@Configuration
@AllArgsConstructor
public class MongoIndexInitializer {

    private final MongoTemplate mongoTemplate;
    @PostConstruct
    public void createIndexes() {
        // ========== 1. 给Song集合的albumId创建索引（关联查询核心索引） ==========
        IndexOperations songIndexOps = mongoTemplate.indexOps(Song.class);
        // 替代弃用的 ensureIndex → 用 createIndex
        songIndexOps.createIndex(new Index()
                .on("albumId", org.springframework.data.domain.Sort.Direction.ASC)
                .named("idx_song_albumId")); // 可选：给索引命名，便于管理
        songIndexOps.createIndex(new Index()
                .on("artists.name", org.springframework.data.domain.Sort.Direction.ASC)
                .named("idx_song_artistName"));

        // ========== 2. 给Album集合的artists.name创建索引（优化按歌手查询） ==========
        IndexOperations albumIndexOps = mongoTemplate.indexOps(Album.class);
        albumIndexOps.createIndex(new Index()
                .on("artists.name", org.springframework.data.domain.Sort.Direction.ASC)
                .named("idx_album_artistName"));

        // 可选：给Song的duration创建索引（优化按时长筛选歌曲）
        songIndexOps.createIndex(new Index()
                .on("duration", org.springframework.data.domain.Sort.Direction.ASC)
                .named("idx_song_duration"));

    }
}
