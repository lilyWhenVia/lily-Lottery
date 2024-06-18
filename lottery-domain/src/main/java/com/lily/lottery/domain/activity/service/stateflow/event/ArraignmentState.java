package com.lily.lottery.domain.activity.service.stateflow.event;

import com.lily.lottery.common.ActivityConstants;
import com.lily.lottery.common.Constants;
import com.lily.lottery.common.Result;
import com.lily.lottery.domain.activity.repository.IActivityRepositoty;
import com.lily.lottery.domain.activity.service.stateflow.AbstractState;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lily via on 2024/6/18 23:42
 * 提交审核状态
 * 当前状态为审核状态，不可以再次提交审核
 * 当前状态为审核状态，可以审核通过、审核拒绝、审核撤销
 * 当前状态为审核状态，可以关闭
 * 当前状态为审核状态，不可以开启活动
 * 当前状态为审核状态，不可以执行活动中操作
 * @author lily
 */
@Component
public class ArraignmentState extends AbstractState {


    @Override
    public Result arraignment(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "待审核状态不可重复提审");
    }

    /**
     * 变更状态时需要传入当前状态
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return
     */
    @Override
    public Result checkPass(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        boolean isSuccess = activityRepositoty.alterStatus(activityId, currentStatus, ActivityConstants.ActivityState.PASS);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动审核通过完成") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result checkRefuse(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        boolean isSuccess = activityRepositoty.alterStatus(activityId, currentStatus, ActivityConstants.ActivityState.REFUSE);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动审核拒绝完成"): Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result checkRevoke(Long activityId, Enum<ActivityConstants.ActivityState> currentStatus) {
        boolean isSuccess = activityRepositoty.alterStatus(activityId, currentStatus, ActivityConstants.ActivityState.REVOKE);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动审核撤销回编辑中") : Result.buildErrorResult("活动状态变更失败");
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
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "待审核活动不可执行活动中操作");
    }
}
