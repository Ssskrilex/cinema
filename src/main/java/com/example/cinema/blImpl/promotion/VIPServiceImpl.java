package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.promotion.VIPTypeMapper;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.User;
import com.example.cinema.po.VIPChargeRecord;
import com.example.cinema.po.VIPType;
import com.example.cinema.vo.*;
import com.example.cinema.po.VIPCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService {
    @Autowired
    VIPCardMapper vipCardMapper;
    @Autowired
    VIPTypeMapper vipTypeMapper;
    @Autowired
    AccountMapper accountMapper;

    @Override
    public ResponseVO addVIPCard(int userId) {
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setBalance(0);
        try {
            int id = vipCardMapper.insertOneCard(vipCard);
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getVIPInfo() {
        VIPInfoVO vipInfoVO = new VIPInfoVO();
        vipInfoVO.setDescription(VIPCard.description);
        vipInfoVO.setPrice(VIPCard.price);
        return ResponseVO.buildSuccess(vipInfoVO);
    }

    @Override
    public ResponseVO charge(VIPCardForm vipCardForm) {

        VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }
        double balance = vipCard.calculate(vipCardForm.getAmount());
        vipCard.setBalance(vipCard.getBalance() + balance);
        try {
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCard.getBalance());
            VIPChargeRecord vipChargeRecord = new VIPChargeRecord();
            vipChargeRecord.setUserId(vipCard.getUserId());
            vipChargeRecord.setVIPId(vipCard.getId());
            vipChargeRecord.setAmount(balance);
            vipCardMapper.chargeRecord(vipChargeRecord);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTypes() {
        try {
            List<VIPType> types = vipTypeMapper.selectAll();
            List<VIPTypeVO> result = new ArrayList<>();
            for (VIPType t : types) {
                result.add(new VIPTypeVO(t));
            }
            return ResponseVO.buildSuccess(result);

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addType(VIPTypeForm vipTypeForm) {

        try {
            VIPType vipType = new VIPType();
            vipType.setName(vipTypeForm.getName());
            vipType.setAmount(vipTypeForm.getAmount());
            vipType.setDescription(vipTypeForm.getDescription());
            vipType.setDiscount(vipTypeForm.getDiscount());
            vipType.setPrice(vipTypeForm.getPrice());
            int id = vipTypeMapper.insertOneType(vipType);
            vipType.setId(id);
            return ResponseVO.buildSuccess(new VIPTypeVO(vipType));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateType(VIPTypeForm vipTypeForm) {
        try {
            VIPType vipType = vipTypeMapper.selectTypeById(vipTypeForm.getId());
            if(vipType==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            vipTypeMapper.updateType(vipTypeForm.getId(),vipTypeForm.getName(),vipTypeForm.getDescription(),vipTypeForm.getPrice(),vipTypeForm.getAmount(),vipTypeForm.getDiscount());
            return ResponseVO.buildSuccess(new VIPTypeVO(vipTypeMapper.selectTypeById(vipTypeForm.getId())));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTypeById(int id) {
        try {
            VIPType vipType = vipTypeMapper.selectTypeById(id);
            return ResponseVO.buildSuccess(new VIPTypeVO(vipType));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO getChargeRecord(int id){
        try {
            List<VIPChargeRecord> recordList = vipCardMapper.selectChargeRecords(id);
            List<VIPChargeRecordVO> recordVOList = new ArrayList<>();
            for(VIPChargeRecord record : recordList){
                recordVOList.add(new VIPChargeRecordVO(record));
            }
            return ResponseVO.buildSuccess(recordVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getMembersByExpenditure(double limit){
        try {
            List<User> userList = accountMapper.getAllUser();
            List<Double> expenditure = new ArrayList<>();
            for(User user : userList) {
                List<VIPChargeRecord> chargeRecordList = vipCardMapper.selectChargeRecords(user.getId());
                double sum = 0;
                for(VIPChargeRecord vipChargeRecord : chargeRecordList){
                    sum += vipChargeRecord.getAmount();
                }
                expenditure.add(sum-vipCardMapper.selectCardByUserId(user.getId()).getBalance());
            }
            List<UserVO> userVOList = new ArrayList<>();
            for(int i = 0; i < expenditure.size(); i++){
                if(expenditure.get(i) > limit)
                    userVOList.add(new UserVO(userList.get(i)));
            }
            return ResponseVO.buildSuccess(userVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("failure");
        }
    }


}
