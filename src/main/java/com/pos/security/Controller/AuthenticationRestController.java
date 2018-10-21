package com.pos.security.Controller;

import com.pos.security.model.POSAuthenticationRequest;
import com.pos.security.model.POSAuthenticationResponse;
import com.pos.security.utility.POSTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController

public class AuthenticationRestController {

    private String tokenHeader;
    private AuthenticationManager authenticationManager;
    private POSTokenUtil posTokenUtil;
    private UserDetailsService userDetailsService;

    @Autowired
    public AuthenticationRestController(@Value("${jwt.header}") String tokenHeader,
                                        AuthenticationManager authenticationManager,
                                        POSTokenUtil posTokenUtil,
                                        @Qualifier("POSUserDetailsService")UserDetailsService userDetailsService) {
        this.tokenHeader = tokenHeader;
        this.authenticationManager = authenticationManager;
        this.posTokenUtil = posTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(value = "${jwt.route.authentication.path}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody POSAuthenticationRequest authenticationRequest) throws AuthenticationException {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = posTokenUtil.generateToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new POSAuthenticationResponse(token));
    }



    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }
}
