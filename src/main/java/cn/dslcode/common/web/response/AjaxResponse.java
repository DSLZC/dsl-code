package cn.dslcode.common.web.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * AJAX请求的返回对象
 * @author 董思林
 * 2016-07-13
 */
@Data
@JsonAutoDetect
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResponse<T> implements Serializable {

    public static final long serialVersionUID = 1L;

	/** AjaxResponseCode 的 code */
	private int code;
    /** 操作说明信息 */
    private String message;
    /** 响应数据 */
    private T body;

    public static<T> AjaxResponse build(int code) {
        return new AjaxResponse(code, null, null);
    }

    public static<T> AjaxResponse build(int code, String message) {
        return new AjaxResponse(code, message, null);
    }

    public static<T> AjaxResponse build(int code, T body) {
        return new AjaxResponse(code, null, body);
    }

    public static<T> AjaxResponse build(AjaxResponseCode responseCode) {
        return new AjaxResponse(responseCode.code, responseCode.message, null);
    }

    public static<T> AjaxResponse build(AjaxResponseCode responseCode, T body) {
        return new AjaxResponse(responseCode.code, responseCode.message, body);
    }
    
    /**
     * 请求处理成功
     * @return
     */
    public static<T> AjaxResponse buildSuccess() {
        return new AjaxResponse(AjaxResponseCode.SUCCESS.code, null, null);
    }
    /**
     * 请求处理成功
     * @param message
     * @return
     */
    public static<T> AjaxResponse buildSuccess(String message) {
        return new AjaxResponse(AjaxResponseCode.SUCCESS.code, message, null);
    }
    /**
     * 请求处理成功
     * @param message
     * @param body
     * @return
     */
    public static<T> AjaxResponse buildSuccess(String message, T body) {
        return new AjaxResponse(AjaxResponseCode.SUCCESS.code, message, body);
    }
    /**
     * 请求处理成功
     * @param body
     * @return
     */
    public static<T> AjaxResponse buildSuccessbody(T body) {
        return new AjaxResponse(AjaxResponseCode.SUCCESS.code, null, body);
    }

    /**
     * 请求处理失败
     * @return
     */
    public static<T> AjaxResponse buildFailed() {
        return new AjaxResponse(AjaxResponseCode.FAIL.code, null, null);
    }
    /**
     * 请求处理失败
     * @param message
     * @return
     */
    public static<T> AjaxResponse buildFailed(String message) {
        return new AjaxResponse(AjaxResponseCode.FAIL.code, message, null);
    }
    /**
     * 请求处理失败
     * @param message
     * @param body
     * @return
     */
    public static<T> AjaxResponse buildFailed(String message, T body) {
        return new AjaxResponse(AjaxResponseCode.FAIL.code, message, body);
    }
    /**
     * 请求处理失败
     * @param body
     * @return
     */
    public static<T> AjaxResponse buildFailedbody(T body) {
        return new AjaxResponse(AjaxResponseCode.FAIL.code, null, body);
    }

}
