package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.StaffAccountService;
import com.example.cinema.data.user.StaffAccountMapper;
import com.example.cinema.po.Staff;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.StaffUserForm;
import com.example.cinema.vo.StaffUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseVO selectStaffList() {
        try {
            List<Staff> staffList = staffAccountMapper.getStaffAccount();
            List<StaffUserVO> staffUserVOList = new ArrayList<>();
            for(Staff staff : staffList){
                staffUserVOList.add(new StaffUserVO(staff));
            }
            return ResponseVO.buildSuccess(staffUserVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseVO.buildFailure("failure");
        }
    }

    @Override
    public ResponseVO deleteAccount(StaffUserVO staffUserVO){
        try {
            staffAccountMapper.deleteStaffAccount(staffUserVO.getId());
        } catch (Exception e) {
            return ResponseVO.buildFailure("delete failed");
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO updateAccount(StaffUserVO staffUserVO){

        try {
            staffAccountMapper.updateStaffAccount(staffUserVO.getId(),
                    staffUserVO.getUsername(), staffUserVO.getPassword(), staffUserVO.getStatus());
                    return ResponseVO.buildSuccess(staffUserVO);
        } catch (Exception e) {
            return ResponseVO.buildFailure("update failed");
        }


    }



}
