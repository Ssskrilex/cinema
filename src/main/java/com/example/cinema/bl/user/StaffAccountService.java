package com.example.cinema.bl.user;

import com.example.cinema.po.Staff;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.StaffUserForm;
import com.example.cinema.vo.StaffUserVO;

public interface StaffAccountService {
    /**
     * 注册员工信息
     * @return
     */
    ResponseVO registerAccount(StaffUserForm staffUserForm);

    /**
     * 影院员工列表
     * @return
     */
    ResponseVO selectStaffList();

    /**
     * 员工信息删除
     * @return
     */
    ResponseVO deleteAccount(StaffUserVO staffUserVO);

    /**
     * 员工信息修改
     * @return
     */
    ResponseVO updateAccount(StaffUserVO staffUserVO);
}
