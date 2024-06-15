package com.lily.lottery.domain.strategy.service.algorithm.impl;

import com.lily.lottery.domain.strategy.model.vo.AwardRateInfo;
import com.lily.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lily via on 2024/6/15 14:57
 * 默认抽奖算法实现
 * 策略模式与工厂模式结合
 */
@Component("singleRateRandomDrawAlgorithm")
public class DefaultRandomDrawAlgorithm extends BaseAlgorithm {
    //
    /**
     * 随机抽奖
     * 父类中未实现的抽象方法
     * @param strategyId 策略ID
     * @param excludeAwardIds 排除的奖品ID列表
     *                        不用于构建概率元组的奖品信息
     * @return 奖品ID
     */
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {

        /**
         * 排除掉不在抽奖范围的奖品ID集合
         * 并将不抽奖元组内的奖品保存至 排除奖品ID列表
         * 若没有排除奖品或排除奖品只有一个，则直接返回空字符串或该奖品id
         * 若有多个排除奖品，则新建抽奖规则随机抽取一个奖品
         * todo: 优化
         */
        // 初始化差异分母
        BigDecimal differenceDenominator = BigDecimal.ZERO;
        // 初始化非抽奖奖品列表
        List<AwardRateInfo> differenceAwardRateList = new ArrayList<>();
        // 获取概率元组中所有的奖品种类
        List<AwardRateInfo> awardRateInfos = awardRateInfoMap.get(strategyId);
        // 遍历所有奖品种类
        for (AwardRateInfo awardRateInfo : awardRateInfos) {
            // 获取奖品ID
            String awardId = awardRateInfo.getAwardId();
            // 如果奖品ID不在排除的奖品ID列表中则添加到差异奖品列表中
            if (!excludeAwardIds.contains(awardId)) {
                differenceAwardRateList.add(awardRateInfo);
                differenceDenominator = differenceDenominator.add(awardRateInfo.getAwardRate());
            }
        }
        // 如果差异奖品列表为空则返回空字符串
        if (differenceAwardRateList.isEmpty()) return "";
        // 如果差异奖品列表只有一个则返回该奖品ID
        if (differenceAwardRateList.size() == 1) return differenceAwardRateList.get(0).getAwardId();
        /**
         * 从差异奖品列表中随机抽取奖品
         */
        // 获取随机概率值为0-100的随机数
        SecureRandom secureRandom = new SecureRandom();
        int randomVal = secureRandom.nextInt(100) + 1;
        // 循环获取奖品
        String awardId = "";
        int cursorVal = 0;
        for (AwardRateInfo awardRateInfo : differenceAwardRateList) {
            // 计算奖品概率值
            // Divide the award rate by the difference denominator, multiply by 100, and round up to the nearest integer
            int rateVal = awardRateInfo.getAwardRate().divide(differenceDenominator, 2, BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue();
            // 如果随机数小于等于当前奖品概率值则返回该奖品ID
            if (randomVal <= (cursorVal + rateVal)) {
                awardId = awardRateInfo.getAwardId();
                break;
            }
            cursorVal += rateVal;
        }
        // 抽中当前奖品ID
        return awardId;
    }
}
