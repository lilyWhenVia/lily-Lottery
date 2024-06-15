package com.lily.lottery.domain.strategy.model.req;

/**
 * Created by lily via on 2024/6/14 20:48
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 抽奖请求
 */
@Data
@NoArgsConstructor
public class DrawReq {

        private Long strategyId;

        private String uId;

        public DrawReq(Long strategyId, String uId) {
            this.strategyId = strategyId;
            this.uId = uId;
        }

}
