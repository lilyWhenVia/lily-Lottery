package com.lily.lottery.domain.strategy.service.draw;

import com.lily.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.lily.lottery.domain.strategy.model.req.DrawReq;
import com.lily.lottery.domain.strategy.model.res.DrawResult;
import com.lily.lottery.domain.strategy.model.vo.AwardRateInfo;
import com.lily.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.lily.lottery.infrastructure.po.Award;
import com.lily.lottery.infrastructure.po.Strategy;
import com.lily.lottery.infrastructure.po.StrategyDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lily via on 2024/6/16 16:32
 * 模板方法抽奖抽象基类
 */
@Slf4j
public abstract class AbstractDrawBase extends DrawStrategySupport implements IDrawExec{

    /**
     * 定义通用的抽奖流程
     * @param req
     * @return
     */
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


    /**
     * @description:  1. 查询不允许抽奖的奖品ID列表，
     * 其中包括：奖品库存为空（已抽完）、风控策略、临时调整等
            * @param: strategyId
            * @return:
            * @author lily via
            * @date: 2024/6/16 16:39
     */
    protected abstract List<String> queryExcludeAwardIds(Long strategyId);

    /**
     * @description: 2. 校验概率元组是否已经初始化到内存
     * @param: strategyId
     * @param: strategyMode
     * @param: strategyDetailList
     * @return: void
     * @date: 2024/6/16 16:41
     */
    private void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyDetail> strategyDetailList) {
        // todo: constant
        /*
         * 如果不是必中奖模式（单项概率），不需要重新初始化抽奖概率数据
         * 1:单项概率、2:总体概率
         */
        if (1 != strategyMode) {
            return;
        }
        /*
         *根据抽奖模式获取对应实现抽奖算法
         */
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategyMode);
        /*
         * 判断是否已经初始化过抽奖概率元组
         */
        boolean existRateTuple = drawAlgorithm.isExistRateTuple(strategyId);
        /*
         * 如果已经初始化过抽奖概率元组，不需要重新初始化
         */
        if (existRateTuple) {
            return;
        }
        /*
         * 封装奖品信息用于初始化抽奖概率元组
         */
        List<AwardRateInfo> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
        for (StrategyDetail strategyDetail : strategyDetailList) {
            awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(), strategyDetail.getAwardRate()));
        }
        /*
         * 初始化抽奖概率元组
         */
        drawAlgorithm.initRateTuple(strategyId, awardRateInfoList);
    }


    /**
     * @description: 3. 根据模型选择并执行抽奖算法
     * @param: strategyId
     * @param: drawAlgorithm
     * @param: excludeAwardIds
     * @return: java.lang.String
     * @date: 2024/6/16 16:40
     */
    protected abstract String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<String> excludeAwardIds);


    /**
     * @description: 4. 封装抽奖结果
     * @param: uId
     * @param: strategyId
     * @param: awardId
     * @return: com.lily.lottery.domain.strategy.model.res.DrawResult
     * @date: 2024/6/16 16:40
     */
    private DrawResult buildDrawResult(String uId, Long strategyId, String awardId){
        // 封装结果
        Award award = super.queryAwardInfoByAwardId(awardId);
        return new DrawResult(uId, strategyId, awardId, award.getAwardName());

    }
}
