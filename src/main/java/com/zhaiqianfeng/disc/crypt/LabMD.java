/**
 * @author ZhaiQianfeng <zhaiqianfeng@163.com> 
 *
 * @webSite: http://www.zhaiqianfeng.com/blog/
 *
 * @gitHub:  https://github.com/zhaiqianfeng 
 */
package com.zhaiqianfeng.disc.crypt;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Hex;

public class LabMD {

	// 待处理的字符串
	private static String src = "com.zhaiqianfeng";

	public static void main(String[] args) {
		jdkmd5();
		jdkSHA1();
		jdkSHA2();
		jdkMAC();
	}

	// jdk md5
	public static void jdkmd5() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] mdRes = md.digest(src.getBytes());
			String md5Base64 = Base64.getEncoder().encodeToString(mdRes);
			// 使用jdk8自带的base64编码显示
			System.out.println("jdk md5 base64编码显示：:" + md5Base64);

			String md5BCHex = Hex.toHexString(mdRes);
			// 使用BC来转换十六进制来显示
			System.out.println("jdk md5 Hex显示:" + md5BCHex);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	// jdk sha1
	public static void jdkSHA1() {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte[] mdRes = md.digest(src.getBytes());
			String shaBase64 = Base64.getEncoder().encodeToString(mdRes);
			System.out.println("\r\njdk sha base64编码显示：" + shaBase64);
			String shaBCHex = Hex.toHexString(mdRes);

			System.out.println("jdk sha Hex显示：" + shaBCHex);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	// jdk sha2
	public static void jdkSHA2() {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] mdRes = md.digest(src.getBytes());
			String shaBase64 = Base64.getEncoder().encodeToString(mdRes);
			System.out.println("\r\njdk sha-512 base64编码显示：" + shaBase64);
			String shaBCHex = Hex.toHexString(mdRes);

			System.out.println("jdk sha-512 Hex显示：" + shaBCHex);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	// jdk mac
	public static void jdkMAC() {
		try {
			//产生秘钥
			KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacMD5");
			SecretKey secretKey=keyGenerator.generateKey();
			byte[] key=secretKey.getEncoded();
			
			//对原始秘钥进行特性处理
			SecretKey restoreSecretKey=new SecretKeySpec(key,"HmacMD5");
			Mac mac=Mac.getInstance(restoreSecretKey.getAlgorithm());
			//设置MAC秘钥
			mac.init(restoreSecretKey);
			//编码
			byte[] macRes=mac.doFinal(src.getBytes());
			
			System.out.println("\r\njdk HmacMD5 key:"+Hex.toHexString(key)+"\r\njdk HmacMD5 result:"+Hex.toHexString(macRes));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}
		
	}

}
