package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.AccountService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.Ticket;
import com.example.cinema.po.User;
import com.example.cinema.po.UserBox;
import com.example.cinema.po.VIPChargeRecord;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private VIPCardMapper vipCardMapper;


    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {
            accountMapper.createNewAccount(userForm.getUsername(), userForm.getPassword());
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getUsername());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return new UserVO(user);
    }

    @Override
    public ResponseVO selectExpenseRecord(UserForm userForm){
        try {
            List<Ticket> ticketList = ticketMapper.selectTicketByUser(
                    accountMapper.getAccountByName(userForm.getUsername()).getId());
            List<VIPChargeRecord> chargeRecordList = vipCardMapper.selectChargeRecords(
                    accountMapper.getAccountByName(userForm.getUsername()).getId());
            List<ExpenseRecordVO> recordVOList = new ArrayList<>();
            for(Ticket ticket : ticketList){
                ExpenseRecordVO expenseRecordVO = new ExpenseRecordVO();
                expenseRecordVO.setTicketVO(ticket.getVO());
                recordVOList.add(expenseRecordVO);
            }
            for (VIPChargeRecord vipChargeRecord : chargeRecordList){
                ExpenseRecordVO expenseRecordVO = new ExpenseRecordVO();
                expenseRecordVO.setVipChargeRecordVO(new VIPChargeRecordVO(vipChargeRecord));
                recordVOList.add(expenseRecordVO);
            }
            return ResponseVO.buildSuccess(recordVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("failure");
        }
    }

    @Override
    public ResponseVO selectUserByAmount(int amount) {
        try {
            List<UserBox> users = accountMapper.getUserByamount(amount);
            return ResponseVO.buildSuccess(users);
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }

    }
}
