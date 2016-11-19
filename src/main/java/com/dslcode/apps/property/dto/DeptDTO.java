package com.dslcode.apps.property.dto;

import com.dslcode.apps.property.entity.Dept;
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
@PropertySource(Dept.class)
public class DeptDTO {
    @PropertyMapping(value="id")
    private Integer deptId;
    private String name;
    @JsonFormat(pattern= DateUtil.yyyyMMddHHmmss,timezone=DateUtil.TIMEZONE)
    private Date createTime;
    @PropertyMapping(value = "users", thisClass = UserDTO.class)
    private List<UserDTO> users;
    @PropertyMapping(value = "father", thisClass = DeptDTO.class)
    private DeptDTO father;
    @PropertyMapping(value = "children", thisClass = DeptDTO.class)
    private List<DeptDTO> children;

}
