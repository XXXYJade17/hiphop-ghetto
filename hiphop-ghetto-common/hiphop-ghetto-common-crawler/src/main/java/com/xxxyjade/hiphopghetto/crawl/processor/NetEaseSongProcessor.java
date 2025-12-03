package com.xxxyjade.hiphopghetto.crawl.processor;

import com.xxxyjade.hiphopghetto.crawl.config.WebMagicConfig;
import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.time.LocalDate;

@Component
public class NetEaseSongProcessor implements PageProcessor {

    @Override
    public Site getSite() {
        return WebMagicConfig.NETEASE_SITE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(Page page) {
        // 判断 URL
        if (page.getUrl().regex("https?://music\\.163\\.com/song\\?id=\\d+").match()) {
            Selectable html = page.getHtml();
            // 歌曲 id
            Long id = Long.parseLong(page.getUrl().regex("id=(\\d+)").get());
            // 歌曲名
            String songName = html.xpath("//meta[@property='og:title']/@content").get();
            // 专辑名
            String albumName = html.xpath("//p[contains(@class, 'des') and contains(@class, 's-fc4')]/a[starts-with(@href, '/album?id=')]/text()").get();
            // 歌手
            String artists = String.join(" / ", html.xpath("//p[@class='des s-fc4']/span/a/text()").all());
            // 时长（秒）
            Integer duration = Integer.parseInt(html.xpath("//meta[@property='music:duration']/@content").get());
            // 封面 URL
            String coverUrl = html.xpath("//meta[@property='og:image']/@content").get();

            Long albumId = page.getRequest().getExtra("albumId");
            LocalDate releaseTime = page.getRequest().getExtra("releaseTime");

            Song song = Song.builder()
                    .id(id)
                    .songName(songName)
                    .albumId(albumId)
                    .albumName(albumName)
                    .artists(artists)
                    .releaseTime(releaseTime)
                    .duration(duration)
                    .coverUrl(coverUrl)
                    .build();

            page.putField("song", song);
        }
    }

}
