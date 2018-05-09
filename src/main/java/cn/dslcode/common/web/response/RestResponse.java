package cn.dslcode.common.web.response;

import cn.dslcode.common.core.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author dongsilin
 * @version 2018/3/21.
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * CommonResponseCode 的 code
     */
    private int code;
    /**
     * 操作说明信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    public static RestResponse build(BizException bizException) {
        return new RestResponse(bizException.getCode(), bizException.getMessage(), null);
    }

    public static RestResponse build(int code) {
        return new RestResponse(code, null, null);
    }

    public static RestResponse build(int code, String message) {
        return new RestResponse(code, message, null);
    }

    public static <T> RestResponse build(int code, T data) {
        return new RestResponse(code, null, data);
    }

    public static RestResponse build(CommonResponseCode responseCode) {
        return new RestResponse(responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static <T> RestResponse build(CommonResponseCode responseCode, T data) {
        return new RestResponse(responseCode.getCode(), responseCode.getMessage(), data);
    }

    /**
     * 请求处理成功
     *
     * @return
     */
    public static RestResponse buildSuccess() {
        return new RestResponse(CommonResponseCode.SUCCESS.code, null, null);
    }

    /**
     * 请求处理成功
     *
     * @param message
     * @return
     */
    public static RestResponse buildSuccess(String message) {
        return new RestResponse(CommonResponseCode.SUCCESS.code, message, null);
    }

    /**
     * 请求处理成功
     *
     * @param message
     * @param data
     * @return
     */
    public static <T> RestResponse buildSuccess(String message, T data) {
        return new RestResponse(CommonResponseCode.SUCCESS.code, message, data);
    }

    /**
     * 请求处理成功
     *
     * @param data
     * @return
     */
    public static <T> RestResponse buildSuccessData(T data) {
        return new RestResponse(CommonResponseCode.SUCCESS.code, null, data);
    }

    /**
     * 请求处理失败
     *
     * @return
     */
    public static RestResponse buildFail() {
        return new RestResponse(CommonResponseCode.FAIL.code, null, null);
    }

    /**
     * 请求处理失败
     *
     * @param message
     * @return
     */
    public static RestResponse buildFail(String message) {
        return new RestResponse(CommonResponseCode.FAIL.code, message, null);
    }

    /**
     * 请求处理失败
     *
     * @param message
     * @param data
     * @return
     */
    public static <T> RestResponse buildFail(String message, T data) {
        return new RestResponse(CommonResponseCode.FAIL.code, message, data);
    }

    /**
     * 请求处理失败
     *
     * @param data
     * @return
     */
    public static <T> RestResponse buildFailData(T data) {
        return new RestResponse(CommonResponseCode.FAIL.code, null, data);
    }

}
