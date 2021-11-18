package com.smart.homewifi.mesh.routerstate;

import org.springframework.stereotype.Component;

/**
 * @Author:Z
 * @Date:2021/11/15 13:49
 * @Version:1.0
 */
@Component
public class RouterMesh {

    public void getRouterMesh() throws InterruptedException {
        while(true){
            Thread.sleep(5000);
            System.out.println("routerupdate************************");
        }
    }



}
