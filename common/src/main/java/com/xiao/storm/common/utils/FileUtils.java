package com.xiao.storm.common.utils;

import java.io.File;
import java.io.FileInputStream;

public class FileUtils {

	/**
	 * 获取文件流
	 * @return
	 */
	public static FileInputStream  getFileInputStream(String path){
		FileInputStream fis = null;
		try{
			File pfile = new File(path);
			fis = new FileInputStream(pfile);
		}catch(Exception e){
			e.printStackTrace();
		}
		return fis;
	}
}
