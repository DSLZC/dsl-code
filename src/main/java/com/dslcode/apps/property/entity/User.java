package com.dslcode.apps.property.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/3.
 */
@Data
public class User {
    private Integer id;
    private String name;
    private Date loginTime;
    private Integer age;
    private Dept dept;
    private List<Task> tasks;

    public User() {
    }
    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
