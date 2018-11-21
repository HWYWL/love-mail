package com.yi.love.model;

import lombok.Data;

@Data
public class Forecast {
    private String date;
    private String sunrise;
    private String high;
    private String low;
    private String sunset;
    private int aqi;
    private String fx;
    private String fl;
    private String type;
    private String notice;
}
