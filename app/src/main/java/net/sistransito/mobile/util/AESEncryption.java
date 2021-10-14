package net.sistransito.mobile.util;

import android.util.Base64;
import android.util.Log;

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption {

    private static final int pswdIterations = 10;
    private static final int keySize = 256;

    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    //private static final String PBE_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String PBE_ALGORITHM = "PBEWithSHA256And256BitAES-CBC-BC";
    private static final String password = "sampleTextsample";
    private static final String salt = "exampleSalt";
    private static final String ivVector = "1024530444355566";

    private static byte[] decrypted;
    private static byte[] encrypted;

    private static SecretKey tmp;
    private static SecretKey secret;

    public static SecretKey secreatPBE(String password)
            throws Exception {
        SecretKeyFactory factory = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 10, 256);
        tmp = factory.generateSecret(spec);
        secret = new SecretKeySpec(tmp.getEncoded(), "AES");

        return secret;
    }

    public static byte[] encryptPBE(String password, String cleartext)
            throws Exception {
        /*SecretKeyFactory factory = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1024, 256);
        tmp = factory.generateSecret(spec);
        secret = new SecretKeySpec(tmp.getEncoded(), "AES");*/

        secreatPBE(password);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        return cipher.doFinal(cleartext.getBytes("UTF-8"));
    }

    public static String decryptPBE(SecretKey secret, String ciphertext,
                                    byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
        return new String(cipher.doFinal(ciphertext.getBytes()), "UTF-8");
    }

    public static String encrypt(String textToEncrypt) throws Exception {

        SecretKey skeySpec = generateKey(password, salt);

        //SecretKeySpec skeySpec = new SecretKeySpec(getRaw(password, salt), "AES");

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(ivVector.getBytes()));
        encrypted = cipher.doFinal(textToEncrypt.getBytes());
        return Base64.encodeToString(encrypted, Base64.DEFAULT);

    }

    public static String decrypt(String textToDecrypt) throws Exception {

        SecretKey skeySpec = generateKey(password, salt);

        System.out.println(skeySpec);

        byte[] encryted_bytes = Base64.decode(textToDecrypt, Base64.DEFAULT);
        //SecretKeySpec skeySpec = new SecretKeySpec(getRaw(password, salt), "AES");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(ivVector.getBytes()));
        decrypted = cipher.doFinal(encryted_bytes);
        return new String(decrypted, "UTF-8");
    }

    private static byte[] getRaw(String plainText, String salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(PBE_ALGORITHM);
            KeySpec spec = new PBEKeySpec(plainText.toCharArray(), salt.getBytes(), pswdIterations, keySize);
            return factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static SecretKey generateKey(String passphraseOrPin, String saltb) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Number of PBKDF2 hardening rounds to use. Larger values increase
        // computation time. You should select a value that causes computation
        // to take >100ms.
        final int iterations = 10;

        // Generate a 256-bit key
        final int outputKeyLength = 256;

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBE_ALGORITHM);
        KeySpec keySpec = new PBEKeySpec(passphraseOrPin.toCharArray(), saltb.getBytes(), iterations, outputKeyLength);
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

        String keyToString = keyToString(secretKey);

        Log.i("keyToString: ", keyToString);
        Log.i("decodeKeyFromString: ", String.valueOf(decodeKeyFromString(keyToString)));

        return secretKey;
    }

    public static String keyToString(SecretKey secretKey) {
        if (secretKey != null) {

            String encodedKey = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
            return encodedKey;
        }
        return null;
    }

    public static SecretKey decodeKeyFromString(String stringKey) {
        byte[] encodedKey  = Base64.decode(stringKey, Base64.DEFAULT);
        SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return  originalKey;
    }

}