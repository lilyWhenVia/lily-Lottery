package com.lily.lottery.domain.activity.service.stateflow;

import com.lily.lottery.common.ActivityConstants;
import com.lily.lottery.domain.activity.service.stateflow.event.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lily via on 2024/6/18 23:34
 * 活动状态配置，当前活动的状态可以直接从活动属性中获取
 * 根据需求获取对应状态实例
 */
public class StateConfig {

    @Resource
    private EditingState editingState;

    @Resource
    private ArraignmentState arraignmentState;

    @Resource
    private OpenState openState;

    @Resource
    private DoingState doingState;

    @Resource
    private PassState passState;

    @Resource
    private RefuseState refuseState;

    @Resource
    private CloseState closeState;



    /**
     * 状态实例映射
     */
    public static Map<Integer, AbstractState> stateMap = new ConcurrentHashMap<>();

    /**
     * 初始化状态实例
     */
    @PostConstruct
    public void initState() {
        stateMap.put(ActivityConstants.ActivityState.EDIT.getCode(), editingState);
        stateMap.put(ActivityConstants.ActivityState.ARRAIGNMENT.getCode(), arraignmentState);
        stateMap.put(ActivityConstants.ActivityState.DOING.getCode(), doingState);
        stateMap.put(ActivityConstants.ActivityState.CLOSE.getCode(), closeState);
        stateMap.put(ActivityConstants.ActivityState.OPEN.getCode(), openState);
        stateMap.put(ActivityConstants.ActivityState.PASS.getCode(), passState);
        stateMap.put(ActivityConstants.ActivityState.REFUSE.getCode(), refuseState);
    }
}
