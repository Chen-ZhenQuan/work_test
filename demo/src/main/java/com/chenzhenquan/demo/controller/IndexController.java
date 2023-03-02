package com.chenzhenquan.demo.controller;

import com.chenzhenquan.demo.entity.User;
import com.chenzhenquan.demo.service.IUserService;
import com.chenzhenquan.demo.vo.UserResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("user")
public class IndexController {
    private final IUserService userService;

    @Autowired
    public IndexController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserResponseInfo getUsers(
            @RequestParam("size") Integer size,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("name") String name
    ) {
        UserResponseInfo info = new UserResponseInfo();
        info.setData(userService.getUsers(size, pageNum, name));
        info.setTotal(userService.userCount(name));
        info.setCurrentPage(pageNum);
        info.setSize(size);
        return info;
    }

    @PostMapping
    public UserResponseInfo create(@RequestBody User user) {
        userService.createUser(user);
        return new UserResponseInfo();
    }

    @PutMapping
    public UserResponseInfo updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new UserResponseInfo();
    }

    @DeleteMapping
    public UserResponseInfo deleteUsers(@RequestParam("ids") String ids) {
        List<Integer> list =  Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        userService.deleteUser(list);
        return new UserResponseInfo();
    }
}
