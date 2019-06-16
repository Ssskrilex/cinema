package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.RefundService;
import com.example.cinema.data.sales.RefundMapper;
import com.example.cinema.po.Refund;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.RefundVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RefundServiceImpl implements RefundService {
    @Autowired
    RefundMapper refundMapper;
    @Override
    public ResponseVO addRefund(RefundForm refundForm) {
        try {
            Refund refund = new Refund();
            refund.setDescription(refundForm.getDescription());
            refund.setPercent(refundForm.getPercent());
            refund.setSid(refundForm.getSid());
            refund.setTime(refundForm.getTime());
            int id = refundMapper.insertRefund(refund);
            return  ResponseVO.buildSuccess(id);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateRefund(RefundForm refundForm) {
        try {
            Refund refund = refundMapper.selectRefundById(refundForm.getId());
            if(refund==null){
                return ResponseVO.buildFailure("没有此退票策略");
            }
            refundMapper.updateRefund(refundForm.getId(),refundForm.getDescription(),refundForm.getSid(),refundForm.getTime(),refundForm.getPercent());
            return  ResponseVO.buildSuccess("更新成功");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO getRefundById(int id) {
        try {
            Refund refund = refundMapper.selectRefundById(id);
            return  ResponseVO.buildSuccess(new RefundVO(refund));
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseVO.buildFailure("失败");
    }

    }

    @Override
    public ResponseVO getRefundByMovieId(int sid) {
        try {
            List<Refund> refund = refundMapper.selectRefundBySchedule(sid);
            List<RefundVO> refundVOS = new ArrayList<>();
            for (Refund r : refund){
                refundVOS.add(new RefundVO(r));
            }
            return  ResponseVO.buildSuccess(refundVOS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deletRefund(int id) {
        try {
            refundMapper.deleteRefund(id);
            return  ResponseVO.buildSuccess("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

}
