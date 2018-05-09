package cn.dslcode.common.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dongsilin
 * @version 2018/3/21.
 */
public enum CommonResponseCode implements ResponseCode {

    /** 默认成功信息 */
    SUCCESS(1000, "操作成功"),
    /** 默认失败信息 */
    FAIL(2000, "操作失败"),
    REPEAT_SUBMIT(2001, "请勿重复提交"),
    /** 默认失败信息 */
    PARAM_FAIL(400, "请求参数有误"),
    /** 默认失败信息 */
    UNAUTHENTICATION(401, "未登录或登录失效"),
    UNAUTHORIZATION(403, "操作权限不足"),

    ;
    public int code;
    public String message;

    CommonResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getName() {
        return name();
    }

    /**
     * 静态内部类Entry
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(of = "name")
    public static class Entry{
        public String name;
        public int code;
        public String message;

        public Entry(CommonResponseCode responseCode) {
            this.name = responseCode.name();
            this.code = responseCode.code;
            this.message = responseCode.message;
        }
    }
    private static Set<Entry> entries;
    private static Map<String, Entry> responseCodeMap;
    
    /**
     * 所有CommonResponseCode转为Set<Entry>
     * @return Set<Entry>
     */
    public static Set<Entry> toEntries(){
        if(entries == null){
            entries = Arrays.stream(CommonResponseCode.values()).map(Entry::new).collect(Collectors.toSet());
        }
        return entries;
    }

    /**
     * 单个CommonResponseCode转为Entry
     * @return Entry
     */
    public Entry toEntry(){
        if (responseCodeMap == null) responseCodeMap = new HashMap<>(50);
        Entry entry = responseCodeMap.get(this.name());
        if (entry == null){
            entry = new Entry(this);
            responseCodeMap.put(this.name(), entry);
        }
        return entry;
    }
    
}
