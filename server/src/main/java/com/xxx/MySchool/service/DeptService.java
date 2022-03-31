package com.kbz1121.MySchool.service;

import com.kbz1121.MySchool.dao.ScDeptDao;
import com.kbz1121.MySchool.entity.ScDept;
import com.kbz1121.MySchool.system.ResponseFormat.ResponseCode;
import com.kbz1121.MySchool.system.ResponseFormat.ResponseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeptService {
    @Resource
    ScDeptDao scDeptDao;

    public ResponseResult getDeptByType(Integer level){
        ScDept dept = new ScDept();
        dept.setValidFlag(1);
        dept.setLevel(level);
        return new ResponseResult(ResponseCode.SUCCESS,scDeptDao.queryAll(dept));
    }
}
