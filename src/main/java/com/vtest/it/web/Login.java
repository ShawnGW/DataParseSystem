package com.vtest.it.web;

import com.vtest.it.dao.vtptmtmapperdao.UserValidate;
import com.vtest.it.pojo.systemyuser.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/system")
public class Login {
    private UserValidate userValidate;
    @Autowired
    public void setUserValidate(UserValidate userValidate) {
        this.userValidate = userValidate;
    }
    @RequestMapping("/register")
    public String register()
    {
        return "login";
    }
    @RequestMapping("/registerUser")
    public String register(@RequestParam("userName") String userName,@RequestParam("password")String password,@RequestParam("vGroup")String vGroup,@RequestParam("email")String email,HttpServletRequest request)
    {
        SystemUser user =new SystemUser();
        user.setUserName(userName);
        user.setPassword(password);
        user.setvGroup(vGroup);
        user.setLevel(1);
        user.setEmail(email);
        userValidate.register(user);
        HttpSession session=request.getSession();
        session.setAttribute("SYSTEM_USER",user);
        return "main";
    }
    @RequestMapping("/cancel")
    public String cancel(@RequestParam("userName")String userName)
    {
        userValidate.cancel(userName);
        return "login";
    }
    @RequestMapping("/loginOff")
    public String loginOff(HttpServletRequest request)
    {
        HttpSession session=request.getSession();
        SystemUser user=(SystemUser)session.getAttribute("SYSTEM_USER");
        if (null!=user)
        {
            session.removeAttribute("SYSTEM_USER");
        }
        return "login";
    }
    @RequestMapping("/validate")
    public String validate(@RequestParam("userName")String userName, @RequestParam("password") String password, HttpServletRequest request, ModelMap modelMap)
    {
        SystemUser user= userValidate.validate(userName);
        if (null==user)
        {
            modelMap.addAttribute("message","there was no this user,please check your username!");
            return "login";
         }else
         {
            if (!user.getPassword().equals(password))
            {
                modelMap.addAttribute("message","your password is not right,please input it again!");
                return "login";
            }else
            {
                HttpSession session=request.getSession();
                session.setAttribute("SYSTEM_USER",user);
                return "main";
            }
         }
    }
    @RequestMapping("/login")
    public String login()
    {
        return "login";
    }
}
