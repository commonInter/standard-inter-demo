package com.inter.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AppKeySecretUtil {

	/**
	 * 使用HmacSHA1对签名进行加密
	 * @param appSecret
	 * @param baseString 参数拼成的字符串key+parm+secret
	 * @return
	 */
	public static String HmacSHA(String appSecret, String baseString) {
		String sign = null;
		try {
			byte keyBytes[] = appSecret.getBytes("UTF-8");
			SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(secretKey);
			sign = new String(Base64.encodeBase64(mac.doFinal(baseString.getBytes("UTF-8"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}

}
