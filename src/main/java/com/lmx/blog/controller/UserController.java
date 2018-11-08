package com.lmx.blog.controller;

import com.lmx.blog.common.Response;
import com.lmx.blog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UsersService usersService;

    @RequestMapping("/get_all")
    public Response getAll(){
        return Response.ok(usersService.getAllUsers());
    }
}
