package com.youedata.util;

import com.google.common.collect.Maps;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * @Description RSA加密工具类
 * @Author zhangzb
 * @Date 2020/1/4
 */
public class RSAUtils {

    private static final String KEY_ALGORITHM = "RSA";
    /**
     * 于封装随机产生的公钥与私钥
     */
    private static Map<Integer, String> keyMap = Maps.newHashMap();

    /**
     *生成私钥  公钥
     */
    public static void gerationStr(){
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom secureRandom = new SecureRandom(new Date().toString().getBytes());
            keyPairGenerator.initialize(1024, secureRandom);
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
            // 将公钥和私钥保存到Map
            keyMap.put(0,Base64.getEncoder().encodeToString(publicKey.getEncoded()));  //0表示公钥
            keyMap.put(1,Base64.getEncoder().encodeToString(privateKey.getEncoded()));  //1表示私钥
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    public static void main(String[] args) {
        gerationStr();
        System.out.println("公钥："+keyMap.get(0));
        System.out.println("私钥："+keyMap.get(1));
        String input = "!!!hello world!!!";
        RSAPublicKey pubKey;
        RSAPrivateKey privKey;
        byte[] cipherText;
        String cipherTextStr;
        Cipher cipher;
        try {
            // 开始加密
            cipherTextStr=encrypt(input,keyMap.get(0));
            //加密后的东西
            System.out.println("cipherTextStr: " + cipherTextStr);
            //开始解密
            String plainTextStr=decrypt(cipherTextStr,keyMap.get(1));
            System.out.println("plainTextStr : " + plainTextStr);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
