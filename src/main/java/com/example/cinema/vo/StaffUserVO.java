package com.example.cinema.vo;

import com.example.cinema.po.Staff;
import com.example.cinema.po.User;

/**
 * @author fjj
 * @date 2019/4/11 3:22 PM
 */
public class StaffUserVO {
    private Integer id;
    private String username;
    private String password;
    private String status;

    public StaffUserVO(Staff staff){
        this.id = staff.getId();
        this.username = staff.getUsername();
        this.password = staff.getPassword();
        this.status = staff.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
