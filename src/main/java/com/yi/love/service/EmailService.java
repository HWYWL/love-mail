package com.yi.love.service;

/**
 * 邮件接口
 *
 * @author YI create in 2022/2/8 10:45
 * @description
 */
public interface EmailService {
    /**
     * 发送邮件
     *
     * @param text 邮件内容
     */
    void sendEmail(String text);
}
