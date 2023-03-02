package com.chenzhenquan.demo.service.impl;

import com.chenzhenquan.demo.entity.User;
import com.chenzhenquan.demo.mapper.UserMapper;
import com.chenzhenquan.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getUsers(Integer size, Integer pageNum, String name) {
        return userMapper.queryUserData((pageNum - 1) * size, size, name);
    }

    @Override
    public int userCount(String name) {
        return userMapper.userCount(name);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void createUser(User user) {
        userMapper.createUser(user);
    }

    @Override
    public void deleteUser(List<Integer> ids) {
        userMapper.deleteUsers(ids);
    }
}
