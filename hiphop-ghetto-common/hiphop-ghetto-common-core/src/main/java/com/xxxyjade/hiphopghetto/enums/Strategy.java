package com.xxxyjade.hiphopghetto.enums;

public enum Strategy {
//==================== Stats ====================
    MUSIC_STATS,
    COMMENT_STATS,
    LIKE_STATS,

//==================== Message ====================
    STATS_UPDATE;

    @Override
    public String toString() {
        String[] parts = this.name().split("_");
        StringBuilder result = new StringBuilder(parts[0].toLowerCase());
        for (int i = 1; i < parts.length; i++) {
            result.append(Character.toUpperCase(parts[i].charAt(0)))
                    .append(parts[i].substring(1).toLowerCase());
        }
        return result.toString();
    }
}
