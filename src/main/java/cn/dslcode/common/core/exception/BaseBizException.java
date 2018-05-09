package cn.dslcode.common.core.exception;

import cn.dslcode.common.web.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dongsilin on 2018/1/29.
 * base异常信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseBizException extends RuntimeException {

    private int code;

    public BaseBizException(String message) {
        super(message);
    }

    public BaseBizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseBizException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

}
