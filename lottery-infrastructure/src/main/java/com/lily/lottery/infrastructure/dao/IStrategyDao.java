package com.lily.lottery.infrastructure.dao;

import com.lily.lottery.infrastructure.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by lily via on 2024/6/14 20:24
 */
@Mapper
public interface IStrategyDao {


    Strategy queryStrategy(Long strategyId);
}
