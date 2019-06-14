package com.example.cinema.data.promotion;


import com.example.cinema.po.VIPType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/20.
 */
@Mapper
public interface VIPTypeMapper {

    int insertOneType(VIPType vipType);

    List<VIPType> selectAll();
    VIPType selectTypeById(@Param("id") int id);
    void updateType(@Param("id") int id,@Param("name") String name,@Param("description") String description,@Param("price") double price,@Param("amount") double amount,@Param("discount") double discount);

}
