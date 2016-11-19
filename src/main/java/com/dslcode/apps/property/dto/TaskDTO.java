package com.dslcode.apps.property.dto;

import com.dslcode.apps.property.entity.Task;
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
@PropertySource(Task.class)
public class TaskDTO {
    @PropertyMapping(value = "id")
    private Integer taskId;
    private String name;
    @JsonFormat(pattern= DateUtil.yyyyMMdd,timezone=DateUtil.TIMEZONE)
    private Date createTime;
    @PropertyMapping(value = "users", thisClass = UserDTO.class)
    private List<UserDTO> users;
    @PropertyMapping(value = "father", thisClass = TaskDTO.class)
    private TaskDTO father;
    @PropertyMapping(value = "children", thisClass = TaskDTO.class)
    private List<TaskDTO> children;

}
