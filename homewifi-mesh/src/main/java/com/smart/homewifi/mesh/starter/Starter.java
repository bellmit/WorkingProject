package com.smart.homewifi.mesh.starter;

import com.smart.homewifi.mesh.gatewaystate.GatewayMesh;
import com.smart.homewifi.mesh.routerstate.RouterMesh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author:Z
 * @Date:2021/11/15 14:40
 * @Description: 项目启动时运行
 * @Version:1.0
 */
@Component          //注入服务
@Order(value = 12)  //设置优先级
public class Starter implements ApplicationRunner {
    @Autowired
    private RouterMesh routerMesh;
    @Autowired
    private GatewayMesh gatewayMesh;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Runnable runnable1 = new Runnable() {  //创建新的线程
            @Override
            public void run() {
                try {
                    routerMesh.getRouterMesh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable runnable2 = new Runnable() {  //创建新的线程
            @Override
            public void run() {
                try {
                    gatewayMesh.getGatewayMesh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        //thread1.start(); //启动线程
        thread2.start();
    }
}
