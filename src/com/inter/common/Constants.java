package com.inter.common;

public class Constants {
	
	public static String url = "http://106.38.231.89/netease/news/list.html";
	
	public static String filename = "params.properties";
	
	/**
	 * ʱ�������
	 */
	public enum TimeType {
		/**
		 * 10λ����ʱ���
		 */
        TIMESTAMP_TEN(1),
        
        /**
         * yyyy-MM-dd HH:mm:ss
         */
        TIME(2);
        
        private final int value;

        //������Ĭ��Ҳֻ����private, �Ӷ���֤���캯��ֻ�����ڲ�ʹ��
        TimeType(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
	}
	
}
