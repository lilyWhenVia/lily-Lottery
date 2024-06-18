package com.lily.lottery.domain.strategy.service.draw;

import com.lily.lottery.common.DrawConstants;
import com.lily.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.lily.lottery.domain.strategy.model.req.DrawReq;
import com.lily.lottery.domain.strategy.model.res.DrawResult;
import com.lily.lottery.domain.strategy.model.vo.AwardRateInfo;
import com.lily.lottery.domain.strategy.model.vo.DrawAwardInfo;
import com.lily.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.lily.lottery.infrastructure.po.Award;
import com.lily.lottery.infrastructure.po.Strategy;
import com.lily.lottery.infrastructure.po.StrategyDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lily via on 2024/6/16 16:32
 * 定义抽象抽奖过程，模板模式
 */
@Slf4j
public abstract class AbstractDrawBase extends DrawStrategySupport implements IDrawExec{

    /**
     * 定义通用的抽奖流程
     * @param req 抽奖请求
     * @return 抽奖结果
     */
    @Override
    public DrawResult doDrawExec(DrawReq req) {
        // 1，获取抽奖策略, 进而用于下一步检验
        StrategyRich strategyRich = super.queryStrategyRich(req.getStrategyId());
        Strategy strategy = strategyRich.getStrategy();

        // 2，查看抽奖策略是否已经初始化到内存
        this.checkAndInitRateData(strategy.getStrategyId(), strategy.getStrategyMode(), strategyRich.getStrategyDetailList());
        // 3. 获取不可抽奖奖品ID列表，包括：奖品库存为空、风控策略、临时调整等
        List<String> excludeAwardIds = this.queryExcludeAwardIds(strategy.getStrategyId());

        // 4. 执行抽奖算法
        // 根据策略方式抽奖, 根据mode从config中初始并封装好的的map中获取对应的算法实例
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategy.getStrategyMode());
        String awardId = this.drawAlgorithm(strategy.getStrategyId(), drawAlgorithm, excludeAwardIds);
        // 5. 封装抽奖结果
        return buildDrawResult(req.getUId(), req.getStrategyId(), awardId);
    }


    /**
     * @description:  1. 查询不允许抽奖的奖品ID列表，
     * 其中包括：奖品库存为空（已抽完）、风控策略、临时调整等
            * @param: strategyId
            * @return:
            * @author lily via
            * @date: 2024/6/16 16:39
     */
    protected abstract List<String> queryExcludeAwardIds(Long strategyId);

    /**
     * @description: 2. 校验概率元组是否已经初始化到内存
     * @param: strategyId
     * @param: strategyMode
     * @param: strategyDetailList
     * @return: void
     * @date: 2024/6/16 16:41
     */
    private void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyDetail> strategyDetailList) {
        /*
         *根据抽奖模式获取对应抽奖算法
         */
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategyMode);
        /*
         * 判断是否已经初始化过抽奖概率元组
         * 如果已经初始化过抽奖概率元组，不需要重新初始化
         */
        boolean existRateTuple = drawAlgorithm.isExistRateTuple(strategyId);
        /*
         * 如果概率元组已存在且不是必中奖模式（单项概率），不需要重新初始化抽奖概率数据
         * 1:单项概率、2:总体概率
         */
        if (existRateTuple && !DrawConstants.StrategyMode.SINGLE.getCode().equals(strategyMode)) {
            return;
        }

        /*
          整体概率模式，未初始化概率元组需要重新初始化
         */
        if (existRateTuple) {
            return;
        }
        /*
         * 封装奖品信息用于初始化抽奖概率元组
         */
        List<AwardRateInfo> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
        for (StrategyDetail strategyDetail : strategyDetailList) {
            awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(), strategyDetail.getAwardRate()));
        }
        /*
         * 初始化抽奖概率元组
         */
        drawAlgorithm.initRateTuple(strategyId, awardRateInfoList);
    }


    /**
     * @description: 3. 根据模型选择并执行抽奖算法
     * @param: strategyId
     * @param: drawAlgorithm
     * @param: excludeAwardIds
     * @return: java.lang.String
     * @date: 2024/6/16 16:40
     */
    protected abstract String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<String> excludeAwardIds);


    /**
     * @description: 4. 封装抽奖结果
     * @param: uId
     * @param: strategyId
     * @param: awardId
     * @return: com.lily.lottery.domain.strategy.model.aggregates.DrawResult
     * @date: 2024/6/16 16:40
     */
    private DrawResult buildDrawResult(String uId, Long strategyId, String awardId){
        // 上层未中奖
        if (awardId == null){
            log.info("执行策略抽奖完成【未中奖】，用户：{} 策略ID：{}", uId, strategyId);
            return new DrawResult(uId, strategyId, DrawConstants.DrawState.FAIL.getCode());

        }
        // 封装奖品信息
        Award award = super.queryAwardInfoByAwardId(awardId);
        DrawAwardInfo drawAwardInfo = new DrawAwardInfo(award.getAwardId(), award.getAwardType(), award.getAwardName(), award.getAwardContent());
        log.info("执行策略抽奖完成【已中奖】，用户：{} 策略ID：{} 奖品ID：{} 奖品名称：{}", uId, strategyId, awardId, award.getAwardName());
        return new DrawResult(uId, strategyId, DrawConstants.DrawState.SUCCESS.getCode(), drawAwardInfo);

    }
}
