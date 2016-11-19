package com.dslcode.core;

import com.dslcode.DslCodeApplicationTests;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by dongsilin on 2016/11/18.
 */
public class UUIDTest  extends DslCodeApplicationTests {

    @Test
    public void test(){
        log.debug(System.currentTimeMillis()+"");
        log.debug(UUID.randomUUID().toString().replaceAll("-", ""));
        log.debug(UUID.randomUUID().clockSequence()+"");
        log.debug(UUID.randomUUID().getLeastSignificantBits()+"");
    }
}
