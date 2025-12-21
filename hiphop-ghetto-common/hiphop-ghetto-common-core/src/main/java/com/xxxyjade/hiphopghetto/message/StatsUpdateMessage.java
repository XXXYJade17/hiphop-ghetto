package com.xxxyjade.hiphopghetto.message;

import com.xxxyjade.hiphopghetto.enums.StatsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StatsUpdateMessage extends Message {

    private Object data;
    private StatsType statsType;
    private Integer value;

}
