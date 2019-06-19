package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.StaffAccountService;
import com.example.cinema.data.user.StaffAccountMapper;
import com.example.cinema.po.Staff;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.StaffUserForm;
import com.example.cinema.vo.StaffUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Service
public class StaffAccountServiceImpl implements StaffAccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";

    @Autowired
    private StaffAccountMapper staffAccountMapper;

    @Override
    public ResponseVO registerAccount(StaffUserForm staffUserForm) {
        try {
            staffAccountMapper.createNewStaffAccount(staffUserForm.getUsername(), staffUserForm.getPassword(), staffUserForm.getStatus());
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }


    @Override
    public StaffUserVO login(StaffUserForm staffUserForm) {
        Staff staff = staffAccountMapper.getStaffAccountByName(staffUserForm.getUsername());
        if (null == staff || !staff.getPassword().equals(staffUserForm.getPassword())) {
            return null;
        }
        return new StaffUserVO(staff);
    }

    @Override
    public ResponseVO deleteAccount(Staff staff){
        try {
            staffAccountMapper.deleteStaffAccount(staff.getId());
        } catch (Exception e) {
            return ResponseVO.buildFailure("delete failed");
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO updateAccount(Staff staff){
        StaffUserVO staffUserVO = new StaffUserVO();
        try {
            staffAccountMapper.updateStaffAccount(staff.getId(),
                    staff.getUsername(), staff.getPassword(), staff.getStatus());
            staffUserVO.setPassword(staff.getPassword());
            staffUserVO.setUsername(staff.getUsername());
            staffUserVO.setStatus(staff.getStatus());
        } catch (Exception e) {
            return ResponseVO.buildFailure("update failed");
        }

        return ResponseVO.buildSuccess(staffUserVO);
    }



}
