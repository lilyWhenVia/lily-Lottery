package com.lily.lottery.domain.strategy.service.algorithm.impl;

import com.lily.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lily via on 2024/6/15 14:57
 * 默认抽奖算法实现
 * 策略模式与工厂模式结合
 */
@Component("defaultRandomDrawAlgorithm")
public class DefaultRandomDrawAlgorithm extends BaseAlgorithm {
    //
    /**
     * 随机抽奖
     * 父类中未实现的抽象方法
     * @param strategyId 策略ID
     * @param excludeAwardIds 排除的奖品ID
     * @return 奖品ID
     */
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        return "";
    }
}
