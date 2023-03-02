package com.chenzhenquan.demo.service;

import com.chenzhenquan.demo.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers(Integer size, Integer pageNum, String name);
    int userCount(String name);
    void updateUser(User user);
    void createUser(User user);
    void deleteUser(List<Integer> ids);
}
