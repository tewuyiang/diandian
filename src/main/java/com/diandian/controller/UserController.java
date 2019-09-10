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

    /**
     * 根据主键id更新用户信息
     * @param user
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/updateUserById")
    public String updateUserById(User user) throws Exception {
        if(userService.updateUserById(user) > 0){
            return "success";
        }
        return "failure";
    }


    /**
     * 根据主键id查询用户信息
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/selectUserById/{id}")
    public User selectUserById(@PathVariable("id") Integer id) throws Exception {
        return userService.selectUserById(id);
    }

}
