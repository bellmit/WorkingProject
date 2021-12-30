package com.ruoyi.homewifi.task;


import com.jcraft.jsch.ChannelSftp;
import com.ruoyi.homewifi.config.BaseConfig;
import com.ruoyi.homewifi.dobj.LakeGiftDo;
import com.ruoyi.homewifi.dobj.LakeReportDo;
import com.ruoyi.homewifi.mapper.DataCityRateMapper;
import com.ruoyi.homewifi.redis.RedisUtils;
import com.ruoyi.homewifi.utils.DateFormatUtil;
import com.ruoyi.homewifi.utils.EmptyUtil;
import com.ruoyi.homewifi.utils.FileZipUtile;
import com.ruoyi.homewifi.utils.SftpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author:Z
 * @Data:2021/9/1 16:11
 * @Description: 数据湖下发数据文件定时数据入库
 * @Version:1.0
 */
@Component("lakeTask")
@EnableScheduling
public class ScheduledTask {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private BaseConfig baseConfig;
    @Autowired
    private DataCityRateMapper dataCityRateMapper;


    /** 使用多线程 配置*/
    private ThreadPoolExecutor pool;
    private static Integer threadNum = 1;
    public static Map<String, ScheduledExecutorService> excutor = new HashMap<>();
    private static final String timerSqlPhonName = "timerSqlPhon";


    private static ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static ThreadLocal<DateFormat> sdf = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };


    /**
     * 据湖下发的全屋wifi礼包数据&报告数据入Mysql
     */
    //@Scheduled(cron = "0 0 05 * * ?")  //每天5点
    //@Scheduled(fixedDelay=3000)
    public String readFileToEs() {
        try {
            logger.info("存储数据湖下发的{}号全屋wifi礼包数据&报告数据，开始执行!!!", DateFormatUtil.getLastDayDate(new Date(), "yyyyMMdd"));
            Long startTime = System.currentTimeMillis();
            pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum);

            //1.sftp获取文件到本地
            boolean ftp = donloadFileFTP();
            if(!ftp){
                logger.info("读取sftp文件出错!!");
            }
            //2.解压文件
            File testfile = new File(baseConfig.getLocaldir());
            int unNum = 0;
            if (testfile.listFiles().length != 0) {
                for (File f : testfile.listFiles()) {
                    boolean unBool = FileZipUtile.unGzipFile(f.toString());
                    if(unBool){
                        unNum++;
                    }
                }
            }else{
                logger.info("{}号,数据湖未下发文件", DateFormatUtil.getLastDayDate(new Date(), "yyyyMMdd"));
                return "";
            }
            logger.info("解压文件成功,本次解压{}个文件",unNum);
            //3、解析入库
            analysisFileToEs();
            Long endTime = System.currentTimeMillis();
            logger.info("解析文件入库成功,耗时{}ms",(endTime-startTime));
            return "true";
        }catch (Exception ex){
            ex.printStackTrace();
            return "解析文件出错!!";
        }
    }

    /**
     * sftp下载文件方法
     */
    private  boolean donloadFileFTP() {
        try {
            logger.info("{} sftp下载排障标签数据开始执行!!!", DateFormatUtil.getLastDayDate(new Date(), "yyyyMMdd"));
            //验证文件夹是否存在
            Integer fileNum = 0;
            SftpUtil sftp = new SftpUtil(baseConfig.getSftpname(), baseConfig.getSftppass(), baseConfig.getSftpip(), Integer.valueOf(baseConfig.getSftpport()));
            sftp.login();
            logger.info("链接sftp成功!");
            Vector<?> listFiles = sftp.listFiles(baseConfig.getSftpdir());
            if (listFiles != null) {
                for (int i = 0; i < listFiles.size(); i++) {
                    ChannelSftp.LsEntry ls = (ChannelSftp.LsEntry) listFiles.get(i);
                    //验证时间  大于 昨天0点 小于昨天24点
                    Long fileTime = Long.valueOf(ls.getAttrs().getATime())*1000;
                    Long fileStartTime = DateFormatUtil.getStartTime(0);
                    Long fileEndTime = DateFormatUtil.getEndTime(0);
                    if(ls != null && (ls.getFilename().contains("swifi_reportinfo") || ls.getFilename().contains("swifi_offer"))){
                        if(fileTime>fileStartTime && fileTime<fileEndTime){
                            if(ls.getFilename().contains("gz")){
                                if(RedisUtils.getFileName(ls.getFilename())!=null) {
                                    logger.info("文件{}已入库!",ls.getFilename());
                                    continue;
                                }
                                sftp.downloadFile(baseConfig.getSftpdir(), ls.getFilename(), baseConfig.getLocaldir() + ls.getFilename());
                                //下载完成不删除sftp文件
                                sftp.delete(baseConfig.getSftpdir(), ls.getFilename());
                                //String filename = baseConfig.getLocaldir() + ls.getFilename();
                                RedisUtils.saveFileName(ls.getFilename());
                                logger.info("{}文件下载成功!!!",ls.getFilename());
                                fileNum++;
                            }
                        }
                    }
                }
            }
            sftp.logout();
            logger.info("文件下载成功,本次下载{}条!!!",fileNum);
            return true;
        }catch (Exception ex){
            logger.error("下载文件出错!!!{}",ex);
            return false;
        }
    }

    /**
     * 完成潜客数据下载后解析数据到ES数据库
     */
    public void analysisFileToEs(){
        //logger.info("四率数据湖数据入库");
        File filename = new File(baseConfig.getLocaldir());
        if (filename.list().length != 0) {
            for (File f : filename.listFiles()) {
                if(f.getName().contains("swifi_offer")){
                    insertGiftData(f);
                }else if(f.getName().contains("swifi_reportinfo")){
                    insertReportData(f);
                }
            }
        }else{
            logger.info("数据湖下发四率入库文件不存在");
        }
    }

    public void insertGiftData(File f) {
        logger.info("数据湖礼包数据入库");
        pool.execute(new Runnable() {
            @Override
            public void run() {
                //读取文件 存es
                File file = new File(f.toString());
                if (file == null) {
                    logger.info("读取文件为空!!");
                    return;
                } else {
                    logger.info("文件大小:{}B,{}KB,{}MB", file.length(), file.length() / 1024, file.length() / 1048576, file.length() / 1073741824);
                }

                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException ex) {
                    br = null;
                    logger.error("读取文件出错,没有此文件:{}", f);
                    return;
                } catch (Exception ex) {
                    logger.error("读取文件出错!!");
                    ex.printStackTrace();
                    return;
                }
                String line;
                int Num = 0;
                int errNum = 0;
                ArrayList<LakeGiftDo> lakeGiftList = new ArrayList<>();
                try {
                    if (br != null) {

                        while ((line = br.readLine()) != null) {
                            try {
                                String[] oneGift = line.split("\u0005", -1);
                                if (oneGift.length < 6) {
                                    errNum++;
                                    continue;
                                }
                                String date = oneGift[0];
                                //java.sql.Date sealDate = new java.sql.Date(df.get().parse(date).getTime());
                                LakeGiftDo lakeGiftDo = new LakeGiftDo();
                                lakeGiftDo.setSealingDate(EmptyUtil.isEmpty(oneGift[0]) ? null : new java.sql.Date(df.get().parse(date).getTime()));
                                lakeGiftDo.setDeptId(EmptyUtil.isEmpty(oneGift[1]) ? null : oneGift[1]);
                                lakeGiftDo.setLakeCityId(EmptyUtil.isEmpty(oneGift[2]) ? null : oneGift[2]);
                                lakeGiftDo.setLakeAreaId(EmptyUtil.isEmpty(oneGift[3]) ? null : oneGift[3]);
                                lakeGiftDo.setGiftCode(EmptyUtil.isEmpty(oneGift[4]) ? null : oneGift[4]);
                                lakeGiftDo.setGiftOrderId(EmptyUtil.isEmpty(oneGift[5]) ? null : oneGift[5]);
                                lakeGiftList.add(lakeGiftDo);
                                Num++;
                                //一个文件发一次的时候报错：jdbc.exceptions.PacketTooBigException: Packet for query is too large
                                if (lakeGiftList.size() >= 1000) {
                                    dataCityRateMapper.insertLakeGiftList(lakeGiftList);
                                    lakeGiftList.clear();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        br.close();
                        //mysql入库
                        if (!lakeGiftList.isEmpty()) {
                            dataCityRateMapper.insertLakeGiftList(lakeGiftList);
                        }
                        logger.info("{},文件读取成功!!本次添加成功{}条,文件单行数据长度出错{}条", f.toString(), Num, errNum);
                    }
                    //添加完成删除文件
                    boolean deleteResult = file.delete();
                    logger.info("删除{}文件,{}", file.getName(), deleteResult);
                } catch (Exception ex) {
                    logger.error("按行读取文件错误,错误:{}", ex);
                }
            }
        });
    }

    public void insertReportData(File f) {
        logger.info("数据湖报告数据入库");
        pool.execute(new Runnable() {
            @Override
            public void run() {
                //读取文件 存es
                File file = new File(f.toString());
                if (file == null) {
                    logger.info("读取文件为空!!");
                    return;
                } else {
                    logger.info("文件大小:{}B,{}KB,{}MB", file.length(), file.length() / 1024, file.length() / 1048576, file.length() / 1073741824);
                }
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException ex) {
                    br = null;
                    logger.error("读取文件出错,没有此文件:{}", f);
                    return;
                } catch (Exception ex) {
                    logger.error("读取文件出错!!");
                    ex.printStackTrace();
                    return;
                }
                String line;
                int Num = 0;
                int errNum = 0;
                ArrayList<LakeReportDo> lakeReportList = new ArrayList<>();
                try {
                    if (br != null) {
                        while ((line = br.readLine()) != null) {
                            try {
                                String[] oneReport = line.split("\u0005", -1);
                                if (oneReport.length < 14) {
                                    logger.info(Arrays.toString(oneReport));
                                    errNum++;
                                    continue;
                                }
                                LakeReportDo lakeReportDo = new LakeReportDo();
                                lakeReportDo.setLakeOrderid(EmptyUtil.isEmpty(oneReport[0]) ? null : oneReport[0]);
                                lakeReportDo.setDayId(EmptyUtil.isEmpty(oneReport[1]) ? null : new java.sql.Date(df.get().parse(oneReport[1]).getTime()));
                                lakeReportDo.setDeptId(EmptyUtil.isEmpty(oneReport[2]) ? null : oneReport[2]);
                                lakeReportDo.setwProvId(EmptyUtil.isEmpty(oneReport[3]) ? null : oneReport[3]);
                                lakeReportDo.setLakeCityId(EmptyUtil.isEmpty(oneReport[4]) ? null : oneReport[4]);
                                lakeReportDo.setwCityId(EmptyUtil.isEmpty(oneReport[5]) ? null : oneReport[5]);
                                lakeReportDo.setwAreaId(EmptyUtil.isEmpty(oneReport[6]) ? null : oneReport[6]);
                                lakeReportDo.setSameArea(EmptyUtil.isEmpty(oneReport[7]) ? null : Integer.parseInt(oneReport[7]));
                                lakeReportDo.setEffectiveReport(EmptyUtil.isEmpty(oneReport[8]) ? null : Integer.parseInt(oneReport[8]));
                                lakeReportDo.setElinkChecked(EmptyUtil.isEmpty(oneReport[9]) ? null : Integer.parseInt(oneReport[9]));
                                lakeReportDo.setWifiChecked(EmptyUtil.isEmpty(oneReport[10]) ? null : Integer.parseInt(oneReport[10]));
                                lakeReportDo.setLakeShareChecked(EmptyUtil.isEmpty(oneReport[11]) ? null : Integer.parseInt(oneReport[11]));
                                lakeReportDo.setLakeShareMethod(EmptyUtil.isEmpty(oneReport[12]) ? null : Integer.parseInt(oneReport[12]));
                                lakeReportDo.setAaaPppoe(EmptyUtil.isEmpty(oneReport[13]) ? null : oneReport[13]);
                                lakeReportList.add(lakeReportDo);
                                Num++;
                                //一个文件发一次的时候报错：jdbc.exceptions.PacketTooBigException: Packet for query is too large
                                if (lakeReportList.size() >= 1000) {
                                    dataCityRateMapper.insertLakeReportList(lakeReportList);
                                    lakeReportList.clear();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        br.close();
                        //mysql入库
                        if (!lakeReportList.isEmpty()) {
                            dataCityRateMapper.insertLakeReportList(lakeReportList);
                        }
                        logger.info("{},文件读取成功!!本次添加成功{}条,文件单行数据长度出错{}条", f.toString(), Num, errNum);
                    }
                    //添加完成删除文件
                    boolean deleteResult = file.delete();
                    logger.info("删除{}文件,{}", file.getName(), deleteResult);
                } catch (Exception ex) {
                    logger.error("按行读取文件错误,错误:{}", ex);
                }
            }
        });
    }

    //TODO:小批量读取成功，但是前端读取超时

    /**
     * 解析本地数据文件并入库。
     */
    //@Scheduled(cron = "0 */1 * * * ?")
    public void excuteTimingTeskWithoutDownload() {
        try {
            Long startTime = System.currentTimeMillis();
            pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum);
            logger.info("{} 本地数据湖下发文件解析入库开始执行!!!", DateFormatUtil.getLastDayDate(new Date(), "yyyyMMdd"));
            //3、解析入库
            analysisFileToEs();
            Long endTime = System.currentTimeMillis();
            logger.info("解析文件入库成功,耗时{}ms",(endTime-startTime));
        }catch (Exception ex){
            logger.error("下载文件出错!!!{}",ex);
        }
    }
}
