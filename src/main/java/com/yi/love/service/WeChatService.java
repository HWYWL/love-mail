package com.yi.love.service;

/**
 * 微信接口
 * @author YI create in 2022/2/8 10:46
 * @description
 */
public interface WeChatService {
    /**
     * 立刻发送消息
     *
     * @param friendName 发送的朋友/群名称
     * @param message    发送的内容
     */
    void sendMsgNow(String friendName, String message);

    /**
     * 定时发送任务
     *
     * @param friendName 发送的朋友/群名称
     * @param timeStr    定时时间
     * @param message    发送的内容
     */
    void sendMsgSchedule(String friendName, String timeStr, String message);
}
