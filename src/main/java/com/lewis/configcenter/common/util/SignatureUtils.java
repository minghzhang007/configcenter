package com.lewis.configcenter.common.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SignatureUtils {

	public static final byte[] SALT_LOGIN_INFO = "#cko$mxi)c2wEegb[i^ncd9e@xc".getBytes();

	/**
     * 按字节xor
     */
    public static byte[] saltByXOR(byte[] source, byte[] key) {
        int keyLen = key.length;
        for (int i = 0; i < source.length; i++) {
            source[i] = (byte) (source[i] ^ key[i % keyLen]);
        }
        return source;
    }
    
    /**
     * MD5
     */
    private static byte[] MD5Digest(byte source[])
    {
        try {
            MessageDigest alg = MessageDigest.getInstance("MD5");
            alg.update(source);
            return alg.digest();
        } catch(NoSuchAlgorithmException e) {
        	//
        }
        return null;
    }
	
    /**
     * MD5：返回十六进制字符串
     * @param needSalt 是否加盐
     */
	private static String HexedMD5Digest(byte source[], byte key[], boolean needSalt) {
		if (needSalt) {
			saltByXOR(source, key);
		}
		return new String(Hex.encodeHex(MD5Digest(source)));
	}

    /**
     * 生成签名
     */
    public static String generateSignature(String source, byte[] key, int length) {
        String signature = HexedMD5Digest(StringUtil.stringToBytes(source, "UTF-8"), key, true);
        if (signature.length() > length) {
            signature = signature.substring(0, length);
        } else if (signature.length() != length) {
            throw new IllegalStateException("Failed to generate signature for: " + source + ", key: " +
                StringUtil.bytesToString(key, "ISO-8859-1") + ", signature: " + signature);
        }
        return signature;
    }
    
    public static String generateLoginSignature(String source) {
    	return generateSignature(source, SALT_LOGIN_INFO, 8);
    }
	
    public static void main(String[] args) {
		System.out.println(generateLoginSignature("li_maozhi@163.com"));
	}
}
