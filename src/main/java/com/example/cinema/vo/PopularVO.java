package com.example.cinema.vo;

import java.util.List;

public class PopularVO {
    private List<MovieVO> movieVOList;
    private int money;

    public List<MovieVO> getMovieVOList() {
        return movieVOList;
    }

    public void setMovieVOList(List<MovieVO> movieVOList) {
        this.movieVOList = movieVOList;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
