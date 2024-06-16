package com.lily.lottery.domain.strategy.model.vo;

/**
 * Created by lily via on 2024/6/14 20:53
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 奖品概率信息，奖品ID
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwardRateInfo {

    // 奖品ID
    private String awardId;

    // 中奖概率
    private BigDecimal awardRate;

}
