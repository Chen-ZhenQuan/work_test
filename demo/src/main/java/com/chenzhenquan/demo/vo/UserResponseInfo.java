package com.chenzhenquan.demo.vo;


import lombok.Data;

@Data
public class UserResponseInfo {
    private int status;
    private Object data;
    private int size;
    private int totalPage;
    private int total;
    private int currentPage;
}
