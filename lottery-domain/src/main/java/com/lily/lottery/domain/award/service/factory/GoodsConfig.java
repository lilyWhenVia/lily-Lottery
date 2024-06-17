package com.lily.lottery.domain.award.service.factory;

import com.lily.lottery.common.AwardConstants;
import com.lily.lottery.domain.award.service.good.IDistributionGoods;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lily via on 2024/6/17 14:22
 * 发货工厂配置
 */
public class GoodsConfig {

    @Resource(name="CouponGoods")
    private IDistributionGoods couponGoods;

    @Resource(name="DescGoods")
    private IDistributionGoods descGoods;

    @Resource(name="PhysicalGoods")
    private IDistributionGoods physicalGoods;

    @Resource(name="RedeemCodeGoods")
    private IDistributionGoods redeemCodeGoods;

    /**
     * 商品发放服务实例
     */
    public static Map<Integer, IDistributionGoods> goodsMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        goodsMap.put(AwardConstants.AwardType.CouponGoods.getCode(), couponGoods);
        goodsMap.put(AwardConstants.AwardType.DESC.getCode(), descGoods);
        goodsMap.put(AwardConstants.AwardType.PhysicalGoods.getCode(), physicalGoods);
        goodsMap.put(AwardConstants.AwardType.RedeemCodeGoods.getCode(), redeemCodeGoods);
    }
}
