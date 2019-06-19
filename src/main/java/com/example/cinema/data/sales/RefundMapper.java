package com.example.cinema.data.sales;

import com.example.cinema.po.Refund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RefundMapper {
    int insertRefund(Refund refund);
    Refund selectRefundById(@Param("id") int id);
    List<Refund> selectRefundBySchedule(@Param("sid") int sid);
    void updateRefund(@Param("id") int id, @Param("description") String description, @Param("sid") int sid, @Param("time") int time, @Param("percent") int percent);
    void deleteRefund(@Param("id") int id);

}
