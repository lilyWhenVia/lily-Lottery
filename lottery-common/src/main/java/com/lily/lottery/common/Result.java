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


    public Result() {
    }

    public Result(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static Result buildResult(String code, String info) {
        return new Result(code, info);
    }

    public static Result buildSuccessResult() {
        return new Result(DrawConstants.ResponseCode.SUCCESS.getCode(), DrawConstants.ResponseCode.SUCCESS.getInfo());
    }

    public static Result buildErrorResult() {
        return new Result(DrawConstants.ResponseCode.UN_ERROR.getCode(), DrawConstants.ResponseCode.UN_ERROR.getInfo());
    }
}
