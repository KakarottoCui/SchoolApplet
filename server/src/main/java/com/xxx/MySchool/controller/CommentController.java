package com.kbz1121.MySchool.controller;

import com.kbz1121.MySchool.service.ArticleService;
import com.kbz1121.MySchool.service.CommentService;
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
@RequestMapping("/comment")
public class CommentController {

    @Resource
    CommentService commentService;

    @GetMapping("/getComment")
    @ResponseBody
    public ResponseResult getComment(@RequestParam(required = true) Integer id){
        return commentService.getComment(id);
    }

    @GetMapping("/getCommentByUserAndType")
    @ResponseBody
    public ResponseResult getCommentByUserAndType(HttpServletRequest request,@RequestParam(required = true) int type){
        return commentService.getCommentByUserAndType(CommonFunction.getUserInfo(request),type);
    }

    @GetMapping("/queryByAritcleParam")
    @ResponseBody
    public ResponseResult queryByAritcleParam(HttpServletRequest request){
        return commentService.queryByAritcleParam(CommonFunction.getUserInfo(request));
    }

    @GetMapping("/delComment")
    @ResponseBody
    public ResponseResult delArticleById(@RequestParam(required = true) Integer id){
        return commentService.delComment(id);
    }

    @RequestMapping("/addComment")
    @ResponseBody
    public ResponseResult addComment(HttpServletRequest request, @RequestParam(required = true) String data){

        return commentService.addComment(data, CommonFunction.getUserInfo(request));
    }


}
