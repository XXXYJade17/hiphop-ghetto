package com.xxxyjade.hiphopghetto.service;


import com.xxxyjade.hiphopghetto.pojo.dto.CollectionDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.IsCollectedDTO;

public interface ICollectionService {

    /**
     * 查询收藏状态
     */
    Boolean select(IsCollectedDTO isCollectedDTO);

    /**
     * 创建收藏
     */
    void collect(CollectionDTO collectionDTO);

    /**
     * 取消收藏
     */
    void uncollect(CollectionDTO collectionDTO);

}
