package com.olymtech.app.ssocenter.old;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.olymtech.cas.util.XXTEA;

public class SecurityValidate {

	private static SecurityValidate instance=null;
	public static  SecurityValidate getInstance(){
				if(instance==null)
				{
					instance=new SecurityValidate();
				}
				return instance;
	}
	/**
	 * 密钥
	 */
	private static final String  privateKey = "0x539ee9c1, 0xa5a3, 0x4c33, 0xa0, 0x37, 0x11, 0x84, 0xb1, 0xe4, 0x98, 0x9";
	
	
	/**
	 * 加密
	 * @param str 要加密的字符串
	 * @return 加密后的结果
	 */
	public String ecrypt(String str){
		try {
			byte[] ecryptByte = XXTEA.encrypt(XXTEA.getEncodingByte(str), XXTEA
					.getEncodingByte(privateKey));
			String ecryptStr = XXTEA.getBASE64(ecryptByte);
			ecryptStr = URLEncoder.encode(ecryptStr, "UTF-8");
			return ecryptStr;
		} catch (Exception ex) {
			ex.getMessage();
			return str;
		}
		
	}

	/**
	 * 解密
	 * @param str 要解密的字符串
	 * @return 解密后的结果
	 */
	public String decrypt(String str){
		try {
			str=URLDecoder.decode(str, XXTEA.URLEncoding);
		} catch (UnsupportedEncodingException e) {
			return null;
		}		
		byte[] bytePrivateKey=XXTEA.getEncodingByte(privateKey);
		byte[] t =XXTEA.getFromBASE64(str);
		try{
		byte[] decryptByte= XXTEA.decrypt(t,bytePrivateKey);
		String dString=new String(decryptByte);
			return dString.equals("")?str:dString;
		}catch (Exception e) {
			return str;
		}
	}
	
	
}