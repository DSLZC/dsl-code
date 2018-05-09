package cn.dslcode.common.web;

import cn.dslcode.common.core.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author dongsilin
 * @version 2018/4/6.
 */
@Slf4j
public class WebUtil {


    /**
     * 判断是否为Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return (null != header && "XMLHttpRequest".equals(header));
    }

    /**
     * Response输出
     *
     * @param response
     * @param o
     * @param type
     */
    public static void output(HttpServletResponse response, Object o, ResponseOutputType type) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(type == ResponseOutputType.JSON ? "application/json; charset=utf-8" : "text/html; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            if (o instanceof String || o instanceof Number) {
                writer.write(String.valueOf(o));
            } else {
                writer.write(JsonUtil.toJson(o));
            }
            writer.flush();
        } catch (IOException e) {
            log.error("", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Response输出对象类型
     */
    public enum ResponseOutputType {
        JSON,
        STRING
    }

}
