package com.lwl.base.common.vo;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

/**
 * 操作结果实体（仅在controller层使用）
 * @author linwenli
 * @date 2020-01-08 10:54
 */
@Data
public class Result<T> {

    /**操作结果码*/
    private Integer code;
    /**提示消息*/
    private String msg;
    /**返回数据*/
    private T data;

    public Result() {}

    public Result(ResultCode resultCode, String msg, T data) {
        this.code = resultCode.getCode();
        this.msg = StringUtils.isEmpty(msg) ? resultCode.getReasonPhrase() : msg;
        this.data = data;
    }

    /**获取Result实例*/
    public static <T> Result<T> getInstance(ResultCode resultCode, String msg, T data) {
        return new Result<>(resultCode, msg, data);
    }

    public static <T> Result<T> getInstance(ResultCode resultCode, String msg, Class<T> t) {
        return new Result<>(resultCode, msg,null);
    }

    /**请求成功*/
    public static <T> Result<T> success(ResultCode resultCode, String msg, T data) {
        if (resultCode == null) {
            resultCode = ResultCode.OK;
        }
        return Result.getInstance(resultCode, msg, data);
    }

    public static <T> Result<T> success(ResultCode resultCode) {
        return success(resultCode, null, null);
    }

    public static <T> Result<T> success(T data) {
        return success(ResultCode.OK, null, data);
    }

    public static <T> Result<T> success() {
        return success(ResultCode.OK);
    }

    /*请求失败*/
    /**接口请求失败时并不会返回data，为了编译器不显示黄色代码警告，加上了反射类型参数，并在接口不返回指定实体时将返回类型指定为Object*/
    public static <T> Result<T> failure(HttpStatus httpStatus, ResultCode resultCode, String msg, Class<T> t) {
        return Result.getInstance(resultCode, msg, t);
    }

    public static <T> Result<T> failure(HttpStatus httpStatus, ResultCode resultCode, Class<T> t) {
        return Result.getInstance(resultCode, null, t);
    }
}
