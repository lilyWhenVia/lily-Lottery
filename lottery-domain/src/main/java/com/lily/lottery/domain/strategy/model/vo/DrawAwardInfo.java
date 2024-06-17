package com.lily.lottery.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lily via on 2024/6/16 18:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrawAwardInfo {


    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
     */
    private Integer awardType;

    /**
     * 奖品名称
     */
    private String awardName;

    /**
     * 奖品内容「描述、奖品码、sku」
     */
    private String awardContent;

    public DrawAwardInfo(String awardId, String awardName) {
        this.awardId = awardId;
        this.awardName = awardName;
    }

    public DrawAwardInfo(String awardId, String awardName, Integer awardType){
        this.awardId = awardId;
        this.awardName = awardName;
        this.awardType = awardType;
    }

}
