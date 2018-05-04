package com.dai.daobuild.test.token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.dai.jigsaw.core.feature.test.TestSupport;
import com.dai.jigsaw.web.token.JwtToken;
import com.dai.jigsaw.web.token.Token;


public class TokenServiceTest extends TestSupport {

	@Resource
	private Token tokenManager;

	@Test
	public void test_token() {
		Map<String, String> info = new HashMap<String, String>();
		info.put("userid", "daidai");
		info.put("username", "呆呆");
		long time = 5;
		String token = tokenManager.create(info, time);
		System.out.println("生成token："+token);
		System.out.println("验证token："+tokenManager.validate(token));
		Map<String, String> tokenInfo = tokenManager.getInfo(token);
		for(String key : tokenInfo.keySet()){
			System.out.println("token-"+key+"："+tokenInfo.get(key));
		}
		try {
			Thread.currentThread().sleep(10*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("验证token："+tokenManager.validate(token));
	}


}
