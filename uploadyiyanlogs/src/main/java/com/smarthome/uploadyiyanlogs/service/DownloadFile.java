package com.smarthome.uploadyiyanlogs.service;
import com.smarthome.uploadyiyanlogs.util.DateFormatUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * @Author:郑GH
 * @Data:2021/7/7 11:06
 * @Description: 从101上传文件到171服务器（暂时没用到）
 * @Version:1.0
 */
@RestController
@RequestMapping("/download")
public class DownloadFile {

    private static final Logger logger = LoggerFactory.getLogger(DownloadFile.class);

    @RequestMapping("/hello")
    public String aa(){
        String aa = "下载文件程序!";
        logger.info(aa);
        return aa;
    }

@RequestMapping("/getfile")
public void getfile(String fileName,String type, HttpServletResponse response) {
        logger.info("文件 " + fileName + " 正在被171服务器申请下载");

        String filePath = getType(type) + fileName;
        BufferedInputStream buffInputStream = null;
        OutputStream outputStream = null;
        try {
        buffInputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
        outputStream = response.getOutputStream();
        byte[] buff = new byte[1024 * 1024]; //如果是稍微大的文件，这里配置的大一些
        int len = 0;
        while ((len = buffInputStream.read(buff)) > 0) {
        //把文件流写入到response的输出流中，供请求端请求
        outputStream.write(buff, 0, len);
        outputStream.flush();
        }
        logger.info("文件 " + fileName + " 被171服务器下载完毕！");

        // 当.CHECK文件被下载完成时，将相同前缀名的.DAT .DAT.gz .VAL .CHECK文件都移到缓存文件夹
        if (filePath.endsWith(".CHECK")&&fileName.contains("GATEWAY_SSOLOG")) {
        logger.info("开始移动被下载完成的派单文件至缓存文件夹");
        String filePathDAT = filePath.substring(0, filePath.indexOf(".CHECK")) + ".DAT";
        String filePathGZ = filePathDAT + ".gz";
        String filePathVAL = filePath.substring(0, filePath.indexOf(".CHECK")) + ".VAL";
        String backpath = "/data1/backfiletmp/"+ DateFormatUtil.getDayDate(new Date(), "yyyyMMdd");
        File localDirCache3File = new File(backpath);
        if (!localDirCache3File.exists()) {
        localDirCache3File.mkdirs();
        }
        // 移动至缓存
        FileUtils.moveFileToDirectory(new File(filePathGZ), localDirCache3File, false);
        FileUtils.moveFileToDirectory(new File(filePathVAL), localDirCache3File, false);
        FileUtils.moveFileToDirectory(new File(filePath), localDirCache3File, false);
        logger.info("移动被下载完成的文件至缓存文件夹成功！");
        }

        } catch (IOException e) {
        logger.info("文件 " + filePath + " 写入http流失败", e);
        } finally {
        try {
        if (buffInputStream != null) {
        buffInputStream.close();
        }
        } catch (IOException e) {
        e.printStackTrace();
        }
        try {
        if (outputStream != null) {
        outputStream.close();
        }
        } catch (Exception e) {
        e.printStackTrace();
        }
        }

        }

public String getType(String type){
        if (type==null) return "";
        if ("manageap".equals(type)) return "/opt/backmanageapdoc/"+DateFormatUtil.getDayDate(new Date(),"yyyyMMdd")+"/";
        if ("elinkap".equals(type)) return "/opt/backelinkapdoc/"+DateFormatUtil.getDayDate(new Date(),"yyyyMMdd")+"/";
        if ("ssolog".equals(type)) return "/data1/filetmp/";
        if ("devstandard".equals(type)) return "/opt/backdevstandard/"+DateFormatUtil.getDayDate(new Date(),"yyyyMMdd")+"/";
        if ("e8cbink".equals(type)) return "/opt/backe8cbinkdoc/"+DateFormatUtil.getDayDate(new Date(),"yyyyMMdd")+"/";
        if ("noelink".equals(type)) return "/data1/backnoelinkdoc/"+DateFormatUtil.getDayDate(new Date(),"yyyyMMdd")+"/";
        return "";
        }



        }