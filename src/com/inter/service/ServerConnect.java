package com.inter.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inter.common.Constants;
import com.inter.util.AppKeySecretUtil;
import com.inter.util.DateUtil;
import com.inter.util.FileUtil;
import com.inter.util.HttpClientUtil;

public class ServerConnect {
	
	/**
	 * 获取Post请求结果
	 * @param url
	 * @param params
	 * @return
	 */
	public String getPostRequestResult(String url) {
		String result = HttpClientUtil.postData(url, getRequestParams(Constants.filename, true));
		
		if(StringUtils.isNotBlank(result)) {
			try {
				JSONObject jsonObj = JSON.parseObject(result);
				String code = jsonObj.getString("code");
				if(StringUtils.isBlank(code) || (StringUtils.isNotBlank(code) && !code.equals("200"))) {
					result = HttpClientUtil.postData(url, getRequestParams(Constants.filename, false));
				}
			} catch (Exception e) {
				return "";
			}
		}
		
		return result;
	}
	
	/**
	 * 根据配置文件获取请求参数
	 * @param filename
	 * @return
	 */
	public Map<String, String> getRequestParams(String filename, boolean whetherSecret) {
		Map<String, String> params = new HashMap<String, String>();
		
		Properties prop = new Properties();
		try {
			FileUtil fileUtil = new FileUtil();
			String filepath = fileUtil.getParamsPath("params.properties");
			InputStream in = new BufferedInputStream(new FileInputStream(filepath));
			prop.load(in);
			
			params.put("appKey", prop.getProperty("appKey"));
			
			String timestamp = "";
			String lasttime = prop.getProperty("lastTime");
			if(StringUtils.isNotBlank(lasttime)) {
				timestamp = String.valueOf(DateUtil.parse(lasttime).getTime()/1000);
				params.put("lastTimestamp", timestamp);
			}
			String type = prop.getProperty("interfaceType");
			params.put("interfaceType", type);
			
			String app_secret = prop.getProperty("appSecret");
			if(StringUtils.isNotBlank(app_secret) && whetherSecret) {
				String content = "lastTimestamp=" +timestamp + "&interfaceType=" + type;
				String sign = AppKeySecretUtil.HmacSHA(app_secret, content);
				params.put("sign", sign);
				System.out.println(sign);
			} else {
				params.put("sign", prop.getProperty("sign"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}
	
	public String getPostRequestResult(String url, Map<String, String> params) {
		String result = HttpClientUtil.postData(url, params);
		return result;
	}

}
