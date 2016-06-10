/**
 * @author ZhaiQianfeng <zhaiqianfeng@163.com> 
 *
 * @webSite: http://www.zhaiqianfeng.com/blog/
 *
 * @gitHub:  https://github.com/zhaiqianfeng 
 */
package com.zhaiqianfeng.disc.crypt;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Hex;

/**
 * 对称加密算法
 */
public class LabSymmetricCipher {
	// 待处理的字符串
	private static String src = "com.zhaiqiafneng";

	public static void main(String[] args) {
		jdkDES();
		jdk3DES();
		jdkAES();
	}

	// jdk DES
	public static void jdkDES() {
		try {
			// 生成Key
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
			keyGenerator.init(56);
			Key secretKey = keyGenerator.generateKey();
			byte[] byteKey = secretKey.getEncoded();

			// 构建规范秘钥，获取规范的Key有两种方式
			// 方式一
			// DESKeySpec dESKeySpec=new DESKeySpec(byteKey);
			// SecretKeyFactory factory=SecretKeyFactory.getInstance("DES");
			// Key dESKey=factory.generateSecret(dESKeySpec);
			//
			// 方式二
			Key dESKey = new SecretKeySpec(byteKey, "DES");

			// 加密
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, dESKey);
			byte[] encodeDESRes = cipher.doFinal(src.getBytes());
			System.out.println("jdk DES encode:" + Hex.toHexString(encodeDESRes));

			// 解密
			cipher.init(Cipher.DECRYPT_MODE, dESKey);
			byte[] decodeDESRes = cipher.doFinal(encodeDESRes);
			System.out.println("jdk DES decode:" + new String(decodeDESRes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// jdk 3DES
	public static void jdk3DES() {
		try {
			// 生成Key
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
			keyGenerator.init(112);
			Key secretKey = keyGenerator.generateKey();
			byte[] byteKey = secretKey.getEncoded();

			// 构建规范秘钥
			Key dESKey = new SecretKeySpec(byteKey, "DESede");

			// 加密
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, dESKey);
			byte[] encodeDESRes = cipher.doFinal(src.getBytes());
			System.out.println("\r\njdk 3DES encode:" + Hex.toHexString(encodeDESRes));

			// 解密
			cipher.init(Cipher.DECRYPT_MODE, dESKey);
			byte[] decodeDESRes = cipher.doFinal(encodeDESRes);
			System.out.println("jdk 3DES decode:" + new String(decodeDESRes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// jdk 3DES
	public static void jdkAES() {
		try {
			// 生成Key
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			Key secretKey = keyGenerator.generateKey();
			byte[] byteKey = secretKey.getEncoded();

			// 构建规范秘钥
			Key dESKey = new SecretKeySpec(byteKey, "AES");

			// 加密
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, dESKey);
			byte[] encodeDESRes = cipher.doFinal(src.getBytes());
			System.out.println("\r\njdk AES encode:" + Hex.toHexString(encodeDESRes));

			// 解密
			cipher.init(Cipher.DECRYPT_MODE, dESKey);
			byte[] decodeDESRes = cipher.doFinal(encodeDESRes);
			System.out.println("jdk AES decode:" + new String(decodeDESRes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
