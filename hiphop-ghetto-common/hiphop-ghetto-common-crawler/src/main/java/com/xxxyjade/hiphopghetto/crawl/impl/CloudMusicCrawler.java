package com.xxxyjade.hiphopghetto.crawl.impl;

import com.alibaba.fastjson2.JSONObject;
import com.xxxyjade.hiphopghetto.crawl.ICloudMusicCrawler;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.entity.Artist;
import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class CloudMusicCrawler implements ICloudMusicCrawler {

    // 基础Header配置
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
    private static final String REFERER = "https://music.163.com/";
    private static final String COOKIE = "appver=1.5.0.75771; _iuqxldmzr_=32; _ntes_nnid=1234567890abcdef,1234567890123; _ntes_nuid=1234567890abcdef";

    // 最大重试次数
    private static final int MAX_RETRIES = 5;
    private static final int RETRY_DELAY = 10000;

    @Override
    public List<Album> crawlByArtistId(String artistId) {
        String url = "https://music.163.com/artist/album?id=" + artistId + "&limit=100";
        Document document = requestHtmlWithRetry(url);
        List<String> albumIds = parseAlbumIds(document);

        return albumIds
                .stream()
                .map(this::crawlByAlbumId)
                .toList();
    }

    @Override
    public Album crawlByAlbumId(String albumId) {
        String url = "https://music.163.com/api/album/" + albumId + "?ext=true&id=" + albumId + "&offset=0&total=true";
        JSONObject response = requestJsonWithRetry(url);
        JSONObject albumObject = response.getJSONObject("album");
        return parseAlbum(albumObject);
    }

    // ---------------- 辅助方法 ----------------
    private Album parseAlbum(JSONObject albumObject) {
        // 构造专辑实体
        Album album = Album.builder()
                // 专辑ID
                .id(albumObject.get("id").toString())
                // 专辑名
                .name(albumObject.get("name").toString())
                // 专辑作者
                .artists(parseArtists(albumObject))
                // 专辑发行时间
                .publishTime(Long.valueOf(albumObject.get("publishTime").toString()))
                // 专辑封面url
                .coverUrl(albumObject.get("picUrl").toString())
                // 专辑简介
                .description(albumObject.get("description").toString())
                .build();
        // 提取专辑歌曲
        List<Song> songs = albumObject.getJSONArray("songs")
                .stream()
                .map(o -> parseSong((JSONObject) o, album))
                .toList();
        album.setSongs(songs);

        return album;
    }

    private Song parseSong(JSONObject songObject, Album parentAlbum) {
        return Song.builder()
                .id(songObject.get("id").toString())
                .name(songObject.get("name").toString())
                .albumId(parentAlbum.getId())
                .albumName(parentAlbum.getName())
                .artists(parseArtists(songObject))
                .publishTime(parentAlbum.getPublishTime())
                .duration(Integer.valueOf(songObject.get("duration").toString()))
                .coverUrl(parentAlbum.getCoverUrl())
                .build();
    }

    private List<Artist> parseArtists(JSONObject songObject) {
        return songObject.getJSONArray("artists")
                .stream()
                .map(o -> {
                    JSONObject artistObject = (JSONObject) o;
                    String artistId = artistObject.get("id").toString();
                    String artistName = artistObject.get("name").toString();
                    return new Artist(artistId, artistName);
                })
                .toList();
    }

    private List<String> parseAlbumIds(Document document) {
        return document.select("ul#m-song-module > li")
                .stream()
                .map(e ->
                        e.selectFirst("a.msk")
                                .attr("href")
                                .replace("/album?id=", ""))
                .toList();
    }

    /**
     * 请求HTML并处理重试
     */
    @SneakyThrows
    private Document requestHtmlWithRetry(String url) {
        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            try {
                return Jsoup.connect(url)
                        .userAgent(USER_AGENT)
                        .referrer(REFERER)
                        .cookie("Cookie", COOKIE)
                        .timeout(10000)
                        .get();
            } catch (IOException e) {
                attempt++;
                log.warn("请求HTML失败 (第{}次重试): {}", attempt, url);
                Thread.sleep(RETRY_DELAY);
            }
        }
        throw new IOException("达到最大重试次数，请求失败: " + url);
    }

    /**
     * 请求API JSON并处理重试
     */
    @SneakyThrows
    private JSONObject requestJsonWithRetry(String url) {
        log.info("请求API: {}", url);
        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            try {
                String response = Jsoup.connect(url)
                        .userAgent(USER_AGENT)
                        .referrer(REFERER)
                        .cookie("Cookie", COOKIE) // 有时API需要简单的Cookie
                        .ignoreContentType(true) // 必须忽略内容类型，否则Jsoup会拒绝JSON
                        .timeout(5000)
                        .method(Connection.Method.GET)
                        .execute()
                        .body();
                if (!response.isEmpty()) {
                    JSONObject jsonResponse = JSONObject.parseObject(response);

                    // 检查是否有错误码
                    if (jsonResponse.containsKey("code")) {
                        int code = jsonResponse.getIntValue("code");
                        if (code == -462) {
                            throw new IOException("触发反爬虫机制，需要验证: " + jsonResponse.toJSONString());
                        }
                    }

                    return jsonResponse;
                }
            } catch (IOException e) {
                attempt++;
                log.warn("请求JSON API失败 (第{}次重试): {}", attempt, url);
                Thread.sleep(RETRY_DELAY);
            }
        }
        throw new IOException("达到最大重试次数，请求失败: " + url);
    }

}
