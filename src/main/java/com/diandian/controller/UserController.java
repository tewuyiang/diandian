package com.diandian.controller;

import com.diandian.model.User;
import com.diandian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/update")
    public String update(User user) throws Exception {
        if(userService.updateByPrimaryKey(user) > 0){
            return "success";
        }
        return "failure";
    }


    @ResponseBody
    @GetMapping("/select/{id}")
    public User selectUserById(@PathVariable("id") Integer id){
        return userService.selectUserById(id);
    }

}
