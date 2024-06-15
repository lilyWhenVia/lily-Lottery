package com.lily.lottery.domain.strategy.service.draw;

import com.lily.lottery.domain.strategy.model.vo.AwardRateInfo;
import com.lily.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.lily.lottery.infrastructure.po.StrategyDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lily via on 2024/6/15 15:00
 * 抽奖方法基类
 * 子类可以获得基类的抽奖相关方法和配置
 */
public class DrawBase extends DrawConfig{

    /**
     * 检查并初始化抽奖概率数据
     * @param strategyId 策略id: 用于初始化抽奖概率元组
     * @param strategyMode 策略模式： 用于判断是否需要初始化元组
     * @param strategyDetailList 策略详情列表 ： 用于获取奖品信息，进而初始化抽奖概率元组
     */
    public void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyDetail> strategyDetailList) {
        // todo: constant
        /*
         * 如果不是必中奖模式（单项概率），不需要重新初始化抽奖概率数据
         * 1:单项概率、2:总体概率
         */
        if (1 != strategyMode) return;
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
        if (existRateTuple) return;
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
}
