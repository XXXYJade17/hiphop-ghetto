package com.xxxyjade.hiphopghetto.enums;

public enum ResourceType {
    ALBUM,
    SONG,
    COMMENT,
    TOPIC,
    USER;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
