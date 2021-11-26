package com.smart.homewifi.mesh.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 *  文件压缩工具类
 * author:yangjun
 * Date:2019/12/25
 */
public class FileZipUtile {
    private static final Logger logger = LoggerFactory.getLogger(FileZipUtile.class);
    private static final String zipType = ".gz";
    /**
     * 解压文件
     * @param sourcedir  文件路径
     * @return
     */
    public static boolean unGzipFile(String sourcedir) {
//        String sourcedir = "D:\\tmp\\2019052099_34_0101.gz"; // 压缩包路径
        String ouputfile = ""; //写到指定的文件中
        try {
            logger.info("解压文件开始执行!!!");
            long stTime = System.currentTimeMillis();
            //建立gzip压缩文件输入流
            FileInputStream fin = new FileInputStream(sourcedir);
            //建立gzip解压工作流
            GZIPInputStream gzin = new GZIPInputStream(fin);
            //建立解压文件输出流
            ouputfile = sourcedir.substring(0, sourcedir.lastIndexOf('.'));
            FileOutputStream fout = new FileOutputStream(ouputfile + ".txt");
            int num;
            byte[] buf = new byte[1024];
            while ((num = gzin.read(buf, 0, buf.length)) != -1) {
                fout.write(buf, 0, num);
            }
            gzin.close();
            fout.close();
            fin.close();
            //解压完成  删除文件
            File file = new File(sourcedir);
            file.delete();
            long endTime = System.currentTimeMillis();
            logger.info("解压文件结束,耗时{}ms", endTime - stTime);
            return true;
        } catch (Exception ex) {
            logger.info("解压文件"+sourcedir+"执行出错,错误:" + ex);
            return false;
        }
    }




    public static void main(String[] args) throws Exception {
        //System.out.println(GzipFile("D:\\3326开启但是b01没开启网关.xlsx","相见时难别亦难，东风无力百花残"));
        //unZip("D:\\3326开启但是b01没开启网关.xlsx.gz","D:","相见时难别亦难，东风无力百花残");
        GzipFile("F:\\Java\\IdeaProjects\\tmp\\mesh\\uploadPath\\我是你爸.txt","");

    }


    /**
     * @param source 原始文件路径
     * @param dest 解压路径
     * @param password 解压文件密码(可以为空)
     */
    public static void unZip(String source, String dest, String password) {
        try {
            File zipFile = new File(source);
            ZipFile zFile = new ZipFile(zipFile); // 首先创建ZipFile指向磁盘上的.zip文件
            zFile.setFileNameCharset("GBK");

            File destDir = new File(dest); // 解压目录
            if (!destDir.exists()) { // 目标目录不存在时，创建该文件夹
                destDir.mkdirs();
            }
            if (zFile.isEncrypted()) {
                zFile.setPassword(password.toCharArray()); // 设置密码
            }

            zFile.extractAll(dest); // 将文件抽出到解压目录(解压)
            List< net.lingala.zip4j.model.FileHeader > headerList = zFile.getFileHeaders();
            List<File> extractedFileList = new ArrayList<File>();
            for (FileHeader fileHeader: headerList) {
                if (!fileHeader.isDirectory()) {
                    extractedFileList.add(new File(destDir, fileHeader.getFileName()));
                }
            }
            File[] extractedFiles = new File[extractedFileList.size()];
            extractedFileList.toArray(extractedFiles);
            for (File f: extractedFileList) {
                System.out.println(f.getAbsolutePath() + "文件解压成功!");
            }

        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
    /**
     * 压缩文件
     * @param oriFileUrl 本地文件地址
     * @param miyue 压缩密码
     */
    public static String GzipFile(String oriFileUrl , String miyue) {
        try {
            //创建压缩文件
            ZipFile zipFile = new ZipFile(oriFileUrl+zipType);
            ArrayList<File> files = new ArrayList<>();
            files.add(new File(oriFileUrl));

            //设置压缩文件参数
            ZipParameters parameters = new ZipParameters();
            //设置压缩方法
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

            //设置压缩级别
            //DEFLATE_LEVEL_FASTEST     - Lowest compression level but higher speed of compression
            //DEFLATE_LEVEL_FAST        - Low compression level but higher speed of compression
            //DEFLATE_LEVEL_NORMAL  - Optimal balance between compression level/speed
            //DEFLATE_LEVEL_MAXIMUM     - High compression level with a compromise of speed
            //DEFLATE_LEVEL_ULTRA       - Highest compression level but low speed
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            //设置压缩文件加密功能开启或关闭
            //parameters.setEncryptFiles(true);
            parameters.setEncryptFiles(false);

            //设置加密方法
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            //设置aes加密强度
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            //设置密码
            parameters.setPassword(miyue.toCharArray());

            //添加文件到压缩文件
            zipFile.setFileNameCharset("gbk");
            zipFile.addFiles(files, parameters);
            return oriFileUrl+zipType;
        } catch (ZipException e) {
            logger.error("压缩文件出错!!!{}",e);
            e.printStackTrace();
            return null;
        }
    }



}
