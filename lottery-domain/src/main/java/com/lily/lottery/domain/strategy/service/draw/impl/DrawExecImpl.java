package com.lily.lottery.domain.strategy.service.draw.impl;

import com.lily.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.lily.lottery.domain.strategy.model.req.DrawReq;
import com.lily.lottery.domain.strategy.model.res.DrawResult;
import com.lily.lottery.domain.strategy.respository.IStrategyRepository;
import com.lily.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.lily.lottery.domain.strategy.service.draw.DrawBase;
import com.lily.lottery.domain.strategy.service.draw.IDrawExec;
import com.lily.lottery.infrastructure.po.Award;
import com.lily.lottery.infrastructure.po.Strategy;
import com.lily.lottery.infrastructure.po.StrategyDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lily via on 2024/6/15 15:01
 * 最外层抽奖服务提供类
 */
@Service("drawExec")
@Slf4j
public class DrawExecImpl extends DrawBase implements IDrawExec {

    // 只依赖于一个聚合对象的仓储dao方法
    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public DrawResult doDrawExec(DrawReq req) {
        log.info("执行策略抽奖开始，strategyId：{}",  req.getStrategyId());
        // 获取抽奖策略配置数据, strategy, strategyDetailList
        /**
         查询数据库：获取策略和策略明细对象。、
         这里其实是封装过的方法，queryStrategyRich里本质上还是进行了两个数据库的查询
         */
        StrategyRich strategyRich = strategyRepository.queryStrategyRich(req.getStrategyId());
        // 获取策略对象
        Strategy strategy = strategyRich.getStrategy();
        // 获取策略明细对象
        List<StrategyDetail> strategyDetailList = strategyRich.getStrategyDetailList();
        // 校验数据，且查看是否需要初始化概率元组
        checkAndInitRateData(req.getStrategyId(), strategy.getStrategyMode(), strategyDetailList);
        /**
         * 开始抽奖
         */
        // 根据策略方式抽奖, 根据mode从config中初始并封装好的的map中获取对应的算法实例
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategy.getStrategyMode());
        // 根据以及获取的具体的抽奖方法进行抽奖
        // 此时排除奖品列表为空 todo：逻辑补全
        String awardId = drawAlgorithm.randomDraw(req.getStrategyId(), new ArrayList<>());
        // 根据奖品Id从数据库获取奖品信息
        Award award = strategyRepository.queryAwardInfo(awardId);
        log.info("执行策略抽奖完成，中奖用户：{} 奖品ID：{} 奖品名称：{}", req.getUId(), awardId, award.getAwardName());
        // 封装结果
        return new DrawResult(req.getUId(), req.getStrategyId(), awardId, award.getAwardName());
    }
}
