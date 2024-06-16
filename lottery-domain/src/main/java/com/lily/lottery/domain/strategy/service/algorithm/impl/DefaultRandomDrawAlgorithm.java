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
 * 单项概率抽奖算法实现
 * 策略模式与工厂模式结合
 * 场景A20%、B30%、C50%
 * 单项概率：如果A奖品抽空后，B和C保持目前中奖概率，用户抽奖扔有20%中为A，
 * 因A库存抽空则结果展示为未中奖。为了运营成本，通常这种情况的使用的比较多
 */
@Component("defaultRandomDrawAlgorithm")
public class DefaultRandomDrawAlgorithm extends BaseAlgorithm {
    //
    /**
     * 随机抽奖
     * 父类中未实现的抽象方法
     * @param strategyId 策略ID
     * @param excludeAwardIds 已经被抽完的奖品ID列表
     *                        不用于构建概率元组的奖品信息
     * @return 奖品ID
     */
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {

        /**
         * 排除已经被抽完的奖品ID，保存剩下的还可以抽奖的ID
         * 若全部奖品都被抽完了，则直接返回空字符串，说明没有获奖
         * 如果只剩下一个奖品了，那么之间获得该奖品
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
            // 如果奖品还没有被抽完则加入差异奖品列表
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
         * 新建规则重新分配概率抽奖
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
