package com.dslcode.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dongsilin on 2018/1/10.
 */
public enum AjaxResponseCode {

    /* 默认成功信息 */
    SUCCESS(10000, "处理成功"),
    /* 默认失败信息 */
    FAIL(20000, "处理失败"),
    /* 默认失败信息 */
    PARAM_FAIL(400, "请求参数有误"),
    /* 默认失败信息 */
    NO_SUBMIT(30000, "订单已处理，不能重复操作"),
    UNAUTHORIZED(401, "抱歉，操作权限不足"),
    UNOPERATE(402, "操作权限不足"),
    MAX_UPLOAD_SIZE_EXCEEDED(460, "抱歉，文件大小超过限制"),

    LOGIN_SUCCESS(10000, "登录成功！"),
    LOGIN_FAIL(20000, "登录失败！"),
    REG_SUCCESS(10000, "注册成功！"),
    REG_FAIL(20000, "注册失败！"),

    /* 保存成功信息 */
    SAVE_SUCCESS(10000, "保存成功"),
    /* 保存成功信息 */
    SAVE_CAR(12000, "找车单的货物量总和不能超过合同总运量"),
    /* 保存失败信息 */
    SAVE_FAIL(20000, "保存失败"),
    /* 回复成功信息 */
    REPLY_SUCCESS(10000, "回复成功"),
    /* 回复失败信息 */
    REPLY_FAIL(20000, "回复失败"),
    /* 取消成功信息 */
    CANCEL_SUCCESS(10000, "取消成功"),
    /* 取消失败信息 */
    CANCEL_FAIL(20000, "取消失败"),

    /* 旧密码不正确 */
    OLDPASSWORD_FALSE(20000, "旧密码不正确"),
    /* 新旧密码不能相同 */
    NEWPASSWORD_REPETITION(20000, "新旧密码不能相同"),
    /* 两次密码输入不同 */
    TWOPASSWORD_FALSE(20000, "两次密码输入不同"),

    /* 删除成功信息 */
    DEL_SUCCESS(10000, "删除成功"),
    /* 删除失败信息 */
    DEL_FAIL(20000, "删除失败"),
    /* 验证码过期 */
    CODE_INVALID(20000, "验证码已失效"),
    /* 验证码不正确 */
    CODE_FAIL(20000, "验证码不正确"),
    /* 该账号被禁止登录 */
    LOGIN_FORBID(50000, "该账号被禁止登录"),
    /* 该账号被停用 */
    LOGIN_BLOCKUP(60000, "该账号被停用"),
    /* 用户或密码不存在 */
    USER_FAIL(20000, "用户或密码不正确"),
    /* 该账号不存在 */
    USER_NO(20001, "该账号不存在"),
    /* 没有设置支付密码 */
    PAY_NO(30000, "您还没有设置支付密码"),
    /* 支付密码错误 */
    PAY_FAIL(20000, "支付密码错误"),
    /* 支付密码正确 */
    PAY_SUCCESS(200, "支付密码正确"),
    /* 订单已冻结，无法支付 */
    ORDER_HIDE(20001, "合同已冻结，无法支付"),
    /* 支付成功 */
    ORDER_SUCCESS(200, "支付成功"),
    /* 支付失败 */
    ORDER_FAIL(201, "支付失败"),
    /* 手机号码已注册 */
    PHONE_REG(20000, "手机号码已注册"),
    /* 手机号码已注册 */
    PHONE_EMPTY(20000, "手机号码不存在"),
    /* 验证码发送成功 */
    PHONECODE_SUCCESS(10000, "短信动态码发送成功！有效时间为3分钟!"),
    /* 验证码发送失败 */
    PHONECODE_FAIL(20000, "短信动态码发送失败，请稍后再试!"),
    /* 手机验证码不正确 */
    PHONECODE_WRONG(20000, "短信动态码不正确"),
    /* 手机验证码正确 */
    PHONECODE_RIGHT(10000, "短信动态码正确"),
    /* 图片验证码正确 */
    PICCODE_SUCCESS(10000, "图片验证码正确"),
    /* 图片验证码错误 */
    PICCODE_FAIL(20000, "图片验证码错误"),
    /* 手机号已变更 */
    PHONE_CHANGE(20000, "手机号已变更"),
    /* 输入的手机号不正确 */
    PHONE_NOCORRECT(30000, "输入的手机号不正确"),
    /* 验证码超时 */
    PHONECODE_RUNTIME(20000, "短信动态码超时"),
    /* 已生成订单 */
    TOORDER_SUCCESS(10000, "已生成订单"),
    /* 交易尚未开市 */
    TIME_NO_START(10001, "交易尚未开市"),
    /* 交易已经闭市 */
    TIME_OUT(10001, "交易已经闭市"),
    /* 订单支付时间过期 */
    DUE_OUT(10001, "订单支付时间过期"),
    /* 当前页码不能为空*/
    PAGE_NO(10001, "当前页码不能为空"),
    /* 每页大小不能为空 */
    PAGE_SIZE(10002, "每页大小不能为空"),
    /*购买数量*/
    ORDER_NUM(10004, "购买数量不能为负数"),
    /*重复提交*/
    DO_MORE_SUBMIT(10005, "不能重复提交"),

    UPLOAD_PAY_MONEY(10006, "上传金额大于订单金额"),

    PASSWORD_NO_MODIFY(30010, "新密码不能与旧密码相同"),
    /*下架成功*/
    DOWN_SUCCESS(10002, "下架成功"),
    /*上架成功*/
    REGO_SUCCESS(10002, "上架成功"),
    /*上架失败*/
    REGO_FAIL(10003, "上架失败"),
    /*下架失败*/
    DOWN_FAIL(10003, "下架失败"),
    /*提交成功*/
    COMMIT_SUCCESS(10002, "提交成功"),
    /*提交失败*/
    COMMIT_FAIL(10003, "提交失败"),
    /*提交的已被删除*/
    COMMIT_DEL(10001, "您要提交的已经被人删除"),
    /*提交的已被修改*/
    COMMIT_MODIFY(10005, "该的地址已经被修改"),

    COMMIT_LESS_NOW(10004, "该的有效期小于当前日期，不可以提交"),
    /*撤销的被修改*/
    CANCEL_MODIFY(10001, "您要撤销的已经被人修改"),
    ;
    public int code;
    public String message;

    AjaxResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private static Set<Entry> entries;
    private static Map<String, Entry> responseCodeMap;

    /**
     * 所有AjaxResponseCode转为Set<Entry>
     * @return
     */
    public static Set<Entry> toEntries(){
        if(entries == null){
            entries = Arrays.stream(AjaxResponseCode.values()).map(Entry::new).collect(Collectors.toSet());
        }
        return entries;
    }

    /**
     * 单个AjaxResponseCode转为Entry
     * @return
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Entry{
        public String name;
        public int code;
        public String message;

        public Entry(AjaxResponseCode responseCode) {
            this.name = responseCode.name();
            this.code = responseCode.code;
            this.message = responseCode.message;
        }
    }
}
