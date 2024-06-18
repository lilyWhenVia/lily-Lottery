package com.lily.lottery.infrastructure.dao;

import com.lily.lottery.infrastructure.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by lily via on 2024/6/14 20:24
 */
@Mapper
public interface IAwardDao {

    /*
     * 查询奖品信息
     */
    Award queryAwardInfo(String awardId);

    /**
     * 插入奖品配置
     *
     * @param list 奖品配置
     */
    void insertList(List<Award> list);

}
