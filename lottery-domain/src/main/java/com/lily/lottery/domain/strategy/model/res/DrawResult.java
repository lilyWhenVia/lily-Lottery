package com.lily.lottery.domain.strategy.model.res;

/**
 * Created by lily via on 2024/6/14 20:50
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 抽奖结果信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrawResult {

    // 用户ID
    private String uId;

    // 策略ID
    private Long strategyId;

    private String awardId;

    private String awardName;


}
