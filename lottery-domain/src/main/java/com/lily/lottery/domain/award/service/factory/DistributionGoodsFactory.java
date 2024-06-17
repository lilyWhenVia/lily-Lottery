package com.lily.lottery.domain.award.service.factory;

import com.lily.lottery.domain.award.service.good.IDistributionGoods;
import com.lily.lottery.domain.award.service.good.impl.CouponGoods;
import com.lily.lottery.domain.award.service.good.impl.PhysicalGoods;

/**
 * Created by lily via on 2024/6/17 14:21
 * 发货服务工厂
 */
public class DistributionGoodsFactory extends GoodsConfig{

    /**
     * 根据商品类型获取发货服务实例
     */
    public IDistributionGoods getDistributionGoodsService(String goodsType) {
        return super.goodsMap.get(goodsType);
    }

}
