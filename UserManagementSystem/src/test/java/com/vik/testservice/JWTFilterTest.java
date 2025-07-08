package com.vik.testservice;

import com.vik.filter.JWTFilter;
import com.vik.service.JWTService;
import com.vik.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class JWTFilterTest {

    @InjectMocks
    private JWTFilter jwtFilter;

    @Mock
    private JWTService jwtService;

    @Mock
    private ApplicationContext context;

    @Mock
    private MyUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterWithoutAuthorizationHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, never()).extractEmail(any());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterWithInvalidToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid.token");
        when(jwtService.extractEmail(anyString())).thenReturn("test@example.com");
        when(context.getBean(MyUserDetailsService.class)).thenReturn(userDetailsService);
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);
        when(jwtService.validateToken(anyString(), any())).thenReturn(false);

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterWithValidToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer valid.token");
        when(jwtService.extractEmail("valid.token")).thenReturn("user@example.com");
        when(context.getBean(MyUserDetailsService.class)).thenReturn(userDetailsService);
        when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);
        when(jwtService.validateToken("valid.token", userDetails)).thenReturn(true);
        when(userDetails.getAuthorities()).thenReturn(null); // or any authority collection

        jwtFilter.doFilterInternal(request, response, filterChain);

        UsernamePasswordAuthenticationToken auth =
            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(auth);
        assertEquals(userDetails, auth.getPrincipal());
        verify(filterChain).doFilter(request, response);
    }
}
