package com.lily.lottery.domain.strategy.model.res;

/**
 * Created by lily via on 2024/6/14 20:50
 */

import com.lily.lottery.common.Constants;
import com.lily.lottery.domain.strategy.model.vo.DrawAwardInfo;
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

    /**
     * 中奖状态：0未中奖、1已中奖、2兜底奖 Constants.DrawState
     */
    private Integer drawState = Constants.DrawState.FAIL.getCode();

    /**
     * 中奖奖品信息
     */
    private DrawAwardInfo drawAwardInfo;


    public DrawResult(String uId, Long strategyId, Integer drawState) {
        this.uId = uId;
        this.strategyId = strategyId;
        this.drawState = drawState;
    }

}
