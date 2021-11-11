package com.episen.ms.setting;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public class InfraSettings {

	public static KeyPair keyPairLoader() {
		
		try(FileInputStream is = new FileInputStream("C:\\Users\\elisa\\IdeaProjects\\episen-ms-security\\episen-ms-security\\src\\main\\resources\\keys\\server.p12")){
			KeyStore kstore = KeyStore.getInstance("PKCS12");
			kstore.load(is, "episen".toCharArray());
			
			Key key = kstore.getKey("episen", "episen".toCharArray());
			
			Certificate certificat = kstore.getCertificate("episen");
			
			return new KeyPair(certificat.getPublicKey(), (PrivateKey) key);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		KeyPair kp = keyPairLoader();
		System.out.println(kp.getPublic().toString());
	}
}
