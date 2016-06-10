/**
 * @author ZhaiQianfeng <zhaiqianfeng@163.com> 
 *
 * @webSite: http://www.zhaiqianfeng.com/blog/
 *
 * @gitHub:  https://github.com/zhaiqianfeng 
 */
package com.zhaiqianfeng.disc.crypt;

import java.util.Base64;

/**
 *	Base64算法 
 */
public class LabBase64 {

	// 待处理的字符串
	private static String src = "com.zhaiqianfeng";

	public static void main(String[] args) {
		jdkBase64();
	}

	/**
	 * JDK Base64 实现
	 */
	public static void jdkBase64() {

		// JDK6的 base64
		String jdk6EncodeRes = javax.xml.bind.DatatypeConverter.printBase64Binary(src.getBytes());
		System.out.println("jdk6 base64 encode:" + jdk6EncodeRes);
		String jdk6DecodeRes = new String(javax.xml.bind.DatatypeConverter.parseBase64Binary(jdk6EncodeRes));
		System.out.println("jdk6 base64 decode:" + jdk6DecodeRes);

		// JDK8基本的 base64
		// jdk 基本的 base64 编码
		String encodeRes = Base64.getEncoder().encodeToString(src.getBytes());
		System.out.println("\r\njdk base64 encode:" + encodeRes);
		// jdk 基本的 base64 解码
		String decodeRes = new String(Base64.getDecoder().decode(encodeRes));
		System.out.println("jdk base64 decode:" + decodeRes);

		// JDK URL base64编码：使用下划线代替基本的64个字符的/
		// jdk URL的 base64 编码
		encodeRes = Base64.getUrlEncoder().encodeToString(src.getBytes());
		System.out.println("\r\njdk url base64 encode:" + encodeRes);
		// jdk URL的 base64 解码
		decodeRes = new String(Base64.getUrlDecoder().decode(encodeRes));
		System.out.println("jdk url base64 decode:" + decodeRes);

		// JDK MIME
		// base64编码：使用基本BASE64输出，而且对MIME格式友好：每一行输出不超过76个字符，而且每行以“\r\n”符结束
		// jdk MIME的 base64 编码
		encodeRes = Base64.getMimeEncoder().encodeToString(src.getBytes());
		System.out.println("\r\njdk MIME base64 encode:" + encodeRes);
		// jdk MIME的 base64 解码
		decodeRes = new String(Base64.getMimeDecoder().decode(encodeRes.getBytes()));
		System.out.println("jdk MIME base64 decode:" + decodeRes);

	}

}
