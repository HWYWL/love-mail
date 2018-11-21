package com.yi.love.model;

import java.util.List;

@lombok.Data
public class Data {
    private String shidu;
    private int pm25;
    private int pm10;
    private String quality;
    private String wendu;
    private String ganmao;
    private Yesterday yesterday;
    private List<Forecast> forecast;
}
