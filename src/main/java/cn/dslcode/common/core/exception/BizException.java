package cn.dslcode.common.core.exception;

import cn.dslcode.common.web.response.ResponseCode;

/**
 * Created by dongsilin on 2018/3/6.
 */
public class BizException extends BaseBizException {

    public BizException(int code) {
        super(code);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(int code, String message) {
        super(code, message);
    }

    public BizException(ResponseCode responseCode) {
        super(responseCode);
    }

}
