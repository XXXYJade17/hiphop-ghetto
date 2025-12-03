package com.xxxyjade.hiphopghetto.crawl.processor;

import com.xxxyjade.hiphopghetto.crawl.config.WebMagicConfig;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class NetEaseAlbumProcessor implements PageProcessor {

    @Override
    public Site getSite() {
        return WebMagicConfig.NETEASE_SITE;
    }

    @Override
    public void process(Page page) {
        // 判断 URL
        if (page.getUrl().regex("https?://music\\.163\\.com/album\\?id=\\d+").match()) {
            // 网易云id
            Long id = Long.parseLong(page.getUrl().regex("id=(\\d+)").get());
            // 专辑名
            String albumName = page.getHtml().xpath("//h2[@class='f-ff2']/text()").get();
            // 歌手名
            String artists = String.join(" / ", page.getHtml().xpath("//p[@class='intr'][1]/span/a/text()").all());
            // 发行时间
            LocalDate releaseTime = LocalDate.parse(page.getHtml().xpath("//p[@class='intr'][2]/text()").get(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // 专辑封面
            String url = page.getHtml().xpath("//div[@class=\"cover u-cover u-cover-alb\"]/img/@data-src").get();
            // 专辑介绍
            String description = page.getHtml().xpath("//div[@id='album-desc-dot']/p/text()").get();
            // 歌曲id列表
            List<Long> songIds = page.getHtml().xpath("//ul[@class='f-hide']/li").all().stream().map(s -> {
                Html html = Html.create(s);
                return Long.parseLong(html.xpath("//a/@href").get().replace("/song?id=", ""));
            }).toList();

            Album album = Album.builder()
                    .id(id)
                    .albumName(albumName)
                    .artists(artists)
                    .releaseTime(releaseTime)
                    .coverUrl(url)
                    .description(description)
                    .build();

            page.putField("album", album);
            page.putField("songIds", songIds);
        }
    }

}
