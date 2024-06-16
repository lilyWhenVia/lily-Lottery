package com.lily.lottery.domain.strategy.service.draw.impl;

import com.lily.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.lily.lottery.domain.strategy.service.draw.AbstractDrawBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


/**
 * Created by lily via on 2024/6/15 15:01
 * 最外层抽奖服务提供类
 */
@Service("drawExec")
@Slf4j
public class DrawExecImpl extends AbstractDrawBase {


    /**
     * 查询剩余奖品库存信息
     * @param strategyId
     * @return
     */
    @Override
    protected List<String> queryExcludeAwardIds(Long strategyId) {
        return Collections.emptyList();
    }

    @Override
    protected String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<String> excludeAwardIds) {
        return "";
    }
}
