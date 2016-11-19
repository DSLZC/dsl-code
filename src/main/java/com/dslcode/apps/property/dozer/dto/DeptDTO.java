package com.dslcode.apps.property.dozer.dto;

import lombok.Data;
import org.dozer.Mapping;

import java.util.Date;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/3.
 */
@Data
public class DeptDTO {
    @Mapping("id")
    private Integer deptId;
    private String name;
    private Date createTime;
    @Mapping("users")
    private List<UserDTO> users;
    @Mapping("father")
    private DeptDTO father;
    @Mapping("children")
    private List<DeptDTO> children;

}
