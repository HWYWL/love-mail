package com.yi.love.model;

import java.util.Date;

@lombok.Data
public class Weather {
    private Date time;
    private CityInfo cityInfo;
    private String date;
    private String message;
    private int status;
    private Data data;
}
