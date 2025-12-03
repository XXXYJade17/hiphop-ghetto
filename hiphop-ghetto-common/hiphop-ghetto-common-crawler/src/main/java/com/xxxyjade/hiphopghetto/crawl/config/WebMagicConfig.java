package com.xxxyjade.hiphopghetto.crawl.config;

import us.codecraft.webmagic.Site;

public class WebMagicConfig {

    public static final String NETEASE_DOMAIN = "music.163.com";

    public static final String ALBUM_URL = "https://music.163.com/album?id=";
    public static final String SONG_URL = "https://music.163.com/song?id=";

    private static final int SLEEP_TIME = 100;
    private static final int RETRY_TIMES = 3;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";

    public static final Site NETEASE_SITE = Site.me()
            .setDomain(NETEASE_DOMAIN)        // 目标域名
            .setSleepTime(SLEEP_TIME)               // 爬取间隔（避免反爬）
            .setRetryTimes(RETRY_TIMES)                 // 重试次数
            .setUserAgent(USER_AGENT); // 模拟浏览器UA


}
