package com.chenzhenquan.demo.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private Integer id;
    private String name;
    private int age;
    private int gender;
    private long phone;
    private String address;
    private String detailedAddress;
}
