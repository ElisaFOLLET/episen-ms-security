package com.episen.episenmssecurity;

import com.episen.episenmssecurity.models.AuthenticationRequest;
import com.episen.episenmssecurity.models.AuthenticationResponse;
import com.episen.episenmssecurity.services.MyUserDetailsService;
import com.episen.episenmssecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    /**
     * First endpoint accessible with a specific JWT and returns content
     */
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String hello(@RequestBody AuthenticationResponse authenticationResponse) {
        return jwtTokenUtil.extractUsername(authenticationResponse.getJwt());
        //return "Hello World";
    }
    /**
     * Second endpoint for authentication which accepts user ID and password and returns JWT
     * @param authenticationRequest sending by a POST request
     * @return a JWT as response
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
