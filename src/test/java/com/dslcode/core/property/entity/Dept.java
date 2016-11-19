package com.dslcode.core.property.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/3.
 */
@Data
public class Dept {
    private Integer id;
    private String name;
    private Date createTime;
    private List<User> users;
    private Dept father;
    private List<Dept> children;

    public Dept() {
    }
    public Dept(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
