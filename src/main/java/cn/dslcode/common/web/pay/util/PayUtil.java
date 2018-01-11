package cn.dslcode.common.web.pay.util;

import cn.dslcode.common.core.date.DateUtil;
import cn.dslcode.common.core.random.RandomCode;
import cn.dslcode.common.core.string.StringUtil;
import cn.dslcode.common.web.pay.config.PayEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dongsilin on 2016/11/23.
 * 支付工具类
 */
public class PayUtil {
    public static final Logger log = LoggerFactory.getLogger(PayUtil.class);

    /**
     * 获取系统生成的订单号，16位。1位业务类型 + 1位支付类型 + 11位时间yMMddHHmmss + 3位随机数
     * @param serviceType 业务类型
     * @param payType 支付类型
     * @return
     */
    public static String getOrderSn(PayEnum.ServiceType serviceType, PayEnum.PayType payType){
        String nowStr = DateUtil.nowStr(DateUtil.yyyyMMddHHmmss0);
        return StringUtil.append2String(
                serviceType.value,
                payType.value,
                nowStr.substring(3),
                RandomCode.random.nextInt(900) + 100
        );
    }


}