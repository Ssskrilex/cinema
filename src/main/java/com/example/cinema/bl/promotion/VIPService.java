package com.example.cinema.bl.promotion;

import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.VIPTypeForm;
import com.example.cinema.vo.ResponseVO;



/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    ResponseVO addVIPCard(int userId);

    ResponseVO getCardById(int id);

    ResponseVO getVIPInfo();

    ResponseVO charge(VIPCardForm vipCardForm);

    ResponseVO getCardByUserId(int userId);

    ResponseVO getTypes();

    ResponseVO addType(VIPTypeForm vipTypeForm);

    ResponseVO updateType(VIPTypeForm vipTypeForm);

    ResponseVO getTypeById(int id);

    ResponseVO getChargeRecord(int id);

    ResponseVO getMembersByExpenditure(double limit);

}
