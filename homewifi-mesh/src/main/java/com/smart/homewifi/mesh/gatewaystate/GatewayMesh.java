package com.smart.homewifi.mesh.gatewaystate;

import com.smart.homewifi.mesh.utils.TimeUtils;
import org.springframework.stereotype.Component;

/**
 * @Author:Z
 * @Data:2021/11/15 13:48
 * @Version:1.0
 */
@Component
public class GatewayMesh {

    public void getGatewayMesh() throws InterruptedException {
        /*while(true){
            Thread.sleep(5000);
            System.out.println("gateWayupdate----------------------------");
        }*/
        //查询ES所得数据量
        int dataNum = 0;
        //上传标志位
        int updloadFlag = 0;

        while(true){
            if(TimeUtils.isDayOfMonth(20)){
                dataNum = 0;
            }
            if(TimeUtils.isDayOfMonth(1) && updloadFlag ==0){
                //上传文件
                uploadFile();
            }
            //使用scroll条件查询时间排序
            int total = 0; //从结果中获取total
            if(total == 0){
                //reindex从线上gatewayonline动态库补充数据

            }
        }
    }

    /**
     * 每月一号没有上传文件的情况下就上传
     */
    public void uploadFile(){
        //1、查询网关ES库

        //2、分批写入文件

        //3、压缩文件

        //4、上传文件

        //5、删除原表、上传标志位置1

        //6、reindex一张新表、记录入库时间

        //7、reflesh
    }
}
