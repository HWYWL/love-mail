# love-mail
爱的邮件，定时给我家妹子发一份

### 先看效果
![](https://i.imgur.com/qVYCVG5.jpg)

### 分析源码

其实源码很简单，一个Scheduled定时器就可以搞定了，天气的数据来自第三方接口
```java
@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 每天中午12点自动执行一次
     */
    @Scheduled(cron = "0 0 12 1/1 * ?")
    public void cronTask() throws MessagingException {
        StringBuffer buffer = new StringBuffer();
        Date date = new Date();

        String result = HttpUtil.get("http://t.weather.sojson.com/api/weather/city/101280101", CharsetUtil.CHARSET_UTF_8);

        Weather weather = JSONUtil.toBean(result, Weather.class);
        List<Forecast> list = weather.getData().getForecast();

        buffer.append("今天是" + dateFormat.format(date) + StrUtil.COMMA + DateUtil.dayOfWeekEnum(date).toChinese() + StrUtil.CRLF + StrUtil.CRLF);

        buffer.append("首先今天好也想你喔（づ￣3￣）づ╭❤～，然后我就要来播送天气预报了！！" + StrUtil.CRLF + StrUtil.CRLF);

        if (list == null || list.isEmpty()){
            buffer.append("好讨厌，今天的天气数据没有更新，不能给我家的抱抱预报天气了[○･｀Д´･ ○]");
        }else {
            Forecast forecast = list.get(0);

            buffer.append("今天最:");
            buffer.append(forecast.getHigh());
            buffer.append(",最");
            buffer.append(forecast.getLow());
            buffer.append(StrUtil.CRLF);
            buffer.append(StrUtil.CRLF);

            buffer.append(forecast.getType());
            buffer.append(",风力" + forecast.getFl());
            buffer.append(",空气质量是" + weather.getData().getQuality());

            buffer.append(StrUtil.CRLF);
            buffer.append(StrUtil.CRLF);

            buffer.append("今天将在 " + forecast.getSunset() + " 太阳会缓缓落下,我会在家做好饭等你哟！");

            buffer.append(StrUtil.CRLF);
            buffer.append(StrUtil.CRLF);

            buffer.append("最后" + forecast.getNotice());

            buffer.append(StrUtil.CRLF);
            buffer.append(StrUtil.CRLF);
            buffer.append("爱你٩(๑>◡<๑)۶傻宝宝！！！");
        }


        OhMyEmail.subject("男朋友的日常问候(^.^)")
                .from("亲爱的傻宝宝:(^.^)")
                .to(Config.to)
                .text(buffer.toString())
                .send();

        log.info("cron 现在时间是 {}", dateFormat.format(new Date()));
    }
}
```

### 部署使用
![](https://i.imgur.com/QWK378x.jpg)

把邮箱配置信息改为你的账号密码，然后发送人也改一下，就好了，打成jar包部署
如果用QQ游戏需要第三方客户端授权码，就是**密码换为第三方授权码**！！！

### 问题建议

- 联系我的邮箱：ilovey_hwy@163.com
- 我的博客：http://www.hwy.ac.cn
- GitHub：https://github.com/HWYWL