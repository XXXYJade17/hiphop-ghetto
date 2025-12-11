package com.xxxyjade.hiphopghetto.message;

import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StatsUpdateMessage extends Message {

    private StatsUpdateDTO statsUpdateDTO;

}
