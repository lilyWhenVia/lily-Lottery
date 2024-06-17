package com.lily.lottery.domain.award.service.good;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lily via on 2024/6/17 14:17
 * 发货服务基类
 */
@Slf4j
public class DistributionBase {

    /**
     * 更新用户获奖状态信息
     *
     * @param userId 用户ID
     * @param orderId 订单ID
     * @param awardId 奖品ID
     * @param awardStatus 奖品状态
     * @param awardStateInfo 奖品状态信息
     */
    protected void updateUserAwardStatus(String userId, String orderId, String awardId, Integer awardStatus, String awardStateInfo) {
        // todo 每个用户抽奖新建一张用户抽奖表。更新用户个人抽奖记录表中奖品发放状态
        log.info("更新用户获奖状态信息, userId: {}, orderId: {}, awardId: {}, awardStatus: {}, awardStateInfo: {}", userId, orderId, awardId, awardStatus, awardStateInfo);
    }
}
