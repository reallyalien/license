

package cn.com.dhcc.license.client.util.server;


import javax.crypto.Cipher;

import cn.com.dhcc.license.client.exception.LicenseException;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;



public class RasUtil {
    private static final String KEY_ALGORITHM = "RSA";

    private static final int KEY_SIZE = 1024;
    


    public static KeyPair initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }
    
    public static void main (String[] args) throws Exception {
    	KeyPair d=initKey();
    	System.out.println(d);
    }


    /**
     * 私钥加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) {
        return encryptByPrivateKey(data, key, 0, data.length);
    }

    public static byte[] encryptByPrivateKey(byte[] data, byte[] key, int start, int length) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(data, start, length);
        } catch (Exception e) {
            throw new LicenseException("encrypt exception", e);
        }
    }

    public static byte[] getPrivateKey(KeyPair keyPair) {
        return keyPair.getPrivate().getEncoded();
    }


    public static byte[] getPublicKey(KeyPair keyPair) {
        return keyPair.getPublic().getEncoded();
    }
}