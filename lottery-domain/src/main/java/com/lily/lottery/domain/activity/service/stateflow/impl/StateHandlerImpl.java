package com.lily.lottery.domain.activity.service.stateflow.impl;

import com.lily.lottery.common.ActivityConstants;
import com.lily.lottery.common.Result;
import com.lily.lottery.domain.activity.repository.IActivityRepositoty;
import com.lily.lottery.domain.activity.service.stateflow.IStateHandler;
import com.lily.lottery.domain.activity.service.stateflow.StateConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lily via on 2024/6/19 0:02
 * 活动状态处理服务
 * 服务当前的状态可以调用任意的其他状态
 *---
 * 由对应的状态实例中定义好的方法来决定是否可以进行状态的切换
 */
@Service
public class StateHandlerImpl extends StateConfig implements IStateHandler {

    /**
     * 提审操作
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    @Override
    public Result arraignment(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return stateMap.get(currentStatus).arraignment(activityId, currentStatus);
    }

    /**
     * 通过操作
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    @Override
    public Result checkPass(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return stateMap.get(currentStatus).checkPass(activityId, currentStatus);
    }

    /**
     * 拒绝操作
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    @Override
    public Result checkRefuse(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return stateMap.get(currentStatus).checkRefuse(activityId, currentStatus);
    }

    /**
     * 撤销操作
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    @Override
    public Result checkRevoke(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return stateMap.get(currentStatus).checkRevoke(activityId, currentStatus);
    }

    /**
     * 关闭操作
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    @Override
    public Result close(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return stateMap.get(currentStatus).close(activityId, currentStatus);
    }

    /**
     * 活动状态开启操作
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    @Override
    public Result open(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return stateMap.get(currentStatus).open(activityId, currentStatus);
    }

    /**
     * 活动状态进行中申请
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    @Override
    public Result doing(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return stateMap.get(currentStatus).doing(activityId, currentStatus);
    }
}
