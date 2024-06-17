package com.lily.lottery.domain.award.service.good;

import com.lily.lottery.domain.award.model.req.GoodsReq;
import com.lily.lottery.domain.award.model.res.DistributionRes;

/**
 * Created by lily via on 2024/6/17 14:16
 * 发货服务接口
 */
public interface IDistributionGoods {
    // 调用数据层接口，进行相应的发货处理

    /**
     * @description: 奖品配送接口
     * 奖品类型：1-实物商品，2-优惠券，3-降价商品，4-文字描述
            * @param: req
            * @return:
            * @author lily via
            * @date: 2024/6/17 14:31
     */
    DistributionRes doDistribution(GoodsReq req);

    /**
     * 获取发货商品名称
     */
    Integer getDistributionGoodsName();
}
