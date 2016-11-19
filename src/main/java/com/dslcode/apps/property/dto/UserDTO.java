package com.dslcode.apps.property.dto;

import com.dslcode.apps.property.entity.User;
import com.dslcode.core.date.DateUtil;
import com.dslcode.core.property.PropertyMapping;
import com.dslcode.core.property.PropertySource;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/3.
 */
@Data
@PropertySource(User.class)
public class UserDTO {
    @PropertyMapping(value="id")
    private Integer userId;
    private String name;
    @JsonFormat(pattern= DateUtil.yyyyMMddHHmmss,timezone=DateUtil.TIMEZONE)
    private Date loginTime;
    private Integer age;
    @PropertyMapping(value="dept", thisClass=DeptDTO.class)
    private DeptDTO dept;
    @PropertyMapping(value="tasks", thisClass=TaskDTO.class)
    private List<TaskDTO> tasks;

}
