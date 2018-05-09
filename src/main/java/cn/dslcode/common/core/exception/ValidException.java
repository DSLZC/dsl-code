package cn.dslcode.common.core.exception;

import cn.dslcode.common.web.response.ResponseCode;

/**
 * @author dongsilin
 * @version 2018/3/7.
 */
public class ValidException extends BizException {


    public ValidException(ResponseCode restResponseCode) {
        super(restResponseCode);
    }

    public ValidException(int code) {
        super(code);
    }

    public ValidException(String message) {
        super(message);
    }

    public ValidException(int code, String message) {
        super(code, message);
    }
}
