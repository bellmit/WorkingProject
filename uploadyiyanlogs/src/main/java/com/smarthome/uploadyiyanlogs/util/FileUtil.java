package com.smarthome.uploadyiyanlogs.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtil {
	public static String getFileMD5(File file){
		String md5 = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			md5 = DigestUtils.md5Hex(fileInputStream);
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return md5;
	}
	public static void fileRename(File oldFile,File newFile){
		oldFile.renameTo(newFile);
	}
}
