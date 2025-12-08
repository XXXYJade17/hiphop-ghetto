package com.xxxyjade.hiphopghetto.crawl;

import com.xxxyjade.hiphopghetto.pojo.entity.Album;

import java.util.List;

public interface ICloudMusicCrawler {

    /**
     * 根据歌手爬取该歌手的专辑详情列表
     * @param artistId 歌手ID
     * @return 专辑列表
     */
    List<Album> crawlByArtistId(String artistId);

    /**
     * 根据专辑爬取专辑详情
     * @param albumId 专辑ID
     * @return 专辑详情
     */
    Album crawlByAlbumId(String albumId);
}
