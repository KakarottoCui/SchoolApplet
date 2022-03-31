package com.kbz1121.MySchool.controller;

import com.kbz1121.MySchool.entity.ScUser;
import com.kbz1121.MySchool.service.ArticleService;
import com.kbz1121.MySchool.system.CommonFunction;
import com.kbz1121.MySchool.system.ResponseFormat.BaseResponse;
import com.kbz1121.MySchool.system.ResponseFormat.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@BaseResponse
@RequestMapping("/article")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @GetMapping("/getTest")
    @ResponseBody
    public ResponseResult login3(){
        String a = "ccc";
        return articleService.getArticle();
    }

    @GetMapping("/getArticleByParam")
    @ResponseBody
    public ResponseResult getArticle(@RequestParam(required = true) String data){
        return articleService.getArticle(data);
    }

    @GetMapping("/getArticleByUserNo")
    @ResponseBody
    public ResponseResult getArticleByUserNo(HttpServletRequest request){
        return articleService.getArticleByUserNo(CommonFunction.getUserInfo(request));
    }

    @GetMapping("/delArticleById")
    @ResponseBody
    public ResponseResult delArticleById(@RequestParam(required = true) Integer id){
        return articleService.delArticleById(id);
    }

    @RequestMapping("/addArticleByParam")
    @ResponseBody
    public ResponseResult addArticleByParam(HttpServletRequest request, @RequestParam(required = true) String data){

        return articleService.addArticle(data, CommonFunction.getUserInfo(request));
    }

    @RequestMapping("/getArticleByUserNo")
    @ResponseBody
    public ResponseResult getArticleByUserNo(HttpServletRequest request, @RequestParam(required = true) String data){

        return articleService.addArticle(data, CommonFunction.getUserInfo(request));
    }



}
