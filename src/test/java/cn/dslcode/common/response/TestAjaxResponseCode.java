package cn.dslcode.common.response;

import cn.dslcode.common.web.response.AjaxResponseCode;
import org.junit.Test;

import java.util.Set;

/**
 * Created by dongsilin on 2018/1/10.
 */
public class TestAjaxResponseCode {

    @Test
    public void test1(){
        Set<AjaxResponseCode.Entry> entries = AjaxResponseCode.toEntries();
        entries.stream().forEach(System.out::println);
    }

    @Test
    public void test2(){
        System.out.println(AjaxResponseCode.COMMIT_SUCCESS.toEntry());
        System.out.println(AjaxResponseCode.toEntries());
    }

}
