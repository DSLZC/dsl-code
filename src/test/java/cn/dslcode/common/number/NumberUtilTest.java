package cn.dslcode.common.number;

import cn.dslcode.common.core.number.NumberUtil;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author dongsilin
 * @version 2018/4/4.
 */
public class NumberUtilTest {

    @Test
    public void ytest(){
        BigDecimal payMoney = new BigDecimal(0.01);
        System.out.println(NumberUtil.format(payMoney, NumberUtil.DEFAULT));

        payMoney = new BigDecimal(75373);
        System.out.println(NumberUtil.format(payMoney, NumberUtil.DEFAULT));

        payMoney = new BigDecimal(12.357);
        System.out.println(NumberUtil.format(payMoney, NumberUtil.DEFAULT));

        payMoney = new BigDecimal(0.124);
        System.out.println(NumberUtil.format(payMoney, NumberUtil.DEFAULT));

        payMoney = new BigDecimal(1);
        System.out.println(NumberUtil.format(payMoney, NumberUtil.DEFAULT));
    }
}
