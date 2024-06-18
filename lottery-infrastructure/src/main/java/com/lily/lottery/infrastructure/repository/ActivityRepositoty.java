package com.lily.lottery.infrastructure.repository;


import com.lily.lottery.common.ActivityConstants;
import com.lily.lottery.domain.activity.model.vo.ActivityVO;
import com.lily.lottery.domain.activity.model.vo.AwardVO;
import com.lily.lottery.domain.activity.model.vo.StrategyDetailVO;
import com.lily.lottery.domain.activity.model.vo.StrategyVO;
import com.lily.lottery.domain.activity.repository.IActivityRepositoty;

import java.util.List;

/**
 * Created by lily via on 2024/6/18 23:14
 */
public class ActivityRepositoty implements IActivityRepositoty {


    @Override
    public void addActivity(ActivityVO activity) {

    }

    @Override
    public void addAward(List<AwardVO> awardList) {

    }

    @Override
    public void addStrategy(StrategyVO strategy) {

    }

    @Override
    public void addStrategyDetailList(List<StrategyDetailVO> strategyDetailList) {

    }

    @Override
    public boolean alterStatus(Long activityId, Enum<ActivityConstants.ActivityState> beforeState, Enum<ActivityConstants.ActivityState> afterState) {
        return false;
    }
}
