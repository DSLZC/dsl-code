package com.dslcode.core;

import com.dslcode.DslCodeApplicationTests;
import com.dslcode.core.date.DateUtil;
import com.dslcode.core.random.RandomCode;
import com.dslcode.core.util.PwdUtil;
import org.joda.time.DateTime;

/**
 * Created by dongsilin on 2016/11/3.
 */
public class RandomCodeTest extends DslCodeApplicationTests{
    //@Test
    public void createCode(){
        log.info("+++++++++++++++++++++++++++++++++++++++++******************************************************************+++={}",RandomCode.getNumCode(200));
        log.info("+++++++++++++++++++++++++++++++++++++++++******************************************************************+++={}", PwdUtil.MD5Encode("123456"));
        log.info("**************************+++={}", DateUtil.parseToDate("2016-03-28 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime()/1000);
        log.info("**************************+++={}", DateUtil.parseToDate("2016-03-28 23:59:59", "yyyy-MM-dd HH:mm:ss").getTime()/1000);

        DateTime yesterdayMorning = DateTime.now().plusDays(-1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        DateTime yesterdayNight = DateTime.now().plusDays(-1).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999);
        String timeStr = yesterdayMorning.toString("yyyy-MM-dd");
        log.info("**************************+++={}",yesterdayMorning);
        log.info("**************************+++={}",yesterdayNight);
        log.info("**************************+++={}",yesterdayMorning.toString("yyyy-MM-dd"));
    }

    //@Test
    public void dateTime(){
        DateTime dateTime = DateTime.parse("2016-11-08");
        while(dateTime.isBeforeNow()){
            dateTime = dateTime.plusDays(1);
            log.info("begin={}", dateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).getMillis()/1000);
            log.info("end={}", dateTime.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).getMillis()/1000);
            log.info("str={}", dateTime.toString("yyyy-MM-dd"));
        }
    }

}
