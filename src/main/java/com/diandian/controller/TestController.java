package com.diandian.controller;

import com.diandian.dao.custom.ListsCustomMapper;
import com.diandian.model.custom.UserCustom;
import com.diandian.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    RoomService roomService;

}
