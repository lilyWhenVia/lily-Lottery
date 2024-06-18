package com.lily.lottery.domain.activity.service.stateflow.event;

import com.lily.lottery.common.ActivityConstants;
import com.lily.lottery.common.Constants;
import com.lily.lottery.common.Result;
import com.lily.lottery.domain.activity.service.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * Created by lily via on 2024/6/18 23:42
 * 活动编辑中
 * 当前状态为编辑中状态，仅可以提交审核，关闭当前状态
 */
@Component
public class EditingState extends AbstractState {
    @Override
    public Result arraignment(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        boolean isSuccess = activityRepositoty.alterStatus(activityId, currentState, ActivityConstants.ActivityState.ARRAIGNMENT);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动提审成功") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result checkPass(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "编辑中不可审核通过");
    }

    @Override
    public Result checkRefuse(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "编辑中不可审核拒绝");
    }

    @Override
    public Result checkRevoke(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "编辑中不可撤销审核");
    }

    @Override
    public Result close(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        boolean isSuccess = activityRepositoty.alterStatus(activityId, currentState, ActivityConstants.ActivityState.CLOSE);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动关闭成功") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result open(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "非关闭活动不可开启");
    }

    @Override
    public Result doing(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "编辑中活动不可执行活动中变更");
    }

}
