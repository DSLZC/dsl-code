package com.dslcode.core.property;

import com.dslcode.DslCodeApplicationTests;
import com.dslcode.core.collection.ListUtil;
import com.dslcode.core.date.DateUtil;
import com.dslcode.core.property.dto.UserDTO;
import com.dslcode.core.property.entity.Dept;
import com.dslcode.core.property.entity.Task;
import com.dslcode.core.property.entity.User;
import org.springframework.beans.BeanUtils;

/**
 * Created by dongsilin on 2016/11/3.
 */
public class PropertyTest extends DslCodeApplicationTests{

    //@Test
    public void copyProperty(){
        User dsl = new User(10, "dsl", 22);dsl.setLoginTime(DateUtil.parseToDate("2016-06-06 14:25:36", DateUtil.yyyyMMddHHmmss));
        User zs = new User(15, "zhangsan", 72);zs.setLoginTime(DateUtil.parseToDate("2016-07-07 17:27:37", DateUtil.yyyyMMddHHmmss));
        User ls = new User(42, "lisi", 15);ls.setLoginTime(DateUtil.parseToDate("2016-09-09 19:29:39", DateUtil.yyyyMMddHHmmss));

        Dept dev = new Dept(1, "开发");dev.setCreateTime(DateUtil.parseToDate("2016-09-09 19:29:39", DateUtil.yyyyMMddHHmmss));
        Dept test = new Dept(1, "测试");test.setCreateTime(DateUtil.parseToDate("2016-06-06 14:25:36", DateUtil.yyyyMMddHHmmss));

        Task makeMoney = new Task(1, "挣钱");makeMoney.setCreateTime(DateUtil.parseToDate("2015-01-01 09:00:00", DateUtil.yyyyMMddHHmmss));
        Task marry = new Task(1, "结婚");marry.setCreateTime(DateUtil.parseToDate("2016-09-28 12:00:00", DateUtil.yyyyMMddHHmmss));

        dsl.setDept(dev);
        dsl.setTasks(ListUtil.getList(makeMoney, marry));


        dev.setChildren(ListUtil.getList(test));

        test.setUsers(ListUtil.getList(zs, ls));

        makeMoney.setUsers(ListUtil.getList(zs, ls));
        makeMoney.setChildren(ListUtil.getList(marry));

        marry.setUsers(ListUtil.getList(zs, ls));

        log.debug("++++++++++***+++dsl={}", dsl);

        long c1 = System.currentTimeMillis();
        UserDTO dslDTO = new UserDTO();
        PropertyUtil.copyProperties(dsl, dslDTO, true);
        long c2 = System.currentTimeMillis();
        log.debug("+******************************+++dslDTO={}", dslDTO);
        log.debug("c2 - c1 ={}", c2-c1);

        long c5 = System.currentTimeMillis();
        UserDTO dslDTO2 = new UserDTO();
        BeanUtils.copyProperties(dsl, dslDTO2);
        long c6 = System.currentTimeMillis();
        log.debug("+******************************+++dslDTO={}", dslDTO2);
        log.debug("c6 - c5 ={}", c6-c5);

        long c3 = System.currentTimeMillis();
        com.dslcode.core.property.dozer.dto.UserDTO dozer = PropertyUtil.dozerMapper(dsl, com.dslcode.core.property.dozer.dto.UserDTO.class);
        long c4 = System.currentTimeMillis();

        log.debug("***********+++dslDTO={}", dozer);
        log.debug("c4 - c3 ={}", c4-c3);

    }

}
