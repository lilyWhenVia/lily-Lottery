package com.lily.lottery.infrastructure.dao;

import com.lily.lottery.infrastructure.po.StrategyDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by lily via on 2024/6/14 20:25
 */
@Mapper
public interface IStrategyDetailDao {


    List<StrategyDetail> queryStrategyDetailList(Long strategyId);

}
