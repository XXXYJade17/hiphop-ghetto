package com.xxxyjade.hiphopghetto.config;

import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig {

    // 定义LocalDate转String的转换器（存储时）
    private static class LocalDateToStringConverter implements Converter<LocalDate, String> {
        @Override
        public String convert(LocalDate source) {
            return source.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    // 定义String转LocalDate的转换器（读取时）
    private static class StringToLocalDateConverter implements Converter<String, LocalDate> {
        @Override
        public LocalDate convert(String source) {
            return LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    // 注册转换器
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new LocalDateToStringConverter());
        converters.add(new StringToLocalDateConverter());
        return new MongoCustomConversions(converters);
    }

    @PostConstruct
    public void createIndexes(MongoTemplate mongoTemplate) {
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