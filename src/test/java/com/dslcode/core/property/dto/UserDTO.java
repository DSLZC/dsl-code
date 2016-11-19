package com.dslcode.core.property.dto;

import com.dslcode.core.property.PropertyMapping;
import com.dslcode.core.property.PropertySource;
import com.dslcode.core.property.entity.User;
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
    private Date loginTime;
    private Integer age;
    @PropertyMapping(value="dept", thisClass=DeptDTO.class)
    private DeptDTO dept;
    @PropertyMapping(value="tasks", thisClass=TaskDTO.class)
    private List<TaskDTO> tasks;

}
