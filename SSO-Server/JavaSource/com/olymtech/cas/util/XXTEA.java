package com.olymtech.cas.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import sun.misc.BASE64Decoder;

public class XXTEA {
    private XXTEA() {
    } 
   public static final String URLEncoding="UTF-8";
   public static String ibos_security_prefix_key="@3^!92)";
   public static final String encryptCasenumberPrefix="security";
   public static final String encryptParameter="casenumber";
   

   //public static byte[] XXTEAPrivateKey=getEncodingByte("olymtechencrypt");
   
   
   public static byte[] getEncodingByte(String src){
	   if(src==null) 
		   return null;
	   
	   try {
		   return src.getBytes(URLEncoding);
		} catch (UnsupportedEncodingException e) {
			
			return null;
		}
   }

    
    /** 
     * Encrypt data with key. 
     * 
     * @param data s
     * @param key 
     * @return 
     */ 
    public static byte[] encrypt(byte[] data, byte[] key) { 
        if (data==null || data.length == 0) { 
            return data; 
        } 
        return toByteArray( 
                encrypt(toIntArray(data, true), toIntArray(key, false)), false); 
    } 
  
    /** 
     * Decrypt data with key. 
     * 
     * @param data 
     * @param key 
     * @return 
     */ 
    public static byte[] decrypt(byte[] data, byte[] key) { 
        if (data.length == 0) { 
            return data; 
        } 
        return toByteArray( 
                decrypt(toIntArray(data, false), toIntArray(key, false)), true); 
    } 
  
    /** 
     * Encrypt data with key. 
     * 
     * @param v 
     * @param k 
     * @return 
     */ 
    public static int[] encrypt(int[] v, int[] k) { 
        int n = v.length - 1; 
  
        if (n < 1) { 
            return v; 
        } 
        if (k.length < 4) { 
            int[] key = new int[4]; 
  
            System.arraycopy(k, 0, key, 0, k.length); 
            k = key; 
        } 
        int z = v[n], y = v[0], delta = 0x9E3779B9, sum = 0, e; 
        int p, q = 6 + 52 / (n + 1); 
  
        while (q-- > 0) { 
            sum = sum + delta; 
            e = sum >>> 2 & 3; 
            for (p = 0; p < n; p++) { 
                y = v[p + 1]; 
                z = v[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) 
                        ^ (sum ^ y) + (k[p & 3 ^ e] ^ z); 
            } 
            y = v[0]; 
            z = v[n] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) 
                    ^ (sum ^ y) + (k[p & 3 ^ e] ^ z); 
        } 
        return v; 
    } 
  
    /** 
     * Decrypt data with key. 
     * 
     * @param v 
     * @param k 
     * @return 
     */ 
    public static int[] decrypt(int[] v, int[] k) { 
        int n = v.length - 1; 
  
        if (n < 1) { 
            return v; 
        } 
        if (k.length < 4) { 
            int[] key = new int[4]; 
  
            System.arraycopy(k, 0, key, 0, k.length); 
            k = key; 
        } 
        int z = v[n], y = v[0], delta = 0x9E3779B9, sum, e; 
        int p, q = 6 + 52 / (n + 1); 
  
        sum = q * delta; 
        while (sum != 0) { 
            e = sum >>> 2 & 3; 
            for (p = n; p > 0; p--) { 
                z = v[p - 1]; 
                y = v[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) 
                        ^ (sum ^ y) + (k[p & 3 ^ e] ^ z); 
            } 
            z = v[n]; 
            y = v[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) 
                    ^ (sum ^ y) + (k[p & 3 ^ e] ^ z); 
            sum = sum - delta; 
        } 
        return v; 
    } 
  
    /** 
     * Convert byte array to int array. 
     * 
     * @param data 
     * @param includeLength 
     * @return 
     */ 
    private static int[] toIntArray(byte[] data, boolean includeLength) { 
        int n = (((data.length & 3) == 0) 
                ? (data.length >>> 2) 
                : ((data.length >>> 2) + 1)); 
        int[] result; 
  
        if (includeLength) { 
            result = new int[n + 1]; 
            result[n] = data.length; 
        } else { 
            result = new int[n]; 
        } 
        n = data.length; 
        for (int i = 0; i < n; i++) { 
            result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3); 
        } 
        return result; 
    } 
  
    /** 
     * Convert int array to byte array. 
     * 
     * @param data 
     * @param includeLength 
     * @return 
     */ 
    private static byte[] toByteArray(int[] data, boolean includeLength) { 
        int n = data.length << 2; 
  
        ; 
        if (includeLength) { 
            int m = data[data.length - 1]; 
  
            if (m > n) { 
                return null; 
            } else { 
                n = m; 
            } 
        } 
        byte[] result = new byte[n]; 
  
        for (int i = 0; i < n; i++) { 
            result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff); 
        } 
        return result; 
    } 
    
    
    public static String getBASE64(byte[] s) { 
    	if (s == null) return null; 
    	return (new sun.misc.BASE64Encoder()).encode( s); 
    } 

    public static byte[] getFromBASE64(String s) { 
    	if (s == null) return null; 
    	BASE64Decoder decoder = new BASE64Decoder(); 
    	try { 
    		byte[] b = decoder.decodeBuffer(s); 
    		return b; 
    	} catch (Exception e) { 
    		return null; 
    	} 
    }
    
	public static String doEncryptURL(String url,String privateKey){
		byte[] bytePrivateKey=getEncodingByte(privateKey);
		
		if(url==null || url.indexOf("?")<0 ){
			return url;
		}
		
		url=getCaseNumberEncryptURL(url,"&"+encryptParameter+"=",bytePrivateKey);
		return url;
	}
	
	public static String getCaseNumberEncryptURL(String url,String casenumberSplitStr,byte[] bytePrivateKey){
		if(url.indexOf(casenumberSplitStr)>-1){
			String casenumber="";
			String casenumberEncryptFinished="";
			String beforeCaseNumberStr="";
			String afterCaseNumberStr="";
		
			beforeCaseNumberStr=url.substring(0,url.indexOf(casenumberSplitStr));
			afterCaseNumberStr=url.substring(url.indexOf(casenumberSplitStr)+casenumberSplitStr.length());
			
			int endPosition=afterCaseNumberStr.indexOf("&");
			if(endPosition>-1){
				casenumber=afterCaseNumberStr.substring(0,endPosition);
				afterCaseNumberStr=afterCaseNumberStr.substring(endPosition);
			} else {
				casenumber=afterCaseNumberStr;
				afterCaseNumberStr="";
			}
			
			casenumber=StringHelper.encodeString(casenumber);
			
			//判断是否加密过，如果有就直接返回url 如果没有那么解密后再返回
			if(isEncryptCasenumber(casenumber)){
				return url;
			}
			
			byte[] enByte=XXTEA.encrypt(getEncodingByte(casenumber), bytePrivateKey);
			try {
				casenumberEncryptFinished=casenumberSplitStr+encryptCasenumberPrefix+URLEncoder.encode(getBASE64(enByte),URLEncoding);
			} catch (UnsupportedEncodingException e) {
				
			}	
			
			url=beforeCaseNumberStr+casenumberEncryptFinished+afterCaseNumberStr;
			
		} 		
		return url;
	}
	
	public static boolean isEncryptCasenumber(String casenumber){
		
		if(casenumber!=null && casenumber.startsWith(encryptCasenumberPrefix)){
			return true;
		} else {
			return false;
		}
		
	}
	
	
	public static String getCaseNumberEncrypt(String casenumber,String privateKey){
		casenumber=StringHelper.encodeString(casenumber);
		byte[] bytePrivateKey=getEncodingByte(privateKey);
		
		//判断是否加密过，如果有就直接返回casenumber 如果没有那么解密后再返回
		if(isEncryptCasenumber(casenumber)){
			return casenumber;
		}
		
		byte[] enByte=XXTEA.encrypt(getEncodingByte(casenumber), bytePrivateKey);
		try {
			casenumber=encryptCasenumberPrefix+URLEncoder.encode(getBASE64(enByte),URLEncoding);
		} catch (UnsupportedEncodingException e) {
			
		}		
		return casenumber;
	}
	
	public static String getDecryptParameter(String parameter,String parameterValue,String privateKey){
		byte[] bytePrivateKey=getEncodingByte(privateKey);
		
		//若不是加密参数那么不做任何处理
		if(parameter!=null && !parameter.equalsIgnoreCase(encryptParameter)){
			return parameterValue;
		} else {
			String casenumberDecryptStr=null;
			String casenumber=parameterValue;
			
			//判断是否加密过，如果有就直接返回casenumber 如果没有那么解密后再返回
			if(!isEncryptCasenumber(casenumber)){
				return casenumber;
			} else {
				//去掉前面的特殊字符就是casenumber加密后的数据；
				if(casenumber.length()>encryptCasenumberPrefix.length()){
					casenumber=casenumber.substring(encryptCasenumberPrefix.length());
				}
			}
			
			//然后开始解密
			if(casenumber!=null){
				byte[] t = getFromBASE64(casenumber);
				byte[] decryptByte= XXTEA.decrypt(t,bytePrivateKey);
				casenumberDecryptStr=new String(decryptByte);
			}
			return casenumberDecryptStr;
		}
	}
	
	/**
	 * 根据Key作为私有Key，把Str字符串加密
	 * @param str
	 * @param key
	 * @return
	 */
	 public static String getEncryptString(String str,String key) {
		  String retValue="";
		  str=StringHelper.encodeString(str);
		  byte[] bytePrivateKey=XXTEA.getEncodingByte(key);
		  try {
			  byte[] enByte=XXTEA.encrypt(XXTEA.getEncodingByte(str), bytePrivateKey);
			  retValue=URLEncoder.encode(XXTEA.getBASE64(enByte),XXTEA.URLEncoding);
		  } catch (Exception e) {
			  return "";
		  }
		  return retValue;
	 }
	 
	 /**
	  * 根据Key作为私有Key，把Str字符串解密得到解密串
	  * @param str
	  * @param key
	  * @return
	  */
	 public static String getDecryptString(String str,String key){
		  byte[] bytePrivateKey=XXTEA.getEncodingByte(key);
		  try {
			str=URLDecoder.decode(str, XXTEA.URLEncoding);
		  } catch (UnsupportedEncodingException e) {
			  return "";
		  }
		  byte[] t =XXTEA.getFromBASE64(str);
		  
		  byte[] decryptByte= XXTEA.decrypt(t,bytePrivateKey);
		  String dString=new String(decryptByte);
		  return dString;
	 }


}
