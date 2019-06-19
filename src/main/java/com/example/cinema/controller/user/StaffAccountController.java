package com.example.cinema.controller.user;

import com.example.cinema.bl.user.StaffAccountService;
import com.example.cinema.config.InterceptorConfiguration;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.StaffUserForm;
import com.example.cinema.vo.StaffUserVO;
import com.example.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class StaffAccountController {
    @Autowired
    StaffAccountService staffAccountService;
    private final static String ACCOUNT_INFO_ERROR="用户名或密码错误";

    @PostMapping("/login")
    public ResponseVO login(@RequestBody StaffUserForm staffUserForm, HttpSession session){
        StaffUserVO staff = staffAccountService.login(staffUserForm);
        if(session==null){
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        //注册session
        session.setAttribute(InterceptorConfiguration.SESSION_KEY,staffUserForm);
        return ResponseVO.buildSuccess(user);
    }
    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody StaffUserForm staffUserForm){
        return staffAccountService.registerAccount(staffUserForm);
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return "index";
    }


}
