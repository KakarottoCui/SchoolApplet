package com.kbz1121.MySchool.system.Interceptor;

import com.kbz1121.MySchool.dao.ScLoginDao;
import com.kbz1121.MySchool.dao.ScUserDao;
import com.kbz1121.MySchool.entity.ScLogin;
import com.kbz1121.MySchool.entity.ScUser;
import com.kbz1121.MySchool.service.ScLoginService;
import com.kbz1121.MySchool.service.ScUserService;
import com.kbz1121.MySchool.service.impl.ScLoginServiceImpl;
import com.kbz1121.MySchool.service.impl.ScUserServiceImpl;
import com.kbz1121.MySchool.system.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor{

    public ScUserDao scUserDao;
    public ScLoginDao scLoginDao;

    public LoginInterceptor(ScUserDao scUserDao,ScLoginDao scLoginDao){
        this.scUserDao = scUserDao;
        this.scLoginDao = scLoginDao;
    }
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String token = request.getParameter("token");

        if(StringUtils.isNotEmpty(token)){
            ScUser scUser = (ScUser)request.getSession().getAttribute("user");
            if(scUser!=null && token.equals(scUser.getToken())){
                return true;
            }else{
                ScLogin scLogin = new ScLogin();
                scLogin.setToken(token);
                scLogin.setValidFlag(1);
                List<ScLogin> loginList = scLoginDao.queryAll(scLogin);
                if(loginList ==null || loginList.size() == 0){
                    session.removeAttribute("user");
                    return true;
                }else{
                    scUser = new ScUser();
                    scUser.setUnionid(loginList.get(0).getUnionid());
                    scUser.setValidFlag(1);
                    List<ScUser> userList = scUserDao.queryAll(scUser);
                    if(userList!=null&&userList.size()>0){
                        scUser = userList.get(0);
                        scUser.setToken(token);
                        session.setAttribute("user",scUser);
                        return true;

                    }else{
                        session.removeAttribute("user");
                        return true;
                    }
                }
            }
        }else{
            session.removeAttribute("user");
            return true;
        }
    }
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
 
        
    }
	
	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
 
        
    }
}
