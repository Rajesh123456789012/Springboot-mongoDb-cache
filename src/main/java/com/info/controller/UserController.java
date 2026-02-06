package com.info.controller;

import com.info.entity.User;
import com.info.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public User update(@PathVariable String id,
                       @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    @GetMapping("/byName/{name}")
    public List<User> getByName(@PathVariable String name){
        return userService.findByName(name);
    }
}

