package com.lily.lottery.common;

import java.io.Serializable;

/**
 * Created by lily via on 2024/6/12 12:36
 * 通用返回结果
 */
public class Result implements Serializable {
    private static final long serialVersionUID = -3826891916021780628L;
    private String code;
    private String info;


    /**
     * 参数为String类型
     * @param code
     * @param info
     */
    public Result(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static Result buildResult(String code, String info) {
        return new Result(code, info);
    }

    /**
     * 参数为枚举类型
     * @param code
     * @param info
     * @return
     */
    public static Result buildResult(Constants.ResponseCode code, String info) {
        return new Result(code.getCode(), info);
    }

    public static Result buildResult(Constants.ResponseCode code, Constants.ResponseCode info) {
        return new Result(code.getCode(), info.getInfo());
    }

    /**
     * 无参构造
     */
    public Result() {
    }

    public static Result buildSuccessResult() {
        return new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
    }

    public static Result buildErrorResult() {
        return new Result(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
    }
}
