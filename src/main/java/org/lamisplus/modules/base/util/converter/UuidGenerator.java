package org.lamisplus.modules.base.util.converter;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
public class UuidGenerator {
    static MessageDigest salt;
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String getUuid(){
        String digest = "";
        try {
            salt = MessageDigest.getInstance("SHA-256");
            salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));

            digest = bytesToHex(salt.digest());
        }catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
            log.info(e.getMessage());
        }
        return digest;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
