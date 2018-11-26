package com.yi.love.config;

import io.github.biezhi.ome.OhMyEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.security.GeneralSecurityException;

import static io.github.biezhi.ome.OhMyEmail.SMTP_QQ;

/**
 * 定时任务配置类
 * @author YI
 * @date 2018-11-5 10:04:06
 */
@Configuration
@EnableScheduling
public class Config {
    private static String username;
    private static String password;
    public static String to;

    public static final String MEIZU_WEATHER_URL = "http://aider.meizu.com/app/weather/listWeather";

    @Bean
    public String before() throws GeneralSecurityException {
        // 配置，一次即可
        OhMyEmail.config(SMTP_QQ(false), username, password);

        return "Successful";
    }

    @Value("${email.username}")
    public void setUsername(String username) {
        Config.username = username;
    }

    @Value("${email.password}")
    public void setPassword(String password) {
        Config.password = password;
    }

    @Value("${email.to}")
    public void setTo(String to) {
        Config.to = to;
    }
}
