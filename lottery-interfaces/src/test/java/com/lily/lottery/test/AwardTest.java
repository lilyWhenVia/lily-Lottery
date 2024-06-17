package com.lily.lottery.test;

import com.alibaba.fastjson.JSON;
import com.lily.lottery.common.AwardConstants;
import com.lily.lottery.common.DrawConstants;
import com.lily.lottery.domain.award.model.req.GoodsReq;
import com.lily.lottery.domain.award.model.res.DistributionRes;
import com.lily.lottery.domain.award.service.factory.DistributionGoodsFactory;
import com.lily.lottery.domain.award.service.good.IDistributionGoods;
import com.lily.lottery.domain.strategy.model.req.DrawReq;
import com.lily.lottery.domain.strategy.model.res.DrawResult;
import com.lily.lottery.domain.strategy.model.vo.DrawAwardInfo;
import com.lily.lottery.domain.strategy.service.draw.IDrawExec;
import com.lily.lottery.infrastructure.dao.IActivityDao;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * Created by lily via on 2024/6/17 15:04
 */
@SpringBootTest
public class AwardTest {

    private Logger logger = LoggerFactory.getLogger(SpringRunnerTest.class);

    @Resource
    private IActivityDao activityDao;

    @Resource
    private IDrawExec drawExec;

    @Resource
    private DistributionGoodsFactory distributionGoodsFactory;

    @Test
    public void test_award() {
        // 执行抽奖
        DrawResult drawResult = drawExec.doDrawExec(new DrawReq("小傅哥", 10001L));

        // 判断抽奖结果
        Integer drawState = drawResult.getDrawState();
        if (DrawConstants.DrawState.FAIL.getCode().equals(drawState)) {
            logger.info("未中奖 DrawAwardInfo is null");
            return;
        }

        // 封装发奖参数，orderId：2109313442431 为模拟ID，需要在用户参与领奖活动时生成
        DrawAwardInfo drawAwardInfo = drawResult.getDrawAwardInfo();
        GoodsReq goodsReq = new GoodsReq(drawResult.getUId(), "2109313442431", drawAwardInfo.getAwardId(), drawAwardInfo.getAwardName(), drawAwardInfo.getAwardContent());

        // 根据 awardType 从抽奖工厂中获取对应的发奖服务
        IDistributionGoods distributionGoodsService = distributionGoodsFactory.getDistributionGoodsService(drawAwardInfo.getAwardType());
        DistributionRes distributionRes = distributionGoodsService.doDistribution(goodsReq);

        logger.info("测试结果：{}", JSON.toJSONString(distributionRes));
    }

}
