/**
 * @author ZhaiQianfeng <zhaiqianfeng@163.com> 
 *
 * @webSite: http://www.zhaiqianfeng.com/blog/
 *
 * @gitHub:  https://github.com/zhaiqianfeng 
 */
package com.zhaiqianfeng.disc.crypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Hex;

/**
 * 非对称加密算法
 */
public class LabAsymmetricCipher {
	private static String src = "com.zhaiqiafneng";
	
	public static void main(String[] args) {
		jdkRSA();
	}
	
	public static void jdkRSA(){
		try {
			//获取密钥对
			KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512);
			KeyPair keyPair=keyPairGenerator.generateKeyPair();
			PublicKey rsaPublicKey=keyPair.getPublic();
			PrivateKey rsaPrivateKey=keyPair.getPrivate();
			
			//获取规范秘钥
			//Only RSAPrivate(Crt)KeySpec and PKCS8EncodedKeySpec supported for RSA private keys
			PKCS8EncodedKeySpec pkcs8EncodeKeySpec=new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
			KeyFactory keyFactory=KeyFactory.getInstance("RSA");
			Key privateKey=keyFactory.generatePrivate(pkcs8EncodeKeySpec);
			
			//私钥加密 公钥解密之私钥加密
			Cipher cipher=Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] encodeRes=cipher.doFinal(src.getBytes());
			System.out.println("私钥加密结果："+Hex.toHexString(encodeRes));
			
			//私钥加密 公钥解密之公钥解密
			//Only RSAPublicKeySpec and X509EncodedKeySpec supported for RSA public keys
			X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			Key publicKey=keyFactory.generatePublic(x509EncodedKeySpec);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] decodeRes=cipher.doFinal(encodeRes);
			System.out.println("公钥解密结果："+new String(decodeRes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
