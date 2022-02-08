package com.yi.love.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yi.love.config.Config;
import com.yi.love.model.Forecast;
import com.yi.love.model.Weather;
import com.yi.love.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务
 *
 * @author YI
 * @date 2018-11-20 16:54:46
 */
@Slf4j
@Component
public class ScheduledTasks implements PageProcessor {
    private final Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Accept-Language", "zh-HK,zh-CN;q=0.9,zh;q=0.8")
            .addHeader("Cache-Control", "max-age=0")
            .addHeader("Connection", "keep-alive")
            .addHeader("Cookie", "my_city=%E5%B9%BF%E5%B7%9E; my_province=%E5%B9%BF%E4%B8%9C; UM_distinctid=1675849d3db5f-0de2eeb0a06f18-5c10301c-1fa400-1675849d3dc1630; CNZZDATA30047636=cnzz_eid%3D1999472196-1543372293-https%253A%252F%252Fwww.baidu.com%252F%26ntime%3D1543372293; CNZZDATA3473983=cnzz_eid%3D1965810081-1543370614-https%253A%252F%252Fwww.baidu.com%252F%26ntime%3D1543370614; CNZZDATA1273458473=1278741387-1543372587-https%253A%252F%252Fwww.baidu.com%252F%7C1543372587; fc_aid=9")
            .addHeader("DNT", "1")
            .addHeader("Host", "www.xzw.com")
            .addHeader("Referer", "https://www.xzw.com/astro/taurus/")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36")
            .addHeader("Upgrade-Insecure-Requests", "1");

    /**
     * 每天早上八点45分自动执行一次
     */
    @Scheduled(cron = "0 15 0,8 ? * *")
    public void cronTask() {
        Spider.create(new ScheduledTasks()).addUrl(Config.HOROSCOPE).addPipeline(new ConsolePipeline()).run();

        log.info("cron 现在时间是 {}", DateUtil.today());
    }

    @Override
    public void process(Page page) {
        StringBuffer buffer = new StringBuffer();
        List<String> fortune = new ArrayList<>();
        // 星座
        String constellation = page.getHtml().xpath("//*[@class='c_main']/dl/dd/h4/text()").toString();

        List<String> li = page.getHtml().xpath("//*[@class='c_main']/dl/dd/ul/li").all();
        for (int i = 5; i < li.size() - 1; i++) {
            fortune.add(page.getHtml().xpath("//*[@class='c_main']/dl/dd/ul/li[" + i + "]/label/text()").toString() + page.getHtml().xpath("//*[@class='c_main']/dl/dd/ul/li[" + i + "]/text()").toString());
        }

        String synthesize = page.getHtml().xpath("//*[@class='c_cont']/p/span/text()").toString();

        String result = HttpUtil.get("http://t.weather.sojson.com/api/weather/city/101280101", CharsetUtil.CHARSET_UTF_8);

        Weather weather = JSONUtil.toBean(result, Weather.class);
        List<Forecast> list = weather.getData().getForecast();

        buffer.append("今天是").append(DateUtil.today()).append(StrUtil.COMMA).append(DateUtil.dayOfWeekEnum(DateUtil.date()).toChinese()).append(StrUtil.CRLF).append(StrUtil.CRLF);

        buffer.append("首先今天好也想你喔（づ￣3￣）づ╭❤～，然后我就要来播送天气预报了！！" + StrUtil.CRLF + StrUtil.CRLF);

        if (list == null || list.isEmpty()) {
            buffer.append("好讨厌，今天的天气数据没有更新，不能给我家的抱抱预报天气了[○･｀Д´･ ○]");
        } else {
            Forecast forecast = list.get(0);

            buffer.append("今天最:");
            buffer.append(forecast.getHigh());
            buffer.append(",最");
            buffer.append(forecast.getLow());
            buffer.append(StrUtil.CRLF);
            buffer.append(StrUtil.CRLF);

            buffer.append(forecast.getType());
            buffer.append(",风力").append(forecast.getFl());
            buffer.append(",空气质量是").append(weather.getData().getQuality());

            buffer.append(StrUtil.CRLF);
            buffer.append(StrUtil.CRLF);

            buffer.append("今天将在 ").append(forecast.getSunset()).append(" 太阳会缓缓落下,我会在家做好饭等你哟！");

            buffer.append(StrUtil.CRLF);
            buffer.append(StrUtil.CRLF);

            // 新作运势
            buffer.append(constellation);
            buffer.append(StrUtil.CRLF);
            buffer.append(StrUtil.CRLF);

            fortune.forEach(e -> {
                buffer.append(e);
                buffer.append(StrUtil.CRLF);
            });

            buffer.append(StrUtil.CRLF);
            buffer.append("综合运势: ").append(synthesize);

            buffer.append(StrUtil.CRLF);
            buffer.append(StrUtil.CRLF);

            buffer.append("最后").append(forecast.getNotice());

            buffer.append(StrUtil.CRLF);
            buffer.append(StrUtil.CRLF);
            buffer.append("爱你٩(๑>◡<๑)۶傻宝宝！！！");
            buffer.append(StrUtil.CRLF);
            buffer.append("很晚了睡觉觉吧，❥(ゝω・✿ฺ)！！！");
        }

        // 发送邮件
//        SpringUtil.getBean(EmailService.class).sendEmail(buffer.toString());
        // 发送微信
        SpringUtil.getBean(WeChatService.class).sendMsgNow("雪妹妹", buffer.toString());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
