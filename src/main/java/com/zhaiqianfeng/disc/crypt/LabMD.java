/**
 * @author ZhaiQianfeng <zhaiqianfeng@163.com> 
 *
 * @webSite: http://www.zhaiqianfeng.com/blog/
 *
 * @gitHub:  https://github.com/zhaiqianfeng 
 */
package com.zhaiqianfeng.disc.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.bouncycastle.util.encoders.Hex;

public class LabMD {

	// 待处理的字符串
	private static String src = "com.zhaiqianfeng";

	public static void main(String[] args) {
		jdkmd5();
		jdkSHA();
		jdkMAC();
	}

	// jdk md5
	public static void jdkmd5() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] mdRes = md.digest(src.getBytes());
			String md5Base64 = Base64.getEncoder().encodeToString(mdRes);
			//使用jdk8自带的base64编码显示
			System.out.println("jdk md5 base64编码显示：:" + md5Base64);
			
			String md5BCHex= Hex.toHexString(mdRes);
			//使用BC来转换十六进制来显示
			System.out.println("jdk md5 Hex显示:" +md5BCHex);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	//jdk sha
	public static void jdkSHA(){
		try {
			MessageDigest md=MessageDigest.getInstance("SHA");
			byte[] mdRes=md.digest(src.getBytes());
			String shaBase64=Base64.getEncoder().encodeToString(mdRes);
			System.out.println("jdk sha base64编码显示："+shaBase64);
			String shaBCHex=Hex.toHexString(mdRes);
			
			System.out.println("jdk sha Hex显示："+shaBCHex);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	//jdk mac
	public static void jdkMAC(){
//		try {
//			MessageDigest md=MessageDigest.getInstance("MAC");
//			byte[] mdRes=md.digest(src.getBytes());
//			String shaBase64=Base64.getEncoder().encodeToString(mdRes);
//			System.out.println("jdk sha base64编码显示："+shaBase64);
//			String shaBCHex=Hex.toHexString(mdRes);
//			
//			System.out.println("jdk sha Hex显示："+shaBCHex);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
	}

}
