package com.youedata.sys.core.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * AES工具类 : ECB模式
 * @author liujijun
 */
public class AESUtil {
	
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding"; // 算法/模式/补码方式
    
    public static String base64Encode(byte[] bytes){  
        return Base64.encodeBase64String(bytes);  
    }  
    
    public static byte[] base64Decode(String base64Code) throws Exception{  
    	return Base64.decodeBase64(base64Code.getBytes("utf-8"));
    }  
    
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(encryptKey.getBytes());
        kgen.init(128, secureRandom);  
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));  
        return cipher.doFinal(content.getBytes("utf-8"));  
    }  
    
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
    	return base64Encode(aesEncryptToBytes(content, encryptKey));  
    }  
    
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
        return new String(decryptBytes);  
    }  
    
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
    	return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);  
    }  

    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        String key = "cfzc1zxi5z6dvzsi";
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[12];
        random.nextBytes(bytes);
        key = Base64.encodeBase64String(bytes);
        String content = "Test String么么哒";//TZlvXTqxslflIhzMj/AGAjbL5RedD8uom6DHFWDZRBI=
        System.out.println("加密前：" + content);  
        System.out.println("加密密钥和解密密钥：" + key);
        String encrypt = aesEncrypt(content, key);
        System.out.println(encrypt.length()+":加密后：" + encrypt);  
        String decrypt = aesDecrypt(encrypt, key);
        System.out.println("解密后：" + decrypt);  
    }
}