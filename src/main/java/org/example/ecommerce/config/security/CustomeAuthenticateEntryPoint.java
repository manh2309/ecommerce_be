//package org.example.ecommerce.config.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.constraints.NotNull;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//@Slf4j
//@RequiredArgsConstructor
//public class CustomeAuthenticateEntryPoint extends OncePerRequestFilter {
//    private final JwtService jwtService;
//    @Override
//    protected void doFilterInternal(
//            @NotNull final HttpServletRequest request,
//            @NotNull final HttpServletResponse response,
//            @NotNull final FilterChain filterChain) throws ServletException, IOException {
//        log.debug("API Request was secured with Security!");
//
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            final String token = authHeader.substring(7);
//            jwtService.verifyAndValidate(token);
//
//            final UsernamePasswordAuthenticationToken authenticationToken = jwtService.getAuthentication(token);
//
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        }
//        filterChain.doFilter(request, response);
//        }
//}
