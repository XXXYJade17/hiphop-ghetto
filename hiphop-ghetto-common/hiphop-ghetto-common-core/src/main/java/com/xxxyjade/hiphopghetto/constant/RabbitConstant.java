package com.xxxyjade.hiphopghetto.constant;

public class RabbitConstant {

    // ==================== EXCHANGE ====================
    public static final String EXCHANGE = "hiphopghetto.exchange";

    // ==================== ROUTING KEY ====================
    public static final String STATS_ROUTING_PATTERN = "stats.#";
    public static final String STATS_DEAD_ROUTING_KEY = "stats.dead";

    // ==================== QUEUE ====================
    public static final String STATS_QUEUE = "stats_queue";
    public static final String CRAWl_QUEUE = "crawl_queue";
    public static final String NOTIFY_QUEUE = "notify_queue";


    // ==================== DEAD QUEUE ====================
    public static final String STATS_DEAD_QUEUE = "stats_dead_queue";
    public static final String CRAWl_DEAD_QUEUE = "crawl_dead_queue";
    public static final String NOTIFY_DEAD_QUEUE = "notify_dead_queue";
    public static final String DEAD_LETTER_QUEUE = "dead_letter_queue";

}
