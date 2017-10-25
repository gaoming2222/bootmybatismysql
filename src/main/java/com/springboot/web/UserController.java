package com.springboot.web;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.entity.Users;
import com.springboot.mapper.UserMapper;
import com.springboot.tools.JsonUtility;

@Controller
@EnableAutoConfiguration
//MyBatis 支持
@MapperScan("com.springboot.mapper")
@SpringBootApplication
public class UserController{

	@Autowired
    private UserMapper userMapper;
	
	@RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

	@RequestMapping("/getAllUsers")
    @ResponseBody
    String getAllUsers() {
		List<Users> users = userMapper.getAllUsers();
		return JsonUtility.convertBean2Json(users);
    }
}
