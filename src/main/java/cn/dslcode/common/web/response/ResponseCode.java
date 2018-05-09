package cn.dslcode.common.web.response;

/**
 * @author dongsilin
 * @version 2018/3/21.
 * ResponseCode顶级接口
 */
public interface ResponseCode {
    int getCode();
    String getMessage();
    String getName();
}
