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
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Hex;

/**
 * 对称加密算法
 *
 */
public class LabSymmetricCipher {
	private static String src="com.zhaiqiafneng";
	public static void main(String[] args){
		jdkDES();
	}
	
	public static void jdkDES(){
		try {
			//生成Key
			KeyGenerator keyGenerator=KeyGenerator.getInstance("DES");
			keyGenerator.init(56);
			Key secretKey=keyGenerator.generateKey();
			byte[] byteKey=secretKey.getEncoded();
			
			//赋予Key特性，两种获取Key的方式
			//方式一
//			DESKeySpec dESKeySpec=new DESKeySpec(byteKey);
//			SecretKeyFactory factory=SecretKeyFactory.getInstance("DES");
//			Key dESKey=factory.generateSecret(dESKeySpec);
//			
			//方式二
			Key dESKey=new SecretKeySpec(byteKey,"DES");
			
			
			Cipher cipher=Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, dESKey);
			byte[] desRes=cipher.doFinal(src.getBytes());
			System.out.println("jdk DES encode:"+Hex.toHexString(desRes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
