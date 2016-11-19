package com.dslcode.core.property.dozer.dto;

import lombok.Data;
import org.dozer.Mapping;

import java.util.Date;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/3.
 */
@Data
public class TaskDTO {
    @Mapping("id")
    private Integer taskId;
    private String name;
    private Date createTime;
    @Mapping("users")
    private List<UserDTO> users;
    @Mapping("father")
    private TaskDTO father;
    @Mapping("children")
    private List<TaskDTO> children;

}
