package com.inter.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class FileUtil {
	
	/**
	 * 得到配置文件的路径
	 * @param filename  文件名
	 * @return
	 * @throws IOException
	 */
	public String getParamsPath(String filename) throws IOException {
        URL url = this.getClass().getClassLoader().getResource("");
        if(url != null) {
        	return url.getPath() + filename;
        }
		return "";
	}
	
	public static void main(String[] args) throws IOException {
		FileUtil f = new FileUtil();
		String filepath = f.getParamsPath("params.properties");
		
		Properties prop = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filepath));
			prop.load(in);
			Set<Object> keys = prop.keySet();

			Iterator<Object> it = keys.iterator();
			while(it.hasNext()) {
				Object obj = it.next();
				System.out.println(obj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
