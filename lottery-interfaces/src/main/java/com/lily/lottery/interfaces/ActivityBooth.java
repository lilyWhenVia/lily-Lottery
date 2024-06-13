package com.lily.lottery.interfaces;

import com.alibaba.dubbo.config.annotation.Service;
import com.lily.lottery.IActivityBooth;
import com.lily.lottery.common.Constants;
import com.lily.lottery.common.Result;
import com.lily.lottery.dto.ActivityDto;
import com.lily.lottery.infrastructure.dao.IActivityDao;
import com.lily.lottery.infrastructure.po.Activity;
import com.lily.lottery.req.ActivityReq;
import com.lily.lottery.res.ActivityRes;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * Created by lily via on 2024/6/12 14:18
 */
@DubboService
public class ActivityBooth implements IActivityBooth {
    @Resource
    private IActivityDao activityDao;

    @Override
    public ActivityRes queryActivityById(ActivityReq req) {

        Activity activity = activityDao.queryActivityById(req.getActivityId());

        ActivityDto activityDto = new ActivityDto();
        activityDto.setActivityId(activity.getActivityId());
        activityDto.setActivityName(activity.getActivityName());
        activityDto.setActivityDesc(activity.getActivityDesc());
        activityDto.setBeginDateTime(activity.getBeginDateTime());
        activityDto.setEndDateTime(activity.getEndDateTime());
        activityDto.setStockCount(activity.getStockCount());
        activityDto.setTakeCount(activity.getTakeCount());

        return new ActivityRes(new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo()), activityDto);
    }
}
