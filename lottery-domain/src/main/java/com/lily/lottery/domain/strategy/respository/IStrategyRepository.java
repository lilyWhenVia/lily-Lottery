package com.lily.lottery.domain.strategy.respository;

/**
 * Created by lily via on 2024/6/14 20:57
 */

import com.lily.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.lily.lottery.infrastructure.po.Award;

/**
 * 策略仓储查询
 */
public interface IStrategyRepository {

    StrategyRich queryStrategyRich(Long strategyId);

    Award queryAwardInfo(String awardId);
}
