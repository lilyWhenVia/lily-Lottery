package com.lily.lottery.domain.strategy.service.draw.impl;

import com.alibaba.fastjson.JSON;
import com.lily.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.lily.lottery.domain.strategy.service.draw.AbstractDrawBase;
import com.lily.lottery.infrastructure.po.Award;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * Created by lily via on 2024/6/15 15:01
 * 最外层抽奖服务提供类
 */
@Service("drawExec")
@Slf4j
public class DrawExecImpl extends AbstractDrawBase {


    /**
     * 查询剩余奖品库存信息
     * @param strategyId 策略ID
     * @return 剩余库存奖品ID列表
     */
    @Override
    protected List<String> queryExcludeAwardIds(Long strategyId) {
        // 查询没有库存的奖品列表
        List<String> awardList = strategyRepository.queryNoStockStrategyAwardList(strategyId);
        log.info("执行抽奖策略 strategyId：{}，无库存奖品列表ID集合 awardList：{}", strategyId,  JSON.toJSONString(awardList));
        return awardList;
    }

    /**
     * 实现抽奖算法
     * @param strategyId 策略ID
     * @param drawAlgorithm 抽奖算法选择
     * @param excludeAwardIds 排除的奖品ID
     * @return 返回null则表示每中奖（未中奖或扣去库存失败）
     * 返回中奖奖品ID则表示中奖
     */
    @Override
    protected String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<String> excludeAwardIds) {
        // 调用策略模式封装好的抽奖算法进行抽奖
        String awardId = drawAlgorithm.randomDraw(strategyId, excludeAwardIds);

        // 如果没有抽中奖品或者抽中的奖品库存不足，则返回null
        if (null == awardId) {
            return null;
        }

        /**
         * 抽取成功扣取库存
         * 扣减库存，暂时采用数据库行级锁的方式进行扣减库存，后续优化为 Redis 分布式锁扣减 decr/incr
         * 注意：通常数据库直接锁行记录的方式并不能支撑较大体量的并发，但此种方式需要了解，
         * 因为在分库分表下的正常数据流量下的个人数据记录中，
         * 是可以使用行级锁的，因为他只影响到自己的记录，不会影响到其他人
         */
        boolean isSuccess = strategyRepository.deductStock(strategyId, awardId);
        return isSuccess ? awardId : null;
    }
}
