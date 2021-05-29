package com.imooc.activitiweb.controller;

import com.imooc.activitiweb.mapper.ActivitiMapper;
import com.imooc.activitiweb.service.TestMiaoService;
import com.imooc.activitiweb.util.AjaxResponse;
import com.imooc.activitiweb.util.GlobalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestMsController {

    @Autowired
    TestMiaoService testMiaoService;

    //获取用户
    @GetMapping(value = "/testms")
    public void getUsers() {

            testMiaoService.loadTestMiao(1L);

    }

}
