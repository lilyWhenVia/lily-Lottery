package com.lily.lottery.domain.activity.service.stateflow.event;

import com.lily.lottery.common.ActivityConstants;
import com.lily.lottery.common.Constants;
import com.lily.lottery.common.Result;
import com.lily.lottery.domain.activity.service.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * Created by lily via on 2024/6/18 23:42
 * 关闭状态
 * 当前状态为关闭状态，仅可以开启
 */
@Component
public class CloseState extends AbstractState {
    @Override
    public Result arraignment(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动关闭不可提审");
    }

    @Override
    public Result checkPass(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动关闭不可审核通过");
    }

    @Override
    public Result checkRefuse(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动关闭不可审核拒绝");
    }

    @Override
    public Result checkRevoke(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动关闭不可撤销审核");
    }

    @Override
    public Result close(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动关闭不可重复关闭");
    }

    @Override
    public Result open(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        boolean isSuccess = activityRepositoty.alterStatus(activityId, currentStatus, ActivityConstants.ActivityState.OPEN);
        return isSuccess?Result.buildResult(Constants.ResponseCode.SUCCESS, "活动开启完成"): Result.buildErrorResult("活动开启失败");
    }

    @Override
    public Result doing(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动关闭不可变更活动中");
    }
}
