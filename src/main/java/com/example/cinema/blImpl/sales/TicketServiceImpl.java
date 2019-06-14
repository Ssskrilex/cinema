package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.blImpl.promotion.CouponServiceImpl;
import com.example.cinema.data.promotion.ActivityMapper;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by liying on 2019/4/16.
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    HallServiceForBl hallService;
    @Autowired
    CouponMapper couponmapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    VIPCardMapper vipCardMapper;

    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
        try{
            List<Ticket> tickets = null;
            List<TicketVO> TicketVOS = null;
            for(int i = 0; i < ticketForm.getSeats().size(); i++) {
                Ticket ticket = new Ticket();
                ticket.setId(ticketForm.getUserId());
                ticket.setScheduleId(ticketForm.getScheduleId());
                ticket.setColumnIndex(ticketForm.getSeats().get(i).getColumnIndex());
                ticket.setRowIndex(ticketForm.getSeats().get(i).getRowIndex());
                tickets.add(ticket);
                TicketVOS.add(ticket.getVO());
            }
            ticketMapper.insertTickets(tickets);
           return ResponseVO.buildSuccess(TicketVOS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    @Transactional
    public ResponseVO completeTicket(List<Integer> id, int couponId) {
        try{
            Coupon coupon = couponmapper.selectById(couponId);
            double total = 0;
            List<Ticket> ticketlist = new ArrayList<>();
            int movieid = -1;
            for(Integer i :id){
                Ticket temp = ticketMapper.selectTicketById(i);
                ticketlist.add(temp);
                total = total + scheduleService.getScheduleItemById(temp.getScheduleId()).getFare();
                if(movieid == -1){
                    movieid = scheduleService.getScheduleItemById(temp.getScheduleId()).getMovieId();
                }

            }
            int userid = ticketlist.get(0).getUserId();

            if(coupon.getStartTime().after(new Date())|| coupon.getEndTime().before(new Date())){
                return ResponseVO.buildFailure("优惠券已过期或尚未开始使用");
            }
            if(coupon.getTargetAmount()<total){
                return ResponseVO.buildFailure("总金额不够使用优惠券");
            }
            total = total - coupon.getDiscountAmount();
            couponmapper.deleteCouponUser(couponId,userid);

            List<Activity> activities = activityMapper.selectActivitiesByMovie(movieid);
            for(Activity ac :activityMapper.selectActivitiesWithoutMovie()){
                activities.add(ac);
            }
            List<Coupon> coupons = new ArrayList<>();
            CouponService couponService = new CouponServiceImpl();
            for(Activity ac : activities){
                coupons.add(ac.getCoupon());
                couponService.issueCoupon(ac.getCoupon().getId(),userid);
            }
            List<TicketVO> ticketvolist = new ArrayList<>();
            for(Ticket t:ticketlist){
                ticketMapper.updateTicketState(t.getId(),1);
                ticketvolist.add(t.getVO());

            }
            TicketWithCouponVO  ticketWithCouponVO = new TicketWithCouponVO();
            ticketWithCouponVO.setActivities(activities);
            ticketWithCouponVO.setCoupons(coupons);
            ticketWithCouponVO.setTotal(total);
            ticketWithCouponVO.setTicketVOList(ticketvolist);
            return ResponseVO.buildSuccess(ticketWithCouponVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }


    }

    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
            ScheduleItem schedule=scheduleService.getScheduleItemById(scheduleId);
            Hall hall=hallService.getHallById(schedule.getHallId());
            int[][] seats=new int[hall.getRow()][hall.getColumn()];
            tickets.stream().forEach(ticket -> {
                seats[ticket.getRowIndex()][ticket.getColumnIndex()]=1;
            });
            ScheduleWithSeatVO scheduleWithSeatVO=new ScheduleWithSeatVO();
            scheduleWithSeatVO.setScheduleItem(schedule);
            scheduleWithSeatVO.setSeats(seats);
            return ResponseVO.buildSuccess(scheduleWithSeatVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTicketByUser(int userId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketByUser(userId);
            List<TicketVO> ticketVOS = new ArrayList<>();
            for(Ticket t :tickets){
                ticketVOS.add(t.getVO());
            }
            return  ResponseVO.buildSuccess(ticketVOS);//这里不知道能不能返回一个列表,不能的话到时候改成TicketsWithCouponVO

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    @Transactional
    public ResponseVO completeByVIPCard(List<Integer> id, int couponId) {
        try{
            Coupon coupon = couponmapper.selectById(couponId);
            double total = 0;
            List<Ticket> ticketlist = new ArrayList<>();
            int movieid = -1;
            for(Integer i :id){
                Ticket temp = ticketMapper.selectTicketById(i);
                ticketlist.add(temp);
                total = total + scheduleService.getScheduleItemById(temp.getScheduleId()).getFare();
                if(movieid == -1){
                    movieid = scheduleService.getScheduleItemById(temp.getScheduleId()).getMovieId();
                }

            }
            int userid = ticketlist.get(0).getUserId();
            VIPCard card = vipCardMapper.selectCardByUserId(userid);


            if(coupon.getStartTime().after(new Date())|| coupon.getEndTime().before(new Date())){
                return ResponseVO.buildFailure("优惠券已过期或尚未开始使用");
            }
            if(coupon.getTargetAmount()<total){
                return ResponseVO.buildFailure("总金额不够使用优惠券");
            }
            total = total - coupon.getDiscountAmount();
            if(card.getBalance()<total){
                return ResponseVO.buildFailure("会员卡内余额不足，请充值");
            }
            vipCardMapper.updateCardBalance(card.getId(),card.getBalance()-total);
            couponmapper.deleteCouponUser(couponId,userid);

            List<Activity> activities = activityMapper.selectActivitiesByMovie(movieid);
            for(Activity ac :activityMapper.selectActivitiesWithoutMovie()){
                activities.add(ac);
            }
            List<Coupon> coupons = new ArrayList<>();
            CouponService couponService = new CouponServiceImpl();
            for(Activity ac : activities){
                coupons.add(ac.getCoupon());
                couponService.issueCoupon(ac.getCoupon().getId(),userid);
            }
            List<TicketVO> ticketvolist = new ArrayList<>();
            for(Ticket t:ticketlist){
                ticketMapper.updateTicketState(t.getId(),1);
                ticketvolist.add(t.getVO());

            }
            TicketWithCouponVO  ticketWithCouponVO = new TicketWithCouponVO();
            ticketWithCouponVO.setActivities(activities);
            ticketWithCouponVO.setCoupons(coupons);
            ticketWithCouponVO.setTotal(total);
            ticketWithCouponVO.setTicketVOList(ticketvolist);
            return ResponseVO.buildSuccess(ticketWithCouponVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO cancelTicket(List<Integer> id) {
        try {
            ticketMapper.updateTicketState(id, 2);
            return ResponseVO.buildSuccess("订单已失效");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }



}
