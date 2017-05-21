package com.sdu.edu.bigdata.csdn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * CSDNApplication
 *
 * @author Allen Jin
 * @date 2017/05/19
 */
@SpringBootApplication
@EnableScheduling
public class CSDNApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CSDNApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CSDNApplication.class, args);

    }

}