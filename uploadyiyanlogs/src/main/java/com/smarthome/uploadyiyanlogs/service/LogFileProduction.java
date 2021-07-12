package com.smarthome.uploadyiyanlogs.service;

import com.jcraft.jsch.JSchException;
import com.smarthome.uploadyiyanlogs.config.BaseConfig;
import com.smarthome.uploadyiyanlogs.es.EsLogList;
import com.smarthome.uploadyiyanlogs.es.EsSearch;
import com.smarthome.uploadyiyanlogs.scp.ScpTransfer;
import com.smarthome.uploadyiyanlogs.sftp.SftpUtil;
import com.smarthome.uploadyiyanlogs.sql.mapper.LogMapper;
import com.smarthome.uploadyiyanlogs.pojo.OperationLog;
import com.smarthome.uploadyiyanlogs.util.CalendarUtils;
import com.smarthome.uploadyiyanlogs.util.GZUtil;
import com.smarthome.uploadyiyanlogs.util.ValCheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author:KUN
 * @Data:2021/7/2 14:54
 * @Description: 将日志数据写入文件
 * @Version:1.0
 */
@EnableScheduling
@Component// 2.开启定时任务
public class LogFileProduction {

    private static final Logger logger = LoggerFactory.getLogger(LogFileProduction.class);
    private final static String EOL = System.getProperty("line.separator");

    @Autowired
    private BaseConfig baseConfig;
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private EsLogList esLogList;

    //@Scheduled(cron = "0/10 * * * * ? ")  //每10秒执行一次
    @Scheduled(cron = "0 0/2 * * * ? ")  //每2分钟执行一次
    //@Scheduled(cron = "0 0 05 * * ?")  //每天5点
    public void producelogFile(){
        logger.info("翼眼登录&查询&操作，定时任务开始执行");
        String fileName = baseConfig.getFilePath()+"10800_ESURFING_SSOLOG_"+ CalendarUtils.getDate()
                +"_"+CalendarUtils.getLastDayDate()+"_D_"+"00_0001"+".DAT";
        logger.info("上传日志文件名：{}",fileName);
        File file = new File(fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        //写入操作日志List
        List<OperationLog> allOperationLogs = logMapper.getOperationLogs();
        if(allOperationLogs != null && allOperationLogs.size() != 0){
            writeYiYanLogForLine(file,allOperationLogs,"修改");
        }
        //写入查询日志List
        List<OperationLog> searchLogList = esLogList.getSearchLogList();
        if (searchLogList != null && searchLogList.size() != 0) {
            writeYiYanLogForLine(file,searchLogList,"查询");
        }
        //写入登录日志List
        List<OperationLog> loginLogList = esLogList.getLoginLogList();
        if (loginLogList != null && loginLogList.size() != 0) {
            writeYiYanLogForLine(file,loginLogList,"登录");
        }
        //生成VAL文件以及CHECK文件
        writeValCheck();
        //将DAT文件生成.gz文件
        GZUtil.compresserToGZ(file,baseConfig.getFilePath(),fileName.substring(fileName.lastIndexOf("/")+1));
        file.delete();
        /*//上传到sftp服务器
        try {
            SftpUtil sftp = new SftpUtil(baseConfig.getSftpname(),baseConfig.getSftppass(),baseConfig.getSftpip(),Integer.valueOf(baseConfig.getSftpport()));
            sftp.login();
            File uploadDir = new File(baseConfig.getFilePath());
            File[] files = uploadDir.listFiles();
            if(files != null && files.length != 0 ){
                for(File f:files){
                    sftp.upload(baseConfig.getSftpremotedir(),f.getPath());
                }
            }
            sftp.logout();
        } catch (Exception e) {
            logger.error("上传文件出错!!!{}",e);
            e.printStackTrace();
        }*/

        //scp发送日志文件到171服务器，之后再由171服务器发送到sftp服务器
        File uploadDir = new File(baseConfig.getFilePath());
        File[] files = uploadDir.listFiles();
        ScpTransfer scpTransfer = new ScpTransfer();
        if(files != null && files.length != 0 ){
            for(File f:files){
                if (f.getName().contains(CalendarUtils.getLastDayDate()+"_D")){
                    scpTransfer.scpUpload(f.getPath());
                }
            }
        }

    }

    //生成VAL文件以及CHECK文件
    public void writeValCheck(){
        try {
            String fileName = baseConfig.getFilePath()+"10800_ESURFING_SSOLOG_"+ CalendarUtils.getDate()
                    +"_"+CalendarUtils.getLastDayDate()+"_D_"+"00_0001"+".DAT";
            File logFile = new File(fileName);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");//注意月份是MM
            Date dataDate = simpleDateFormat.parse(CalendarUtils.getLastDayDate());
            Date fileHandleDate = simpleDateFormat.parse(CalendarUtils.getDate());
            long lineNum = Files.lines(Paths.get(logFile.getPath())).count();
            ValCheckUtils.writeVALFile(logFile, lineNum,dataDate,fileHandleDate);
            ValCheckUtils.writeCheckFile(logFile,true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //按行写文件，将操作日志list中的字符串，按行写入file_name文件中
    public static void writeYiYanLogForLine(File file, List<OperationLog> list,String logType)
    {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file,true));
            for ( OperationLog log: list ) {
                String authority = log.getUsername() == "admin" ? "管理员":"普通";
                String oneLog ="20003"+"\u0005"+
                        "家庭网络连接管理平台(翼眼平台)"+"\u0005"+
                        "10002"+"\u0005"+
                        "家庭网络事业部"+"\u0005"+
                        log.getUsername()+"\u0005"+
                        log.getIp()+"\u0005"+
                        log.getName()+"\u0005"+
                        "在职"+"\u0005"+
                        logType+"\u0005"+
                        log.getModule()+"\u0005"+  //操作对象
                        log.getFunction()+"\u0005"+  //操作对象描述
                        "成功"+"\u0005"+
                        log.getStartDate()+"\u0005"+
                        authority+"\u0005"+
                        "\u0005";
                logger.info(oneLog);
                writer.write(oneLog + EOL);//按行写文件，后面追加行分隔符EOL
            }
            //关闭流
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("翼眼{}日志，写入出错",logType);
        }finally {
            if ( writer != null ) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
