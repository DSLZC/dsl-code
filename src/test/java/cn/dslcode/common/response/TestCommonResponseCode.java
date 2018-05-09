package cn.dslcode.common.response;

import cn.dslcode.common.web.response.CommonResponseCode;
import org.junit.Test;

import java.util.Set;

/**
 * Created by dongsilin on 2018/1/10.
 */
public class TestCommonResponseCode {

    @Test
    public void test1(){
        Set<CommonResponseCode.Entry> entries = CommonResponseCode.toEntries();
        entries.stream().forEach(System.out::println);
    }

    @Test
    public void test2(){
        System.out.println(CommonResponseCode.FAIL.toEntry());
        System.out.println(CommonResponseCode.toEntries());
    }

}
