package com.chenzhenquan.demo.mapper;

import com.chenzhenquan.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> queryUserData(Integer startIndex, Integer size, String name);
    Integer userCount(String name);
    void updateUser(User user);
    void createUser(User user);
    void deleteUsers(List<Integer> list);
}
