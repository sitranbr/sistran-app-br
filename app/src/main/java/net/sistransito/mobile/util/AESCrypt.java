package net.sistransito.mobile.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;
import android.util.Log;

/**
 * @author vipin.cb , vipin.cb@experionglobal.com <br>
 *         Sep 27, 2013, 5:18:34 PM <br>
 *         Package:- <b>com.veebow.util</b> <br>
 *         Project:- <b>Veebow</b>
 *         <p>
 */

public class AESCrypt {

    private final Cipher cipher;
    private final SecretKeySpec secretKeySpec;
    private AlgorithmParameterSpec spec;
    private static final String secretKeyInstance = "PBKDF2WithHmacSHA1";
    private static String saltB = "vdfjgodfjig53457tosjojsdo53458";
    private static final String SEED_16_CHARACTER = "96MjU1M0FD16Z.Qz";
    private SecretKey skeySpec;

    public AESCrypt() throws Exception {

        skeySpec = generateKey("stasera10", saltB);

        // hash password with SHA-256 and crop the output to 128-bit for secretKeySpec
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(SEED_16_CHARACTER.getBytes("UTF-8"));
        byte[] keyBytes = new byte[32];

        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);

        //Log.d("keyBytes: ", String.valueOf(keyBytes));

        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        //secretKeySpec = new SecretKeySpec(getRaw(password, AESSalt), "AES");
        spec = getIV();

        byte[] iv = { 1, 8, 5, 8, 6, 7, 9, 7, 3, 8, 9, 0, 8, 5, 1, 1, };

        String bytesToHex = bytesToHex(iv);

        Log.i("bytesToHex: ", bytesToHex);

    }

    public static SecretKey generateKey(String passphraseOrPin, String saltb) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Number of PBKDF2 hardening rounds to use. Larger values increase
        // computation time. You should select a value that causes computation
        // to take >100ms.
        final int iterations = 10;

        // Generate a 256-bit key
        final int outputKeyLength = 256;

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(secretKeyInstance);
        KeySpec keySpec = new PBEKeySpec(passphraseOrPin.toCharArray(), saltb.getBytes(), iterations, outputKeyLength);
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
        return secretKey;
    }

    public AlgorithmParameterSpec getIV() {
        byte[] iv = { 1, 8, 5, 8, 6, 7, 9, 7, 3, 8, 9, 0, 8, 5, 1, 1, };
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);

        return ivParameterSpec;
    }

    private static String bytesToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }

    public String encrypt(String plainText) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, spec);
        //cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, spec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedText = new String(Base64.encode(encrypted,
                Base64.DEFAULT), "UTF-8");

        return encryptedText;
    }

    public String decrypt(String cryptedText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, spec);
        //cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, spec);
        byte[] bytes = Base64.decode(cryptedText, Base64.DEFAULT);
        byte[] decrypted = cipher.doFinal(bytes);
        String decryptedText = new String(decrypted, "UTF-8");

        return decryptedText;
    }

}


