package com.lily.lottery.domain.strategy.respository.impl;

import com.lily.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.lily.lottery.domain.strategy.respository.IStrategyRepository;
import com.lily.lottery.infrastructure.dao.IAwardDao;
import com.lily.lottery.infrastructure.dao.IStrategyDao;
import com.lily.lottery.infrastructure.dao.IStrategyDetailDao;
import com.lily.lottery.infrastructure.po.Award;
import com.lily.lottery.infrastructure.po.Strategy;
import com.lily.lottery.infrastructure.po.StrategyDetail;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lily via on 2024/6/14 20:58
 */
/**
 * 策略仓储查询实现类
 */
@Component
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IAwardDao awardDao;

    /**
     * 查询聚合策略信息
     * @param strategyId 策略ID
     * @return 策略信息
     */
    @Override
    public StrategyRich queryStrategyRich(Long strategyId) {
        Strategy strategy = strategyDao.queryStrategy(strategyId);
        List<StrategyDetail> strategyDetailList = strategyDetailDao.queryStrategyDetailList(strategyId);
        return new StrategyRich(strategyId, strategy, strategyDetailList);
    }

    /**
     * 查询奖品信息
     * @param awardId 奖品ID
     * @return 奖品信息
     */
    @Override
    public Award queryAwardInfo(String awardId) {
        return awardDao.queryAwardInfo(awardId);
    }
}
