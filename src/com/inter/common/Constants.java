package com.inter.common;

public class Constants {
	
	public static String url = "http://106.38.231.89/netease/news/list.html";
	
	public static String filename = "params.properties";
	
	/**
	 * 时间戳类型
	 */
	public enum TimeType {
		/**
		 * 10位数字时间戳
		 */
        TIMESTAMP_TEN(1),
        
        /**
         * yyyy-MM-dd HH:mm:ss
         */
        TIME(2);
        
        private final int value;

        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        TimeType(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
	}
	
}
