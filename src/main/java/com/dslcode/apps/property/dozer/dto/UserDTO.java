package com.dslcode.apps.property.dozer.dto;

import lombok.Data;
import org.dozer.Mapping;

import java.util.Date;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/3.
 */
@Data
public class UserDTO {
    @Mapping("id")
    private Integer userId;
    private String name;
    private Date loginTime;
    private Integer age;
    @Mapping("dept")
    private DeptDTO dept;
    @Mapping("tasks")
    private List<TaskDTO> tasks;

}
