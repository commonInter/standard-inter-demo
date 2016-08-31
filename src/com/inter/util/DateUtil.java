package com.inter.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static Date parse(String date){
        SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_PATTERN);
        try {
            return sf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static void main(String[] args) {
		System.out.println(parse("2016-07-25 08:23:34").getTime()/1000);
	}
	
}
