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
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.bouncycastle.util.encoders.Hex;

import com.google.common.base.Objects;

/**
 * 非对称加密算法
 */
public class LabAsymmetricCipher {
	// 待处理的字符串
	private static String src = "com.zhaiqiafneng";

	public static void main(String[] args) {
		jdkRSA();
		jdkDH();
	}

	// JDK实现的RSA算法
	public static void jdkRSA() {
		try {
			// 获取密钥对
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			PublicKey rsaPublicKey = keyPair.getPublic();
			PrivateKey rsaPrivateKey = keyPair.getPrivate();

			// 私钥加密 公钥解密之私钥加密
			// 获取规范私钥
			// Only RSAPrivate(Crt)KeySpec and PKCS8EncodedKeySpec supported for
			// RSA private keys
			PKCS8EncodedKeySpec pkcs8EncodeKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key privateKey = keyFactory.generatePrivate(pkcs8EncodeKeySpec);
			// 私钥加密
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] encodeRes = cipher.doFinal(src.getBytes());
			System.out.println("私钥加密 公钥解密之私钥加密结果：" + Hex.toHexString(encodeRes));

			// 私钥加密 公钥解密之公钥解密
			// 获取规范公钥
			// Only RSAPublicKeySpec and X509EncodedKeySpec supported for RSA
			// public keys
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] decodeRes = cipher.doFinal(encodeRes);
			System.out.println("私钥加密 公钥解密之公钥解密结果：" + new String(decodeRes));

			// 公钥加密 私钥解密之公钥加密
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encodeRes = cipher.doFinal(src.getBytes());
			System.out.println("\r\n公钥加密 私钥加密之公钥加密结果：" + Hex.toHexString(encodeRes));
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			decodeRes = cipher.doFinal(encodeRes);
			System.out.println("公钥加密 私钥加密之私钥结果：" + new String(decodeRes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// JDK实现的DH算法
	public static void jdkDH() {
		try {
			// 初始化发送发秘钥
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
			keyPairGenerator.initialize(512);
			KeyPair senderKeyPair = keyPairGenerator.generateKeyPair();
			byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();

			// 初始化接收方秘钥
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
			// ---通过发送方的公钥获取公钥参数规范
			KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
			PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
			DHParameterSpec dhParameterSpec = ((DHPublicKey) receiverPublicKey).getParams();
			// ---通过发送方的公钥参数来生成接收方的秘钥
			keyPairGenerator.initialize(dhParameterSpec);
			KeyPair receiverKeyPair = keyPairGenerator.generateKeyPair();
			PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
			byte[] receiverPublicKeyEnc = receiverKeyPair.getPublic().getEncoded();

			// 秘钥构建
			KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
			receiverKeyAgreement.init(receiverPrivateKey);
			receiverKeyAgreement.doPhase(receiverPublicKey, true);
			SecretKey receiverDESKey = receiverKeyAgreement.generateSecret("DES");

			KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
			x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
			PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);

			KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
			senderKeyAgreement.init(senderKeyPair.getPrivate());
			senderKeyAgreement.doPhase(senderPublicKey, true);
			SecretKey senderDEStKey = senderKeyAgreement.generateSecret("DES");

			if (Objects.equal(senderDEStKey, receiverDESKey)) {
				System.out.println("\r\nDH算法，发送发和接收方成功交换了秘钥");
			}

			// 发送方加密
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, senderDEStKey);
			byte[] encodeResult = cipher.doFinal(src.getBytes());
			System.out.println("DH之发送方发加密结果：" + Hex.toHexString(encodeResult));

			// 接收方解密
			cipher.init(Cipher.DECRYPT_MODE, receiverDESKey);
			byte[] decodeResult = cipher.doFinal(encodeResult);
			System.out.println("DH之接收方发解密结果:" + new String(decodeResult));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
