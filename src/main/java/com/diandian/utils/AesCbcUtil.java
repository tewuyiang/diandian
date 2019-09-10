package com.diandian.utils;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AesCbcUtil {

	static {
        //BouncyCastle???????????????????????????http://www.bouncycastle.org/
        Security.addProvider(new BouncyCastleProvider());
    }
 
	public static byte[] strToByteArray(String str) {
	    if (str == null) {
	        return null;
	    }
	    byte[] byteArray = str.getBytes();
	    return byteArray;
	}
    public static String decrypt(String data, String key, String iv, String encodingFormat){
//        initialize();
 
    	System.out.println("??????data:"+data+"\nkey:"+key+"\niv:"+iv);
        //???????????
        byte[] dataByte = Base64.decodeBase64(strToByteArray(data));
        //???????
        byte[] keyByte = Base64.decodeBase64(strToByteArray(key));
        //?????
        byte[] ivByte = Base64.decodeBase64(strToByteArray(iv));
 
 
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
 
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
 
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
 
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// ?????
 
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, encodingFormat);
                return result;
            }
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
 
        return null;
    }
}
