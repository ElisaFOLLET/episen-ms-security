package com.episen.episenmssecurity.security;

import com.episen.episenmssecurity.models.UserContext;
import com.episen.episenmssecurity.setting.InfraSettings;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

@Service
public class JwTokenValidator {
    private JWSVerifier verifier;

    @PostConstruct
    public void init() {
        verifier = new RSASSAVerifier((RSAPublicKey) InfraSettings.keyPairLoader().getPublic());
    }


    /**
     * @param token, check if this is a valid token or not
     * @return userContext from the token
     */
    public UserContext extractUsername(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            if (!signedJWT.verify(verifier)) {
                throw new RuntimeException("Token cannot be verified. Invalide Token");
            }

            UserContext user = new UserContext();
            user.setSubject(signedJWT.getJWTClaimsSet().getSubject());

            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
