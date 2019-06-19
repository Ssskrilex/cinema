package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.RefundService;
import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.data.management.MovieMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.data.sales.RefundMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.Movie;
import com.example.cinema.po.Refund;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.po.Ticket;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.RefundVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
@Service
public class RefundServiceImpl implements RefundService {
    @Autowired
    RefundMapper refundMapper;
    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleMapper scheduleMapper;
    @Autowired
    MovieMapper movieMapper;
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

    @Override
    public ResponseVO doRefund(int tid) {
        try {
            Ticket ticket = ticketMapper.selectTicketById(tid);
            int sid = ticket.getScheduleId();
            ScheduleItem scheduleItem = scheduleMapper.selectScheduleById(sid);
            Date now = new Date();
            long time = (now.getTime() - scheduleItem.getStartTime().getTime())/60000;
            List<Refund> refunds = refundMapper.selectRefundBySchedule(scheduleItem.getMovieId());
            int max = 0;
            for (Refund r :refunds){
                if(time>=r.getTime() && r.getPercent()>=max){
                    max = r.getPercent();
                }
            }
            if(max == 0){
                return ResponseVO.buildSuccess(0);
            }
            double refundmoney = scheduleItem.getFare() * max / 100.0;
            ticketMapper.deleteTicket(tid);
            return  ResponseVO.buildSuccess(refundmoney);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("退票失败");
        }
    }

    @Override
    public ResponseVO getRefunds() {
        List<RefundVO> ret=new ArrayList<>();
        List<Movie> list=movieMapper.selectAllMovie();
        for(Movie movie:list){
            List<Refund> item =refundMapper.selectRefundBySchedule(movie.getId());
            for(Refund refund:item){
                ret.add(new RefundVO(refund));
            }
        }
        return ResponseVO.buildSuccess(ret);
    }


}
