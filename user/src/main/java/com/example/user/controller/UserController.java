package com.example.user.controller;

import com.example.user.dto.CreateUserDTO;
import com.example.user.dto.UpdateUserDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {



    @GetMapping
    public String getUsers(){
        return "get users";
    }

    @PostMapping
    public String createUser(CreateUserDTO createUserDTO) {
        return "create user";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id){
        return "Get User By Id " + id;
    }

    @PutMapping("/{id}")
    public String updateUserById(UpdateUserDTO updateUserDTO) {
        return "update user";
    }

    @PatchMapping("/{id}")
    public String updatePartialUser(UpdateUserDTO  updateUserDTO) {
        return "update partial user";
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable Long id) {
        return "delete user";
    }

}
