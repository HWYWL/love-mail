package com.yi.love;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class LoveMailApplication implements CommandLineRunner {

    public static void main(String[] args) {
//        SpringApplication.run(LoveMailApplication.class, args);
        ApplicationContext contexto = new SpringApplicationBuilder(LoveMailApplication.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setVisible(true);
        });
    }
}
