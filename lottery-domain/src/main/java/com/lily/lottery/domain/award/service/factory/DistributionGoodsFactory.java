package com.lily.lottery.domain.award.service.factory;

import com.lily.lottery.domain.award.service.good.IDistributionGoods;
import com.lily.lottery.domain.award.service.good.impl.CouponGoods;
import com.lily.lottery.domain.award.service.good.impl.PhysicalGoods;
import org.springframework.stereotype.Service;

/**
 * Created by lily via on 2024/6/17 14:21
 * 发货服务工厂
 */
@Service
public class DistributionGoodsFactory extends GoodsConfig{

    /**
     * 根据商品类型获取发货服务实例
     */
    public IDistributionGoods getDistributionGoodsService(Integer goodsType) {
        return super.goodsMap.get(goodsType);
    }

}
