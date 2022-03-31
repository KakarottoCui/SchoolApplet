package com.kbz1121.MySchool.system;

import com.kbz1121.MySchool.entity.ScUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonFunction {

    public static ScUser getUserInfo(HttpServletRequest request){
        return  (ScUser)request.getSession().getAttribute("user");
    }

}
