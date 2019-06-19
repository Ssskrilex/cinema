package com.example.cinema.data.user;

import com.example.cinema.po.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface StaffAccountMapper {
    /**
     * 创建一个新的账号
     * @param username
     * @param password
     * @param status
     * @return
     */
    public int createNewStaffAccount(@Param("username") String username, @Param("password") String password, @Param("status") String status);

    /**
     * 根据用户名查找账号
     * @param username
     * @return
     */
    public Staff getStaffAccountByName(@Param("username") String username);

    /**
     * 根据用户id更新账号
     * @param id
     * @return
     */
    public void updateStaffAccount(@Param("id") Integer id, @Param("username") String username, @Param("password") String password, @Param("status") String status);

    /**
     * 根据用户id删除账号
     * @param id
     * @return
     */
    public void deleteStaffAccount(@Param("id") Integer id);
}
