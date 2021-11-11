package com.episen.ms.security;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.episen.ms.setting.InfraSettings;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwTokenGenerator {

	private JWSSigner signer;
	
	@PostConstruct
	public void init() {
		
		signer = new RSASSASigner(InfraSettings.keyPairLoader().getPrivate());
	}
	
	public String generateToken(String subject, List<String> roles) {
		
		ZonedDateTime currentDate = ZonedDateTime.now();
		
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
												.subject(subject)
												.audience("web")
												.issuer("EPISEN-SECURITY")
												.issueTime(Date.from(currentDate.toInstant()))
												.expirationTime(Date.from(currentDate.plusDays(3).toInstant()))
												.jwtID(UUID.randomUUID().toString())
												.claim("ROLES", roles)
												.build();
		
		JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
										.keyID(UUID.randomUUID().toString())
										.type(JOSEObjectType.JWT)
										.build();
		
		SignedJWT signedJwt = new SignedJWT(header, claimsSet);
		
		try {
			signedJwt.sign(signer);
		} catch (JOSEException e) {
			throw new RuntimeException(e);
		}
		
		return signedJwt.serialize();
	}
	
}
