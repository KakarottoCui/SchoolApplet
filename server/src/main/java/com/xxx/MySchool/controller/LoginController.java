package com.kbz1121.MySchool.controller;

import com.kbz1121.MySchool.service.ArticleService;
import com.kbz1121.MySchool.service.WXCheckService;
import com.kbz1121.MySchool.system.ResponseFormat.BaseResponse;
import com.kbz1121.MySchool.system.ResponseFormat.ResponseResult;
import com.kbz1121.MySchool.system.restful.SysRestful;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@BaseResponse
@RequestMapping("/user")
public class LoginController {

    @Resource
    WXCheckService wxCheckService;

    @GetMapping("/getUserInfo")
    @ResponseBody
    public ResponseResult getUserInfo(String code,String iv,String enData,String userInfo){
        return wxCheckService.getUserInfo(code,enData,iv,userInfo);
    }

    @RequestMapping("/checkStudent")
    @ResponseBody
    public ResponseResult checkStudent(String code,String data){
        return wxCheckService.checkStudent(code,data);
    }

}
