package com.kbz1121.MySchool.service;

import com.alibaba.fastjson.JSON;
import com.kbz1121.MySchool.dao.ScArticleDao;
import com.kbz1121.MySchool.dao.ScLoginDao;
import com.kbz1121.MySchool.dao.ScPicDao;
import com.kbz1121.MySchool.dao.ScUserDao;
import com.kbz1121.MySchool.entity.ScArticle;
import com.kbz1121.MySchool.entity.ScLogin;
import com.kbz1121.MySchool.entity.ScPic;
import com.kbz1121.MySchool.entity.ScUser;
import com.kbz1121.MySchool.system.ResponseFormat.ResponseCode;
import com.kbz1121.MySchool.system.ResponseFormat.ResponseResult;
import com.kbz1121.MySchool.system.WX.AesCbcUtil;
import com.kbz1121.MySchool.system.restful.SysRestful;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class WXCheckService {

    @Resource
    ScArticleDao scArticleDao;
    @Resource
    ScLoginDao scLoginDao;
    @Resource
    ScUserDao scUserDao;
    @Resource
    ScPicDao scPicDao;

    public ResponseResult checkStudent(String code,String data)  {
        SysRestful sysRestful = new SysRestful();
        String sys = sysRestful.wxGetUserKey(code);
        ScUser scUserSub = JSON.parseObject(data,ScUser.class);
        Map<String,String> keyMap = JSON.parseObject(sys, HashMap.class);
        ScUser scUser = new ScUser();
        scUser.setValidFlag(1);
        scUser.setUserNo(scUserSub.getUserNo());
        List<ScUser> oldUser = scUserDao.queryAll(scUser);
        if(oldUser.size()>0){
            return new ResponseResult(ResponseCode.SERVICE_ERROR,"学号重复");
        }
        scUser.setUserNo(null);
        scUser.setUnionid(keyMap.get("openid"));
        List<ScUser> scUserList = scUserDao.queryAll(scUser);
        if(scUserList !=null && scUserList.size() >0){
            scUser = scUserList.get(0);
            scUser.setUpdateTime(new Date());
            scUser.setName(scUserSub.getName());
            scUser.setDeptFk(scUserSub.getDeptFk());
            scUser.setType(1);
            scUser.setUserNo(scUserSub.getUserNo());
            scUser.setProject(scUserSub.getProject());
            scUser.setPhone(scUserSub.getPhone());
            scUserDao.updateByUid(scUser);
            return new ResponseResult(ResponseCode.SUCCESS,"授权成功");
        }else {
            return new ResponseResult(ResponseCode.SERVICE_ERROR,"验证前必须完成授权");
        }
    }

    public ResponseResult getUserInfo(String code,String enData,String iv,String userInfo)  {
        SysRestful sysRestful = new SysRestful();
        String sys = sysRestful.wxGetUserKey(code);
        HashMap<String,Object> res = new HashMap<String,Object>();
        Map<String,String> keyMap = JSON.parseObject(sys, HashMap.class);
        ScUser scUser = new ScUser();
        scUser.setUnionid(keyMap.get("openid"));
        scUser.setValidFlag(1);
        List<ScUser> scUserList = scUserDao.queryAll(scUser);
        if(scUserList !=null && scUserList.size() >0){
            scUser = scUserList.get(0);
            if(scUser.getDeptFk() == -1){
                res.put("token","");
                res.put("userInfo",scUser);
                return new ResponseResult(ResponseCode.SUCCESS,res);
            }
            ScPic scPic = new ScPic();
            scPic.setType(1);
            scPic.setUserFk(scUser.getUserNo());
            List<ScPic> scPics = scPicDao.queryAll(scPic);
            if(scPics.size()>0) {
                scUser.setPic(scPics.get(0).getBase64());
            }
            ScLogin scLogin = new ScLogin();
            scLogin.setValidFlag(1);
            scLogin.setStat(1);
            scLogin.setUnionid(keyMap.get("openid"));
            List<ScLogin> scLogins = scLoginDao.queryAll(scLogin);
            String token = "";
            if(scLogins ==null || scLogins.size() < 1){
                token = UUID.randomUUID().toString() +"-"+ System.currentTimeMillis();
                scLogin.setStat(1);
                scLogin.setToken(token);
                scLogin.setInfo(userInfo);
                scLogin.setCreateTime(new Date());
                scLogin.setUserFk(scUser.getUserNo());
                scLogin.setSessionKey(keyMap.get("session_key"));
                scLoginDao.insert(scLogin);
            }else {
                scLogin = scLogins.get(0);
                scLogin.setCreateTime(new Date());
                token = scLogin.getToken();
                scLoginDao.update(scLogin);
            }
            res.put("token",token);
            res.put("userInfo",scUser);
            return new ResponseResult(ResponseCode.SUCCESS,res);

        }else{
            Map<String,String> userMap = JSON.parseObject(userInfo, HashMap.class);
            scUser.setCreateTime(new Date());
            scUser.setUpdateTime(new Date());
            scUser.setNick(userMap.get("nickName"));
            scUser.setName(userMap.get("nickName"));
            scUser.setDeptFk(-1);
            scUser.setType(-1);
            scUser.setUserNo(String.valueOf(System.currentTimeMillis()));
            scUser.setUnionid(keyMap.get("openid"));
            scUser.setPicFk(-1);
            scUser.setPic(userMap.get("avatarUrl"));
            scUserDao.insert(scUser);
            ScPic scPic = new ScPic();
            scPic.setType(1);
            scPic.setBase64(userMap.get("avatarUrl"));
            scPic.setValidFlag(1);
            scPic.setArtFk(0);
            scPic.setUserFk(scUser.getUnionid());
            scPic.setCreateTime(new Date());
            scPicDao.insert(scPic);
            scUser.setPicFk(scPic.getId());
            scUserDao.update(scUser);
            res.put("token","");
            res.put("userInfo",scUser);
            return new ResponseResult(ResponseCode.SUCCESS,res);
        }
//        //{"session_key":"2w0s74ZV02\/rDO1Tnf53sQ==","openid":"o4oDB5WeDCQ118QXIcxNWY_Grmgg"}
//        try{
//            result = AesCbcUtil.decrypt(enData,keyMap.get("session_key"), iv, "UTF-8");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return new ResponseResult(ResponseCode.SUCCESS,result);
    }

}
