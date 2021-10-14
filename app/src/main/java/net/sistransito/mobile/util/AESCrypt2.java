package net.sistransito.mobile.util;

import android.util.Base64;
import android.util.Log;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypt2
{
    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";
    private AlgorithmParameterSpec spec;

    public static String encrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(AESCrypt2.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
        return encryptedValue64;

    }

    public static String decrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(AESCrypt2.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.decode(value, Base64.DEFAULT);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue,"utf-8");
        return decryptedValue;

    }

    private static Key generateKey() throws Exception
    {
        Key key = new SecretKeySpec(AESCrypt2.KEY.getBytes(),AESCrypt2.ALGORITHM);

        String keyToString = keyToString(key);

        Log.i("keyToString: ", keyToString);
        Log.i("decodeKeyFromString: ", String.valueOf(decodeKeyFromString(keyToString)));

        return key;
    }

    public static String keyToString(Key secretKey) {
        if (secretKey != null) {
            String encodedKey = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
            return encodedKey;
        }
        return null;
    }

    public static SecretKey decodeKeyFromString(String stringKey) {
        if (stringKey != null) {
            byte[] encodedKey = Base64.decode(stringKey, Base64.DEFAULT);
            SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
            return originalKey;
        }
        return null;
    }
}
