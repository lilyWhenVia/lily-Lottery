package com.lily.lottery.infrastructure.dao;

import com.lily.lottery.domain.activity.model.vo.AlterStateVO;
import com.lily.lottery.infrastructure.po.Activity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by lily via on 2024/6/12 12:00
 * 对活动信息表的操作mapper类
 */
@Mapper
public interface IActivityDao {

    void insert(Activity req);

    Activity queryActivityById(Long activityId);

    int alterState(AlterStateVO alterStateVO);

}
