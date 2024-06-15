package com.lily.lottery.domain.strategy.service.algorithm;

/**
 * Created by lily via on 2024/6/14 21:08
 */

import com.lily.lottery.domain.strategy.model.vo.AwardRateInfo;

import java.util.List;

/**
 * 抽奖算法接口
 */
public interface IDrawAlgorithm {

    /**
     * 初始化概率元组
     * 1. 将奖品概率数据转换为一整条数组上的分区数据
     * 2. 根据0-100中各个区间的奖品信息，使用斐波那契散列计算出索引位置，把奖品数据存放到元祖中
     * 例如概率为0.2时概率对应20
     * 20对应的斐波那契哈希值：（20 * HASH_INCREMENT + HASH_INCREMENT）= -1549107828 HASH_INCREMENT = 0x61c88647
     * 通过哈希值计算索引位置：hashCode & (rateTuple.length - 1) = 12
     * 那么tup[14] = 0.2 中奖概率对应的奖品
     * 当后续通过随机数获取到1-100的值后，可以直接定位到对应的奖品信息，通过这样的方式把轮巡算法的时间复杂度从O(n) 降低到 0(1)
     *
     * @param strategyId 策略ID
     * @param awardRateInfoList 奖品概率配置集合 「获取示例：AwardRateInfo.awardRate = 0.04」
     */
    void initRateTuple(Long strategyId, List<AwardRateInfo> awardRateInfoList);

    /**
     * 判断元组是否已初始化
     * @param strategyId
     * @return
     */
    boolean isExistRateTuple(Long strategyId);

    /**
     * SecureRandom 生成随机数，计算索引
     *
     * @param strategyId 策略ID
     * @param excludeAwardIds 排除掉已经不能作为抽奖的奖品ID，留给风控和空库存使用
     * @return 中奖结果
     */
    String randomDraw(Long strategyId, List<String> excludeAwardIds);
}
