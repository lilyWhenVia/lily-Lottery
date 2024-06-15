package com.lily.lottery.domain.strategy.service.algorithm.impl;

import com.lily.lottery.domain.strategy.service.algorithm.BaseAlgorithm;

import java.util.List;

/**
 * Created by lily via on 2024/6/15 14:59
 * 单次抽奖随机抽奖算法实现
 * 策略模式与工厂模式结合
 */
public class SingleRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        return "";
    }
}
