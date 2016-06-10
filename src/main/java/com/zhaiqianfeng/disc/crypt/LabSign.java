/**
 * @author ZhaiQianfeng <zhaiqianfeng@163.com> 
 *
 * @webSite: http://www.zhaiqianfeng.com/blog/
 *
 * @gitHub:  https://github.com/zhaiqianfeng 
 */
package com.zhaiqianfeng.disc.crypt;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.encoders.Hex;


/**
 * 签名算法
 */
public class LabSign {

	// 待处理的字符串
	private static String src = "com.zhaiqianfeng";
	
	public static void main(String[] args){
		jdkRSASigin();
		jdkDSASigin();
	}
	
	//jdk 实现的RSA签名算法
	public static void jdkRSASigin(){
		try {
			//构建秘钥
			KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512);
			KeyPair keyPair=keyPairGenerator.generateKeyPair();
			
			//构建符合规范的秘钥
			KeyFactory keyFactory=KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
			PrivateKey privateKey=keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			
			//私钥签名
			Signature signature=Signature.getInstance("MD5withRSA");
			signature.initSign(privateKey);
			signature.update(src.getBytes());
			byte[] encodeResult=signature.sign();
			System.out.println("MD5withRSA签名结果："+Hex.toHexString(encodeResult));
			
			//公钥验证签名
			X509EncodedKeySpec X509EncodedKeySpec=new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
			PublicKey publicKey=keyFactory.generatePublic(X509EncodedKeySpec);
			signature.initVerify(publicKey);
			signature.update(src.getBytes());
			boolean verfyResult=signature.verify(encodeResult);
			System.out.println("MD5withRSA验证签名结果："+verfyResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//jdk 实现的DSA签名算法
		public static void jdkDSASigin(){
			try {
				//构建秘钥
				KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("DSA");
				keyPairGenerator.initialize(512);
				KeyPair keyPair=keyPairGenerator.generateKeyPair();
				
				//构建符合规范的秘钥
				KeyFactory keyFactory=KeyFactory.getInstance("DSA");
				PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
				PrivateKey privateKey=keyFactory.generatePrivate(pkcs8EncodedKeySpec);
				
				//私钥签名
				Signature signature=Signature.getInstance("SHA1withDSA");
				signature.initSign(privateKey);
				signature.update(src.getBytes());
				byte[] encodeResult=signature.sign();
				System.out.println("\r\nSHA1withDSA签名结果："+Hex.toHexString(encodeResult));
				
				//公钥验证签名
				X509EncodedKeySpec X509EncodedKeySpec=new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
				PublicKey publicKey=keyFactory.generatePublic(X509EncodedKeySpec);
				signature.initVerify(publicKey);
				signature.update(src.getBytes());
				boolean verfyResult=signature.verify(encodeResult);
				System.out.println("SHA1withDSA验证签名结果："+verfyResult);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

}
