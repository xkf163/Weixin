package com.xkf.util;

import java.util.Arrays;

public class CheckUtil {
	public static final String  token= "xkf";
	public static boolean checkSignature(String signature,String timestamp,String nonce) {
		String[] arr = new String[]{token,timestamp,nonce};
		//排序
		Arrays.sort(arr);
		
		//拼凑字符串
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		
		//sha1加密
		SHA1 sha1 = new SHA1();
		
//		System.out.println("token验证：");
//		System.out.println(content);
		
		String temp=sha1.hex_sha1(content.toString());
		
//		System.out.println("temp:"+temp);
//		System.out.println("signature:"+signature);
//		System.out.println(temp.equals(signature));
		
		//比较
		return temp.equals(signature);
		
		
		
	}
}
