package com.example.cinema.controller.user;

import com.example.cinema.bl.user.AccountService;
import com.example.cinema.vo.ActivityForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    AccountService accountService;

    @PostMapping("/users/beyondamount")
    public ResponseVO getUsersByAmount(@RequestBody int amount){
        return accountService.selectUserByAmount(amount);
    }
}
