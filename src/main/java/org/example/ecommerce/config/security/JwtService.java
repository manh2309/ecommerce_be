package org.example.ecommerce.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface JwtService {

    void verifyAndValidate(final String token);

    UsernamePasswordAuthenticationToken getAuthentication(final String token);
}
