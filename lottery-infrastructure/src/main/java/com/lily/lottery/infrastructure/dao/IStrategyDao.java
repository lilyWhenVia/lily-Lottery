package com.lily.lottery.infrastructure.dao;

import com.lily.lottery.infrastructure.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by lily via on 2024/6/14 20:24
 */
@Mapper
public interface IStrategyDao {


    /**
     * 查询策略配置
     *
     * @param strategyId 策略ID
     * @return           策略配置信息
     */
    Strategy queryStrategy(Long strategyId);

    /**
     * 插入策略配置
     *
     * @param req 策略配置
     */
    void insert(Strategy req);
}
