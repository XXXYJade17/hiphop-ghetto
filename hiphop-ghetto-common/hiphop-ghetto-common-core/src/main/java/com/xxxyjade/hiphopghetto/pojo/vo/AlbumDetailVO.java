package com.xxxyjade.hiphopghetto.pojo.vo;

import com.xxxyjade.hiphopghetto.pojo.entity.Artist;
import com.xxxyjade.hiphopghetto.pojo.entity.MusicStats;
import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDetailVO implements Serializable {

    private String id;
    private String name;
    private List<Artist> artists;
    private LocalDate publishTime;
    private String coverUrl;
    private String description;
    private MusicStats stats;
    private List<Song> songs;

}
