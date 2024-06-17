package com.lily.lottery.domain.award.service.factory;

import com.lily.lottery.domain.award.service.good.IDistributionGoods;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lily via on 2024/6/17 14:22
 * 发货工厂配置
 */
public class GoodsConfig {

    @Resource
    private IDistributionGoods couponGoods;

    @Resource
    private IDistributionGoods descGoods;

    @Resource
    private IDistributionGoods physicalGoods;

    @Resource
    private IDistributionGoods redeemGoods;

    /**
     * 商品发放服务实例
     */
    public static Map<String, IDistributionGoods> goodsMap = new ConcurrentHashMap<>();
    @PostConstruct
    public void init(){
        goodsMap.put("couponGoods", couponGoods);
        goodsMap.put("descGoods", descGoods);
        goodsMap.put("physicalGoods", physicalGoods);
        goodsMap.put("redeemGoods", redeemGoods);
    }
}
