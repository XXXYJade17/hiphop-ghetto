package com.xxxyjade.hiphopghetto.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceType {
    ALBUM(1),
    SONG(2),
    COMMENT(3),
    TOPIC(4);

    @EnumValue
    private final Integer resourceType;

}
