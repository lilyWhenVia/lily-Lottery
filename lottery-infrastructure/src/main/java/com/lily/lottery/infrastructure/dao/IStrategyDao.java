package com.lily.lottery.infrastructure.dao;

import com.lily.lottery.infrastructure.po.Strategy;

/**
 * Created by lily via on 2024/6/14 20:24
 */
public interface IStrategyDao {


    Strategy queryStrategy(Long strategyId);
}
