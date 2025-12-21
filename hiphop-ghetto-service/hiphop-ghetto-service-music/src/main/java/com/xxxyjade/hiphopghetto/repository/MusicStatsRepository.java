package com.xxxyjade.hiphopghetto.repository;

import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MusicStatsRepository {

    private final MongoTemplate mongoTemplate;

    public int updateStats(StatsUpdateDTO statsUpdateDTO) {
        Query query = Query.query(Criteria.where("_id").is(statsUpdateDTO.getId()));
        Update update = new Update().inc("stats." + statsUpdateDTO.getStatsType(), statsUpdateDTO.getValue());
        return (int) mongoTemplate.updateFirst(query, update, statsUpdateDTO.getResourceType().toString()).getModifiedCount();
    }

}
