package com.smarthome.uploadyiyanlogs.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;

public class GZUtil {
	private final static int BUFFER = 1048576;

	public static File compresserToGZ(File file,String outPutPath,String fileName){
        File outPutFile = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        GZIPOutputStream gzp = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis,BUFFER);
            outPutFile = new File(outPutPath+"/"+fileName+".gz");
            fos = new FileOutputStream(outPutFile);
            gzp = new GZIPOutputStream(fos);
            int count;  
            byte data[] = new byte[BUFFER];  
            while ((count = bis.read(data, 0, BUFFER)) != -1) {  
                gzp.write(data, 0, count);  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(gzp != null){
                    gzp.finish();
                    gzp.flush();
                    gzp.close();
                }
                if(fos != null)  fos.close();
                if(bis != null)  bis.close();
                if(fis != null)  fis.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outPutFile;
    }
}
