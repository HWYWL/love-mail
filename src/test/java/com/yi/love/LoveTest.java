package com.yi.love;

import com.yi.love.task.ScheduledTasks;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: YI
 * @description: 测试爬虫和发送
 * @date: create in 2022年2月8日10:39:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoveTest {
    @Autowired
    ScheduledTasks scheduledTasks;

    @BeforeClass
    public static void setupHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }

    @Test
    public void innerCreateJsonSQLTest() {
        scheduledTasks.cronTask();
    }
}
