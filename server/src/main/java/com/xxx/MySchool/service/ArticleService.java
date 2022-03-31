package com.kbz1121.MySchool.service;

import com.alibaba.fastjson.JSON;
import com.kbz1121.MySchool.dao.ScArticleDao;
import com.kbz1121.MySchool.dao.ScPicDao;
import com.kbz1121.MySchool.entity.ScArticle;
import com.kbz1121.MySchool.entity.ScPic;
import com.kbz1121.MySchool.entity.ScUser;
import com.kbz1121.MySchool.system.ResponseFormat.ResponseCode;
import com.kbz1121.MySchool.system.ResponseFormat.ResponseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {
    @Resource
    ScArticleDao scArticleDao;
    @Resource
    ScPicDao scPicDao;
    public ResponseResult getArticle(){
        return new ResponseResult(ResponseCode.SUCCESS,scArticleDao.queryById(1));
    }
    public ResponseResult getArticle(String data){
        ScArticle scArticle = JSON.parseObject(data,ScArticle.class);
        scArticle.setValidFalg(1);
        List<ScArticle> scArticleList = scArticleDao.queryAll(scArticle);
        for (ScArticle one:scArticleList){
            ScPic scPic = new ScPic();
            scPic.setArtFk(one.getId());
            scPic.setValidFlag(1);
            List<ScPic> picList = scPicDao.queryAll(scPic);
            if(picList.size()>0){
                List<ScPic> artPicList = scArticle.getShowType() == 0 ? Arrays.asList(picList.get(0)) : picList;
                one.setImgList(artPicList);
            }
        }
        return new ResponseResult(ResponseCode.SUCCESS,scArticleList);
    }

    public ResponseResult addArticle(String param, ScUser scUser){
        if(scUser == null)
            new ResponseResult(ResponseCode.SERVICE_ROLE_ERROR,"无权限访问");
        ScArticle scArticle = JSON.parseObject(param,ScArticle.class);
        if(scArticle.getType() > 2 ){
            scArticle.setDeptFk(scUser.getDeptFk());
        }
        scArticle.setUserFk(scUser.getUserNo());
        scArticle.setCreateTime(new Date());
        scArticle.setUpdateTime(new Date());
        scArticle.setValidFalg(1);
        scArticleDao.insert(scArticle);
        if(scArticle.getPic() == 1){
            ScPic scPic = new ScPic();
            scPic.setBase64(scArticle.getPicBase64());
            scPic.setArtFk(scArticle.getId());
            scPic.setType(0);
            scPic.setValidFlag(1);
            scPic.setUserFk(scUser.getUserNo());
            scPic.setCreateTime(new Date());
            scPicDao.insert(scPic);
        }
        return new ResponseResult(ResponseCode.SUCCESS,"发布成功");
    }

    public ResponseResult delArticleById(Integer id){
        ScArticle article = new ScArticle();
        article.setId(id);
        article.setValidFalg(0);
        scArticleDao.update(article);
        return new ResponseResult(ResponseCode.SUCCESS,"删除成功");
    }

    public ResponseResult getArticleByUserNo(ScUser scUser){
        ScArticle article = new ScArticle();
        article.setUserFk(scUser.getUserNo());
        article.setValidFalg(1);
        return new ResponseResult(ResponseCode.SUCCESS,scArticleDao.queryAll(article));
    }

}
