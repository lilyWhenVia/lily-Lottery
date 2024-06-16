package com.lily.lottery.domain.strategy.respository;

/**
 * Created by lily via on 2024/6/14 20:57
 */

import com.lily.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.lily.lottery.infrastructure.po.Award;

import java.util.List;

/**
 * 策略仓储查询
 */
public interface IStrategyRepository {

    /**
     * 查询策略信息
     * @param strategyId 策略ID
     * @return           策略信息
     */
    StrategyRich queryStrategyRich(Long strategyId);

    /**
     * 查询奖品信息
     * @param awardId 奖品ID
     * @return        奖品信息
     */
    Award queryAwardInfo(String awardId);

    /**
     * 查询不允许抽奖的奖品ID列表
     * @param strategyId 策略ID
     * @return           不允许抽奖的奖品ID列表
     */
    List<String> queryNoStockStrategyAwardList(Long strategyId);

    /**
     * 扣减库存
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return           扣减结果
     */
    boolean deductStock(Long strategyId, String awardId);

}
