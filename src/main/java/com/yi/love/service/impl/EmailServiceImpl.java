package com.yi.love.service.impl;

import com.yi.love.config.Config;
import com.yi.love.service.EmailService;
import io.github.biezhi.ome.OhMyEmail;
import io.github.biezhi.ome.SendMailException;
import org.springframework.stereotype.Service;

/**
 * 邮件发送逻辑
 * @author YI create in 2022/2/8 10:47
 * @description
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendEmail(String text) {
        try {
            OhMyEmail.subject("你家大宝的日常问候(^.^)")
                    .from("亲爱的小雪雪:(^.^)")
                    .to(Config.to)
                    .text(text)
                    .send();
        } catch (SendMailException e) {
            e.printStackTrace();
        }
    }
}
