package com.lily.lottery.domain.activity.service.stateflow.event;

import com.lily.lottery.common.ActivityConstants;
import com.lily.lottery.common.Constants;
import com.lily.lottery.common.Result;
import com.lily.lottery.domain.activity.service.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * Created by lily via on 2024/6/18 23:42
 * 活动拒绝状态
 * 当前状态为拒绝状态，仅可以撤回申请，关闭
 */
@Component
public class RefuseState extends AbstractState {

    @Override
    public Result arraignment(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "已审核状态不可重复审核");
    }

    @Override
    public Result checkPass(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "已审核状态不可重复审核");
    }

    @Override
    public Result checkRefuse(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动审核拒绝不可重复拒绝");
    }

    @Override
    public Result checkRevoke(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        boolean isSuccess = activityRepositoty.alterStatus(activityId, currentStatus, ActivityConstants.ActivityState.REVOKE);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动审核撤销") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result close(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        boolean isSuccess = activityRepositoty.alterStatus(activityId, currentStatus, ActivityConstants.ActivityState.CLOSE);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动审核关闭"):Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result open(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动非关闭状态不可开启");
    }

    @Override
    public Result doing(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "审核拒绝不可执行活动为进行中");
    }

}
