package com.smart.homewifi.mesh.scp;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import com.smart.homewifi.mesh.utils.CalendarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;

/**
 * @Author:KUN
 * @Data:2021/7/12 10:18
 * @Description: scp传输文件
 * @Version:1.0
 */
public class ScpTransfer {
    //数据服务器的ip地址
    @Value("${spring.datatransport.sftp.ip}")
    private String dataServerIp;
    //数据服务器的用户名
    @Value("spring.datatransport.sftp.name")
    private String dataServerUsername;
    //数据服务器的密码
    @Value("spring.datatransport.sftp.pass")
    private String dataServerPassword;
    //数据服务器的目的文件夹
    @Value("spring.datatransport.sftpdir")
    private String dataServerDestDir;
    @Value("spring.datatransport.sftp.port")
    private Integer dataServerPort;
    //从远程到本地的保存路径
    //private String localDir = "D:\\上传文件的临时目录";

    private static final Logger logger = LoggerFactory.getLogger(ScpTransfer.class);

    //上传单个文件
    public void scpUploadFile(String localFile) throws Exception{
        //文件scp到数据服务器
        Connection conn = new Connection(dataServerIp,dataServerPort);
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

    //上传整个文件夹内的所有文件（不递归，默认不存在子文件夹）
    public void scpUploadDir(String localDir){
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
            SCPClient client = new SCPClient(conn);
            File uploadDir = new File(localDir);
            File[] files = uploadDir.listFiles();
            if(files != null && files.length != 0 ){
                for(File f:files){
                    if (f.getName().contains(CalendarUtils.getLastDayDate()+"_D")){
                        client.put(f.getPath(), dataServerDestDir); //本地文件scp到远程目录
                        logger.info("SCP上传{}文件",f.getName());
                    }
                }
            }
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件夹:{} scp到数据服务器时发生异常",localDir);
        }
        logger.info("scp文件结束");
    }

}
