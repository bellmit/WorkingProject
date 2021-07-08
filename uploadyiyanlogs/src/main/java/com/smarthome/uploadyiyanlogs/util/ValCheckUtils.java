package com.smarthome.uploadyiyanlogs.util;
import java.io.*;
import java.util.Date;

/**
 * @Author:KUN
 * @Data:2021/7/6 14:58
 * @Description: 生成VAL文件以及CHECK文件
 * @Version:1.0
 */
public class ValCheckUtils {

    /**
     * 生成.VAL文件
     * 每个数据文件对应一个VAL文件，用于记录文件信息
     */
    public static File writeVALFile(File f, long rowSize, Date dataDate, Date fileHandleDate) throws IOException {
        int separater = 5;
        char cSeparater = (char)separater;
        FileOutputStream fos;
        String fpath = f.getCanonicalPath();
        File file = new File(fpath.substring(0,fpath.indexOf(".")) +".VAL");
        try {
            fos = new FileOutputStream(file);
            OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw=new BufferedWriter(osw);
            String content = f.getName()+cSeparater+rowSize+cSeparater+f.length()+cSeparater+ FileUtil.getFileMD5(f)+cSeparater+ DateFormatUtil.getDayDate(dataDate, "yyyyMMdd");
            content += cSeparater+"D"+ cSeparater + DateFormatUtil.getDayDate(fileHandleDate, "yyyyMMddHHmmss");
            bw.write(content+"\t\n");
            bw.close();
            osw.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 生成.CHECK文件
     * 一批文件对应一个CHECK文件
     */
    public static File writeCheckFile(File f,boolean isFinall) throws IOException {
        FileOutputStream fos;
        String fpath = f.getCanonicalPath();
        String filename = fpath.substring(0, fpath.indexOf(".")-4)+"0001";
        File file = new File(filename+".CHECK.TMP");
        int separater = 5;
        char cSeparater = (char)separater;
        try {
            fos = new FileOutputStream(file,true);
            OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw=new BufferedWriter(osw);
            String content = f.getName()+cSeparater+"D";
            bw.write(content+"\r\n");
            bw.close();
            osw.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //最后一次，后缀改为check
        if(isFinall){
            File checkfile = new File(filename+".CHECK");
            file.renameTo(checkfile);
        }
        return file;
    }
}
