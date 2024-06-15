package com.lily.lottery.domain.strategy.service.algorithm;

import com.lily.lottery.domain.strategy.model.vo.AwardRateInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lily via on 2024/6/14 21:20
 * 抽奖算法基类
 */
/**
 * @description: 使用斐波那契方法初始化概率元组，将奖品概率数据转换为一整条数组上的分区数据
 * @author Lily Via
 * @date 2024/6/15 16:02
 * @version 1.0
 */
public abstract class BaseAlgorithm implements IDrawAlgorithm{

    // 斐波那契散列增量，逻辑：黄金分割点：(√5 - 1) / 2 = 0.6180339887，Math.pow(2, 32) * 0.6180339887 = 0x61c88647
    private final int HASH_INCREMENT = 0x61c88647;

    /**
     *  斐波那契数列数组长度
     *  概率在0-100之间，所以数组长度为128，可以覆盖0-100的概率，但是又不会过度浪费空间
     *  斐波那契散列法使得在0-100之间的数据映射到128的数组中尽可能少的产生冲突
     */
    private final int RATE_TUPLE_LENGTH = 128;

    /**
     * 存放概率与奖品对应的散列结果，strategyId -> rateTuple
     * 将初始化好的概率元组与其对应的策略id保存在本地缓存中，可以减少查询数据库再进行计算的性能损耗
     */
    protected Map<Long, String[]> rateTupleMap = new ConcurrentHashMap<>();

    /**
     * 存放策略id与奖品区间概率值的映射关系，strategyId -> awardRateInfoList
     * awardRateInfoList中是每一组的奖品概率rate与奖品id
     */
    protected Map<Long, List<AwardRateInfo>> awardRateInfoMap = new ConcurrentHashMap<>();

    @Override
    public void initRateTuple(Long strategyId, List<AwardRateInfo> awardRateInfoList) {
        // 初始化awardRateInfo，保存奖品概率信息
        awardRateInfoMap.put(strategyId, awardRateInfoList);
        /**
         * 初始化rateTuple数组
         * 如果strategyId不存在就创建一个长度为RATE_TUPLE_LENGTH的String数组
         * 因为此时rateTupleMap未初始化，所以这一步其实就等于存入了一个strategyId为键，rateTuple为值的键值对
         */
        String[] rateTuple = rateTupleMap.computeIfAbsent(strategyId, k -> new String[RATE_TUPLE_LENGTH]);

        // 初始化cursorVal
        int cursorVal = 0;
        /**
         * 遍历awardRateInfoList，获取每一个奖品的概率值
         * 将概率值通过斐波那契函散列映射到rateTuple数组中
         * 下标通过斐波那契散列法计算
         * 值存入awardId
         */
         for (AwardRateInfo awardRateInfo : awardRateInfoList) {
            // 将奖品概率值转换为百分制，得到数组中该种奖品存放的个数
            int rateVal = awardRateInfo.getAwardRate().multiply(new BigDecimal(100)).intValue();
            // 循环次数为 0-rateVal，也就是奖品存入次数
             // 个人认为此处不需要加cursorVal， 因为rateVal就能控制循环次数
            for (int i = cursorVal + 1; i <= (rateVal + cursorVal); i++) {
                rateTuple[hashIdx(i)] = awardRateInfo.getAwardId();
            }
            cursorVal += rateVal;
        }

    }

    /**
     * 判断是否已经，做了数据初始化
     * @param strategyId
     * @return
     */
    @Override
    public boolean isExistRateTuple(Long strategyId) {
        // 判断rateTupleMap中是否存在strategyId
        return rateTupleMap.containsKey(strategyId);
    }


    /**
     * 斐波那契（Fibonacci）散列法，计算哈希索引下标值
     * @param val
     * @return
     */
    protected int hashIdx(int val) {
        // 使用黄金分割点计算哈希值
        int hashCode = val * HASH_INCREMENT + HASH_INCREMENT;
        // 哈希值与数组长度取模，得到下标
        return hashCode & (RATE_TUPLE_LENGTH - 1);
    }
}
