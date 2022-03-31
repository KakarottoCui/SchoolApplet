package com.kbz1121.MySchool.service;

import com.alibaba.fastjson.JSON;
import com.kbz1121.MySchool.dao.ScCommentDao;
import com.kbz1121.MySchool.dao.ScDeptDao;
import com.kbz1121.MySchool.entity.ScArticle;
import com.kbz1121.MySchool.entity.ScComment;
import com.kbz1121.MySchool.entity.ScDept;
import com.kbz1121.MySchool.entity.ScUser;
import com.kbz1121.MySchool.system.ResponseFormat.ResponseCode;
import com.kbz1121.MySchool.system.ResponseFormat.ResponseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Resource
    ScCommentDao scCommentDao;

    public ResponseResult getComment(Integer id){
        ScComment comment = new ScComment();
        comment.setValidFlag(1);
        comment.setArtFk(id);
        comment.setValidFlag(1);
        comment.setType(0);
        List<ScComment> getAll = scCommentDao.queryByParam(comment);
        if(getAll.size()>0){
            for(ScComment scComment:getAll){
                comment.setComFk(String.valueOf(scComment.getId()));
                comment.setType(1);
                List<ScComment> getAllRe = scCommentDao.queryByParam(comment);
                scComment.setReCom(getAllRe);
            }
        }
        return new ResponseResult(ResponseCode.SUCCESS,getAll);
    }

    public ResponseResult getCommentByUserAndType(ScUser scUser,int type){
        ScComment comment = new ScComment();
        comment.setValidFlag(1);
        comment.setUserNo(scUser.getUserNo());
        comment.setType(type);
        List<ScComment> getAll = scCommentDao.queryByParam(comment);
        return new ResponseResult(ResponseCode.SUCCESS,getAll);
    }

    public ResponseResult queryByAritcleParam(ScUser scUser){
        ScComment comment = new ScComment();
        comment.setUserNo(scUser.getUserNo());
        List<ScComment> getAll = scCommentDao.queryByAritcleParam(comment);
        return new ResponseResult(ResponseCode.SUCCESS,getAll);
    }

    public ResponseResult addComment(String data, ScUser scUser){
        if(scUser == null)
            new ResponseResult(ResponseCode.SERVICE_ROLE_ERROR,"无权限访问");
        ScComment scComment = JSON.parseObject(data,ScComment.class);
        scComment.setCreateTime(new Date());
        scComment.setUpdateTime(new Date());
        scComment.setUserNo(scUser.getUserNo());
        scComment.setValidFlag(1);
        scCommentDao.insert(scComment);
        return new ResponseResult(ResponseCode.SUCCESS,"提交成功");
    }

    public ResponseResult delComment(Integer id){
        ScComment scComment = new ScComment();
        scComment.setValidFlag(0);
        scComment.setId(id);
        scCommentDao.update(scComment);
        return new ResponseResult(ResponseCode.SUCCESS,"删除成功");
    }
}
