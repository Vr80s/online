package com.xczhihui.bxg.online.common.utils.office365;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.xczhihui.bxg.online.common.utils.OnlineConfig;

//public class Office365Util {
//	
//	private static final byte[] DESkey = OnlineConfig.OFFICE365_KEY.getBytes();
//	private static final byte[] DESIV = OnlineConfig.OFFICE365_XL.getBytes() ;
//	static AlgorithmParameterSpec iv = null;
//	private static Key key = null;
//	
//	public Office365Util() {
//		try {
//			// 设置密钥参数
//			DESKeySpec keySpec = new DESKeySpec(DESkey);
//			// 设置向量
//			iv = new IvParameterSpec(DESIV);
//			// 获得密钥工厂
//			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//			key = keyFactory.generateSecret(keySpec);// 得到密钥对象
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public  String getEncodeUrl(String data) {
//		try {
//			// 得到加密对象Cipher
//			Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//			// 设置工作模式为加密模式，给出密钥和向量
//			enCipher.init(Cipher.ENCRYPT_MODE, key, iv);
//			byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
//			sun.misc.BASE64Encoder base64Encoder = new sun.misc.BASE64Encoder();
//			String code = base64Encoder.encode(pasByte).replace("+", "_").replace("/", "@");
//			return "http://officeweb365.com/o/?i="+OnlineConfig.OFFICE365_USER_ID
//					+"&furl="+code;
//		} catch (Exception e) {
//		}
//		return "";
//	}
//	
//}
