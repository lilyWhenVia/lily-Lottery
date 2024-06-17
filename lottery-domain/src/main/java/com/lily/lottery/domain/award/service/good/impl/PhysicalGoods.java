package com.lily.lottery.domain.award.service.good.impl;

import com.lily.lottery.common.AwardConstants;
import com.lily.lottery.domain.award.model.req.GoodsReq;
import com.lily.lottery.domain.award.model.res.DistributionRes;
import com.lily.lottery.domain.award.service.good.DistributionBase;
import com.lily.lottery.domain.award.service.good.IDistributionGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by lily via on 2024/6/17 14:17
 * 实体商品发货服务实现类
 */
@Component("PhysicalGoods")
@Slf4j
public class PhysicalGoods extends DistributionBase implements IDistributionGoods {
    /**
     * 发货
     * @param req
     * @return
     */
    @Override
    public DistributionRes doDistribution(GoodsReq req) {
        // 模拟调用兑换码
        log.info("模拟调用兑换码 uId：{} awardContent：{}", req.getUId(), req.getAwardContent());
        // 更新用户领奖结果
        super.updateUserAwardStatus(req.getUId(), req.getOrderId(), req.getAwardId(), AwardConstants.AwardState.SUCCESS.getCode(), AwardConstants.AwardState.SUCCESS.getInfo());
        // 返回发货结果
        return new DistributionRes(req.getUId(), AwardConstants.AwardState.SUCCESS.getCode(), AwardConstants.AwardState.SUCCESS.getInfo());
    }

    /**
     * 获取发货商品种类
     * @return
     */
    @Override
    public Integer getDistributionGoodsName() {
        return AwardConstants.AwardType.PhysicalGoods.getCode();
    }
}
