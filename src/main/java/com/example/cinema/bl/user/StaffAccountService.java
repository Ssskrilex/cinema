package com.example.cinema.bl.user;

import com.example.cinema.po.Staff;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.StaffUserForm;
import com.example.cinema.vo.StaffUserVO;

public interface StaffAccountService {
    /**
     * 注册员工账号
     * @return
     */
    ResponseVO registerAccount(StaffUserForm staffUserForm);

    /**
     * 影院员工登录，登录成功会将用户信息保存再session中
     * @return
     */
    StaffUserVO login(StaffUserForm staffUserForm);

    /**
     * 员工账号删除
     * @return
     */
    ResponseVO deleteAccount(Staff staff);

    /**
     * 员工信息修改
     * @return
     */
    ResponseVO updateAccount(Staff staff);
}
