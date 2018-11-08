package com.lmx.blog.service;

import com.lmx.blog.mapper.UsersMapper;
import com.lmx.blog.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersMapper usersMapper;

    public List<Users> getAllUsers(){
        return usersMapper.selectAll();
    }
}
