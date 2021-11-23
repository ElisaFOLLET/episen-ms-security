package com.episen.episenmssecurity.security;

import com.episen.episenmssecurity.setting.InfraSettings;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.*;


/**
 * Allows creating new JWT given user details object
 * Pull up information from an existing JWT, look up a username from a JWT for example
 */
@Service
public class JwTokenGenerator {

    private JWSSigner signer;

    @PostConstruct
    public void init() {
        signer = new RSASSASigner(InfraSettings.keyPairLoader().getPrivate());
    }

    /**
     * @param userDetails, provided by the user details services
     * @return a JWT based on the userDetails
     */
    public String generateToken(UserDetails userDetails) {
        List<String> roles = new ArrayList<>();
        return createToken(roles, userDetails.getUsername());
    }

    /**
     * Creation of the JWT
     * @return JWT
     */
    private String createToken(List<String> roles, String subject) {
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
