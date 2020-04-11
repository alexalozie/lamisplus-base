package org.lamisplus.modules.base.util.converter;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
public class UuidGenerator {
    public static String getUuid(){
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }
}
