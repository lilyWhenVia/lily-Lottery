package com.lily.lottery.domain.strategy.service.draw;

import com.lily.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.lily.lottery.domain.strategy.respository.IStrategyRepository;
import com.lily.lottery.infrastructure.po.Award;

import javax.annotation.Resource;

/**
 * Created by lily via on 2024/6/16 16:28
 * 抽奖策略数据支持层
 * 提供抽奖时与数据库交互的服务
 */
public class DrawStrategySupport extends DrawConfig{

    @Resource
    protected IStrategyRepository strategyRepository;

    /**
     * 查询策略配置信息
     * @param strategyId 策略ID
     * @return 策略配置信息
     */
    protected StrategyRich queryStrategyRich(Long strategyId) {
        return strategyRepository.queryStrategyRich(strategyId);
    }

    /**
     * 查询奖品详情信息
     * @param awardId 奖品ID
     * @return 中奖详情
     */
    protected Award queryAwardInfoByAwardId(String awardId) {
        return strategyRepository.queryAwardInfo(awardId);
    }
}
