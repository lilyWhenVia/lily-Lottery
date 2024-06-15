package com.lily.lottery.domain.strategy.service.draw;

import com.lily.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lily via on 2024/6/15 15:00
 * 根据抽奖模式选择进行实例化抽奖策略算法
 * 决定抽奖是按照必中奖方式抽奖还是按照概率抽奖
 */
public class DrawConfig {

    @Resource(name = "defaultRandomDrawAlgorithm")
    private IDrawAlgorithm defaultDrawAlgorithm;

    @Resource(name = "singleRateRandomDrawAlgorithm")
    private IDrawAlgorithm singleRateDrawAlgorithm;

    protected static Map<Integer, IDrawAlgorithm> drawAlgorithmMap = new ConcurrentHashMap<>();

    /**
     * 当一个 bean 被创建并注入到 Spring 容器中时,
     * 被 @PostConstruct 注解标记的方法会在构造函数执行后自动被调用
     */
    @PostConstruct
    public void init() {
        drawAlgorithmMap.put(1, defaultDrawAlgorithm);
        drawAlgorithmMap.put(2, singleRateDrawAlgorithm);
    }
}
