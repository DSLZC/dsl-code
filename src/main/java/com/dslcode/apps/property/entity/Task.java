package com.dslcode.apps.property.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/3.
 */
@Data
public class Task {
    private Integer id;
    private String name;
    private Date createTime;
    private List<User> users;
    private Task father;
    private List<Task> children;
    public Task() {
    }
    public Task(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
