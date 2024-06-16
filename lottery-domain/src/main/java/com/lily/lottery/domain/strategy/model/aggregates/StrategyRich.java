package com.lily.lottery.domain.strategy.model.aggregates;

/**
 * Created by lily via on 2024/6/14 20:45
 */

import com.lily.lottery.infrastructure.po.Strategy;
import com.lily.lottery.infrastructure.po.StrategyDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 抽奖详情与策略聚合类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StrategyRich {


    // 策略ID
    private Long strategyId;
    // 策略配置
    private Strategy strategy;
    // 策略明细
    private List<StrategyDetail> strategyDetailList;

}
