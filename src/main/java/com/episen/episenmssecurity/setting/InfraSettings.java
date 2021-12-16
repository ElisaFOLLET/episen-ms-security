package com.episen.episenmssecurity.setting;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public class InfraSettings {
    @Value( "${path}" )
    private static String path;

    public static KeyPair keyPairLoader(){
        try(FileInputStream is = new FileInputStream(path)) {

            KeyStore kstore = KeyStore.getInstance("PKCS12");
            kstore.load(is, "episen".toCharArray());

            Key key = kstore.getKey("episen", "episen".toCharArray());

            Certificate certificat = kstore.getCertificate("episen");

            return new KeyPair(certificat.getPublicKey(), (PrivateKey)key);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
