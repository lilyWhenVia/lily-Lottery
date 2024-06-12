package com.lily.lottery.req;

import java.io.Serializable;

/**
 * Created by lily via on 2024/6/12 12:31
 * 活动展位请求
 */
public class ActivityReq implements Serializable {

    private Long activityId;

    /**
     * 获取活动ID
     */
    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
