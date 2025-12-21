package com.xxxyjade.hiphopghetto.message;

import com.xxxyjade.hiphopghetto.enums.Strategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Message {

    String id = UUID.randomUUID().toString().replaceAll("-", "");

    Strategy support;

}
