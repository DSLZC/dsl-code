package com.dslcode.apps.controller.core;

import com.dslcode.apps.property.dto.UserDTO;
import com.dslcode.apps.property.entity.Dept;
import com.dslcode.apps.property.entity.Task;
import com.dslcode.apps.property.entity.User;
import com.dslcode.core.collection.ListUtil;
import com.dslcode.core.date.DateUtil;
import com.dslcode.core.property.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by dongsilin on 2016/11/3.
 */
@RestController
@RequestMapping("/property")
public class PropertyController{
    public static final Logger log = LoggerFactory.getLogger(PropertyController.class);

    @PostMapping("/copy")
    public Object copyProperty(){
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

        long c1 = System.currentTimeMillis();
        UserDTO dslDTO = new UserDTO();
        PropertyUtil.copyProperties(dsl, dslDTO, true);
        long c2 = System.currentTimeMillis();
        log.debug("+******************************+++dslDTO={}", dslDTO);
        log.debug("c2 - c1 ={}", c2-c1);

//        long c5 = System.currentTimeMillis();
//        UserDTO dslDTO2 = new UserDTO();
//        BeanUtils.copyProperties(dsl, dslDTO2);
//        long c6 = System.currentTimeMillis();
//        log.debug("+******************************+++dslDTO={}", dslDTO2);
//        log.debug("c6 - c5 ={}", c6-c5);

        long c3 = System.currentTimeMillis();
        com.dslcode.apps.property.dozer.dto.UserDTO dozer = PropertyUtil.dozerMapper(dsl, com.dslcode.apps.property.dozer.dto.UserDTO.class);
        long c4 = System.currentTimeMillis();

        log.debug("***********+++dslDTO={}", dozer);
        log.debug("c4 - c3 ={}", c4-c3);
        return dozer;
    }

}
