package com.yi.love.utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

/**
 * 微信工具类
 * @author YI create in 2022/2/8 11:23
 * @description
 */
public class WeChatRobotUtil {
    private Robot bot = null;
    private Clipboard clip = null;

    public WeChatRobotUtil() {
        try {
            this.clip = Toolkit.getDefaultToolkit().getSystemClipboard();
            this.bot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开微信，我这边设置了CTRL+ALT+W 快捷键打开微信
     */
    public void openWeChat() {
        bot.keyPress(KeyEvent.VK_CONTROL);
        bot.keyPress(KeyEvent.VK_ALT);
        bot.keyPress(KeyEvent.VK_W);

        bot.keyRelease(KeyEvent.VK_CONTROL);
        bot.keyRelease(KeyEvent.VK_ALT);

        bot.delay(1000);
    }

    /**
     * 查找微信中好友名称
     *
     * @param name 好友/群名称
     */
    public void chooseFriends(String name) {
        Transferable text = new StringSelection(name);
        clip.setContents(text, null);
        bot.delay(1000);
        bot.keyPress(KeyEvent.VK_CONTROL);
        bot.keyPress(KeyEvent.VK_F);
        bot.keyRelease(KeyEvent.VK_CONTROL);

        bot.delay(1000);

        bot.keyPress(KeyEvent.VK_CONTROL);
        bot.keyPress(KeyEvent.VK_V);
        bot.keyRelease(KeyEvent.VK_CONTROL);

        bot.delay(2000);

        bot.keyPress(KeyEvent.VK_ENTER);

    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        Transferable text = new StringSelection(message);
        clip.setContents(text, null);
        bot.delay(1000);
        bot.keyPress(KeyEvent.VK_CONTROL);
        bot.keyPress(KeyEvent.VK_V);
        bot.keyRelease(KeyEvent.VK_CONTROL);
        bot.delay(1000);

        bot.keyPress(KeyEvent.VK_ENTER);

        bot.delay(1000);
        bot.keyPress(KeyEvent.VK_CONTROL);
        bot.keyPress(KeyEvent.VK_ALT);
        bot.keyPress(KeyEvent.VK_W);

        bot.keyRelease(KeyEvent.VK_CONTROL);
        bot.keyRelease(KeyEvent.VK_ALT);
    }
}
