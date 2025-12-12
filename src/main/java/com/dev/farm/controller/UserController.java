package com.dev.farm.controller;


import com.dev.farm.entity.User;
import com.dev.farm.service.UserRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRedis repository;

    @PostMapping
    public String save(@RequestBody User user) {
        repository.save(user);
        return "User saved!";
    }

    @GetMapping("/{id}")
    public User get(@PathVariable String id) {
        return repository.findById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        repository.delete(id);
        return "User deleted!";
    }
}
