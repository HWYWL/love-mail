package com.yi.love.controller;

import cn.hutool.http.HttpUtil;
import com.yi.love.annotation.Log;
import com.yi.love.config.Config;
import com.yi.love.model.ResponseBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author YI
 * @date 2018-11-26 16:21:21
 */
@Controller
public class WeatherController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Log("获取天气信息")
    @RequestMapping("/")
    public String weather() {
        return "index";
    }

    @RequestMapping("weather/query")
    @ResponseBody
    public ResponseBo queryWeather(String areaId) {
        try {
            String data = HttpUtil.post(Config.MEIZU_WEATHER_URL, "cityIds=" + areaId);
            return ResponseBo.ok(data);
        } catch (Exception e) {
            log.error("查询天气失败", e);
            return ResponseBo.error("查询天气失败，请联系网站管理员！");
        }
    }
}
