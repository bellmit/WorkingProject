package com.smarthome.uploadyiyanlogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UploadyiyanlogsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadyiyanlogsApplication.class, args);
        System.out.println("翼眼系统定时上传日志程序启动成功");
    }

}
