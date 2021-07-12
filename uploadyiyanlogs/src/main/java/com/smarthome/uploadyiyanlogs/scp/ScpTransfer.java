package com.smarthome.uploadyiyanlogs.scp;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author:KUN
 * @Data:2021/7/12 10:18
 * @Description: scp传输文件
 * @Version:1.0
 */
public class ScpTransfer {
    //数据服务器的ip地址
    private String dataServerIp = "10.251.16.171";
    //数据服务器的用户名
    private String dataServerUsername = "qoslog";
    //数据服务器的密码
    private String dataServerPassword = "vRPFNxhzroXFBT5m0801";
    //数据服务器的目的文件夹
    private String dataServerDestDir = "/data/yiyanlogs/";
    //从远程到本地的保存路径
    //private String localDir = "D:\\上传文件的临时目录";

    private static final Logger logger = LoggerFactory.getLogger(ScpTransfer.class);

    public void scpUpload(String localFile){
        //文件scp到数据服务器
        Connection conn = new Connection(dataServerIp,22);
        logger.info("开始scp文件");
        try {
            conn.connect();
            //服务器账号密码
            boolean isAuthenticated = conn.authenticateWithPassword(dataServerUsername, dataServerPassword);
            if (isAuthenticated == false){
                throw new IOException("Authentication failed.文件scp到数据服务器时发生异常");
            }
            //创建连接
            //SCPClient client = conn.createSCPClient();
            SCPClient client = new SCPClient(conn);
            client.put(localFile, dataServerDestDir); //本地文件scp到远程目录
            //client.get(dataServerDestDir + "00审计.zip", localDir);//远程的文件scp到本地目录
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件:{} scp到数据服务器时发生异常",localFile);
        }
        logger.info("scp文件结束");
    }

}
