package com.example.cinema.po;

import java.sql.Date;

/**
 * Created by 161250110 on 2019/6/14.
 */
public class DateAudience {
    /**
     * 观影人数
     */
    private int audienceNum;

    /**
     * 观影时间
     */
    private Date movieDate;

    /**
     * 观看电影id
     */
    private Integer movieId;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Date getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(Date movieDate) {
        this.movieDate = movieDate;
    }

    public int getAudienceNum() {
        return audienceNum;
    }

    public void setAudienceNum(int audienceNum) {
        this.audienceNum = audienceNum;
    }
}
