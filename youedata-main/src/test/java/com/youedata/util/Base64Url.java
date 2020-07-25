package com.youedata.util;

import org.junit.Test;

import io.jsonwebtoken.impl.Base64UrlCodec;

/**
 * @Description base64 url 避免转义
 * @Author liujijun
 */
public class Base64Url {

    @Test
    public void test(){       
    	String src = "mUoi5lkwcT5Yiq6EWNwNdFsRPMJNcmc7q6iUkJizLLzTwMVE1mvbV3egclQURoY+ASP+1XhSo70EerAhW1wxLRRim6bNlx8/40kcpDt9CNa/Ac1QW+4TyUssSVG2lv+i";
    	System.out.println(src);
    	
    	String encodeStr2 = Base64UrlCodec.BASE64URL.encode(src);
    	System.out.println(encodeStr2);
    	
    	byte[] decode2 = Base64UrlCodec.BASE64URL.decode(encodeStr2);
    	String decodeStr2 = new String(decode2);
    	System.out.println(decodeStr2);
    }

}
