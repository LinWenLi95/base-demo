package com.lwl.base.common.vo;

import lombok.Getter;

/**
 * 操作结果码
 * @author linwenli
 */
@Getter
public enum ResultCode {
    /**请求成功*/
    OK(200,"请求成功"),

    /* 客户端错误 */
    /**坏请求（如，参数错误）*/
    BAD_REQUEST(400,"坏请求"),
    /**未授权*/
    UNAUTHORIZED(401,"未授权"),
    /**被禁止访问*/
    FORBIDDEN(403,"被禁止访问"),
    /**请求的资源不存在*/
    NOT_FOUND(404,"请求的资源不存在"),
    /**请求的方法不允许使用*/
    METHOD_NOT_ALLOWED(405,"请求的方法不允许使用"),
    /**不支持的媒体类型*/
    UNSUPPORTED_MEDIA_TYPE(415,"不支持的媒体类型"),
    /**请求过多*/
    TOO_MANY_REQUESTS(429,"请求过多"),

    /* 服务端错误 */
    /**服务器内部错误*/
    INTERNAL_SERVER_ERROR(500,"服务器内部错误"),
    /**暂停服务*/
    SERVICE_UNAVAILABLE(503,"暂停服务"),

    /* 自定义状态码(HttpStatus用来表示操作是否正确，ResultCode用来说明操作具体结果，如果和HttpStatus相同，ResultCode就完全没用的必要了) */
    /**请求未报错,但得到预期外的结果*/
    UNEXPECTED_RESULTS(10001,"请求未报错,但得到预期外的结果");

    /**状态码*/
    private Integer code;
    /**原因*/
    private String reasonPhrase;

    ResultCode(Integer code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

}
