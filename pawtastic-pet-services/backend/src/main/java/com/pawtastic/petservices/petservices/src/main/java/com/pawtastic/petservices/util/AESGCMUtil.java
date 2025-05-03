package com.pawtastic.petservices.util;

import com.pawtastic.petservices.dto.EncryptedPayloadDTO;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

public class AESGCMUtil {
    private static final String PASSPHRASE = "super-secret-passphrase";
    private static final int AES_KEY_SIZE = 256;
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final String DIGEST = "SHA-256";
    private static final int GCM_TAG_LENGTH = 16;

    // Key derivation using PBKDF2
    public static SecretKey deriveKey(byte[] salt) throws Exception {
        // Use PBKDF2 with HMAC SHA-256 as the key derivation function
        PBEKeySpec spec = new PBEKeySpec(PASSPHRASE.toCharArray(), salt, 100000, AES_KEY_SIZE);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] key = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(key, "AES");
    }


    // Encryption method
    public static String encrypt(String plainText, byte[] salt) throws Exception {
        SecretKey secretKey = deriveKey(salt);
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        byte[] iv = new byte[12]; // 12-byte IV for AES-GCM
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        byte[] encryptedText = cipher.doFinal(plainText.getBytes());

        // Concatenate IV, salt, and encrypted text
        byte[] encryptedData = new byte[iv.length + salt.length + encryptedText.length];
        System.arraycopy(iv, 0, encryptedData, 0, iv.length);
        System.arraycopy(salt, 0, encryptedData, iv.length, salt.length);
        System.arraycopy(encryptedText, 0, encryptedData, iv.length + salt.length, encryptedText.length);

        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Decryption method
public static String decrypt(EncryptedPayloadDTO dto) throws Exception {

        String[] parts = dto.getData().split(":", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid encrypted payload format");
        }
        String ivBase64 = parts[0];
        String ctBase64 = parts[1];

        byte[] iv = Base64.getDecoder().decode(ivBase64);
        byte[] ciphertext = Base64.getDecoder().decode(ctBase64);

        // Derive the same AES key from passphrase + salt
        byte[] salt = convertListToByteArray(dto.getSalt());
        SecretKey aesKey = deriveKey(salt);

        //Decrypt with AES-GCM using the IV and ciphertext
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, aesKey, spec);
        byte[] plainBytes = cipher.doFinal(ciphertext);

        return new String(plainBytes, StandardCharsets.UTF_8);
    }

    public static byte[] convertListToByteArray(List<Integer> list) {
        byte[] byteArray = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            byteArray[i] = list.get(i).byteValue();
        }
        return byteArray;
    }
}
