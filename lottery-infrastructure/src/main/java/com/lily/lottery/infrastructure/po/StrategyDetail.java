package com.lily.lottery.infrastructure.po;

import lombok.*;

import java.math.BigDecimal;

/**
 * 抽奖细节表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StrategyDetail {

    // 自增ID
    private String id;

    // 策略ID
    private Long strategyId;

    // 奖品ID
    private String awardId;

    // 奖品数量
    private String awardCount;

    // 中奖概率
    private BigDecimal awardRate;

    // 奖品库存（实际的奖品剩余数量）
    private String awardSurplusCount;

    // 创建时间
    private String createTime;

    // 修改时间
    private String updateTime;

}
