package com.lily.lottery.domain.activity.service.stateflow.event;

import com.lily.lottery.common.ActivityConstants;
import com.lily.lottery.common.Constants;
import com.lily.lottery.common.Result;
import com.lily.lottery.domain.activity.service.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * Created by lily via on 2024/6/18 23:42
 * 审核通过状态
 * 当前状态为审核通过状态，仅可以审核拒绝，关闭
 * 以及开启活动
 */
@Component
public class PassState extends AbstractState {

    @Override
    public Result arraignment(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "审核已通过不可重复提审");
    }

    @Override
    public Result checkPass(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "审核已通过不可重复提审");
    }

    @Override
    public Result checkRefuse(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        boolean isSuccess = activityRepositoty.alterStatus(activityId, currentStatus, ActivityConstants.ActivityState.REFUSE);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "审核拒绝完成"): Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result checkRevoke(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "审核通过不可撤销(可先拒绝审核)");
    }

    @Override
    public Result close(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        boolean isSuccess = activityRepositoty.alterStatus(activityId, currentStatus, ActivityConstants.ActivityState.CLOSE);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动审核关闭完成"):Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result open(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动非关闭状态不可开启");
    }

    @Override
    public Result doing(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        boolean isSuccess = activityRepositoty.alterStatus(activityId, currentStatus, ActivityConstants.ActivityState.DOING);
        return isSuccess?Result.buildResult(Constants.ResponseCode.SUCCESS, "变更活动中完成"):Result.buildErrorResult("活动状态变更失败");
    }
}
