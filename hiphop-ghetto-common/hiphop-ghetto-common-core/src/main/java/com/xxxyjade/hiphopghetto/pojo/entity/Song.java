package com.xxxyjade.hiphopghetto.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 歌曲实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "song")
public class Song implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String name;

    private String albumId;

    private String albumName;

    private List<Artist> artists;

    private Long publishTime;

    private Integer duration;

    private String coverUrl;

    @Builder.Default
    private MusicStats stats = new MusicStats();

}
