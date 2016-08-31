package com.inter.test;

import com.inter.common.Constants;
import com.inter.service.ServerConnect;

public class TestMain {
	
	public static void main(String[] args) {
		ServerConnect sconnect = new ServerConnect();
		String result = sconnect.getPostRequestResult(Constants.url);
		System.out.println(result);
	}
	
}
