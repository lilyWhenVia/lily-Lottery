package com.lily.lottery.domain.award.service.good.impl;

import com.lily.lottery.domain.award.model.req.GoodsReq;
import com.lily.lottery.domain.award.model.res.DistributionRes;
import com.lily.lottery.domain.award.service.good.DistributionBase;
import com.lily.lottery.domain.award.service.good.IDistributionGoods;
import org.springframework.stereotype.Component;

/**
 * Created by lily via on 2024/6/17 14:17
 * 实体商品发货服务实现类
 */
@Component
public class PhysicalGoods extends DistributionBase implements IDistributionGoods {
    @Override
    public DistributionRes doDistribution(GoodsReq req) {
        return null;
    }

    @Override
    public Integer getDistributionGoodsName() {
        return 0;
    }
}
