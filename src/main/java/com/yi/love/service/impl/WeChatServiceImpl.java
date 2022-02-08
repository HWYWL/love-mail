package com.yi.love.service.impl;

import com.yi.love.service.WeChatService;
import com.yi.love.utils.WeChatRobotUtil;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 微信发送逻辑
 * @author YI create in 2022/2/8 10:54
 * @description
 */
@Service
public class WeChatServiceImpl implements WeChatService {
    ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();

    /**
     * 立刻发送消息
     *
     * @param friendName 发送的朋友/群名称
     * @param message    发送的内容
     */
    public void sendMsgNow(String friendName, String message) {
        WeChatRobotUtil robot = new WeChatRobotUtil();
        printLog(friendName, message);
        robot.openWeChat();
        robot.chooseFriends(friendName);
        robot.sendMessage(message);
    }

    /**
     * 定时发送任务
     *
     * @param friendName 发送的朋友/群名称
     * @param timeStr    定时时间
     * @param message    发送的内容
     */
    public void sendMsgSchedule(String friendName, String timeStr, String message) {
        exe.schedule(() -> sendMsgNow(friendName, message), getDate(timeStr), TimeUnit.SECONDS);
    }

    /**
     * 发送内容日志
     *
     * @param friendName 发送给谁
     * @param message    发送的消息
     */
    private void printLog(String friendName, String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("-----------------发送消息-----------------");
        System.out.println("当前时间: " + sdf.format(new Date()));
        System.out.println("发送对象: " + friendName);
        System.out.println("发送内容: " + message);
    }

    /**
     * 获取定时任务的时间
     *
     * @param timeStr 时间
     * @return 任务延时发送时间(秒)
     */
    private long getDate(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        String targetTime = currentDate + " " + timeStr;

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //目标时间 时间戳
        long targetTimer = 0;
        try {
            targetTimer = sdf.parse(targetTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //当前时间 时间戳
        long currentTimer = System.currentTimeMillis();
        //判断是否已过目标时间
        if (targetTimer < currentTimer) {
            //目标时间加一天
            targetTimer += 1000 * 60 * 60 * 24;
        }
        //返回目标日期
        Date date = new Date(targetTimer);

        return (date.getTime() - System.currentTimeMillis()) / 1000;
    }
}
