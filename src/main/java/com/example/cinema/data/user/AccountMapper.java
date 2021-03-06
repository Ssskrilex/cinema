package com.example.cinema.data.user;

import com.example.cinema.po.User;
import com.example.cinema.po.UserBox;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Mapper
public interface AccountMapper {

    /**
     * 创建一个新的账号
     * @param username
     * @param password
     * @return
     */
    public int createNewAccount(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名查找账号
     * @param username
     * @return
     */
    public User getAccountByName(@Param("username") String username);

    /**
     * 获得所有用户
     * @return
     */
    public List<User> getAllUser();

    List<UserBox> getUserByamount(@Param("amount") int amount);
}
