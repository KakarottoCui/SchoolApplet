package com.kbz1121.MySchool.controller;

import com.kbz1121.MySchool.service.ArticleService;
import com.kbz1121.MySchool.service.DeptService;
import com.kbz1121.MySchool.service.ScDeptService;
import com.kbz1121.MySchool.system.CommonFunction;
import com.kbz1121.MySchool.system.ResponseFormat.BaseResponse;
import com.kbz1121.MySchool.system.ResponseFormat.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@BaseResponse
@RequestMapping("/dept")
public class DeptController {

    @Resource
    DeptService deptService;

    @GetMapping("/getDeptData")
    @ResponseBody
    public ResponseResult getDeptData(@RequestParam(required = false) Integer level){
        return deptService.getDeptByType(level);
    }



}
