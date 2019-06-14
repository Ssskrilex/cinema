package com.example.cinema.vo;

import java.util.Date;

/**
 * @author 161250110
 * @date 2019/4/21 3:00 PM
 */
public class PlacingRateVO {
    private Date date;

    private Double moviePlacingRate;

    private String movieName;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getMoviePlacingRate() {
        return moviePlacingRate;
    }

    public void setMoviePlacingRate(Double moviePlacingRate) {
        this.moviePlacingRate = moviePlacingRate;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
