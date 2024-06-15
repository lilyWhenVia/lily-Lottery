package com.lily.lottery.domain.strategy.service.draw;

import com.lily.lottery.domain.strategy.model.req.DrawReq;
import com.lily.lottery.domain.strategy.model.res.DrawResult;

/**
 * Created by lily via on 2024/6/15 15:00
 * 抽奖算法接口
 */
public interface IDrawExec {

    /**
     * 执行抽奖
     * @return 抽奖结果封装类
     */
    DrawResult doDrawExec(DrawReq req);
}
