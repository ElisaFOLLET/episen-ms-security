package com.episen.episenmssecurity.setting;

import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public class InfraSettings {
    public static KeyPair keyPairLoader(){
//C:/Users/elisa/git-workspace/episen-ms-security/src/main/resources/keys/server.p12 en local
        //File file = new File("episen-ms-security/src/main/resources/keys/server.p12");
        try(FileInputStream is = new FileInputStream("episen-ms-security/src/main/resources/keys/server.p12")) {

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
