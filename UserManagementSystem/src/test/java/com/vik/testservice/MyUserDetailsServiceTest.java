package com.vik.testservice;

import com.vik.entity.MyUser;
import com.vik.model.UserPrinciple;
import com.vik.repository.IUserManagementRepo;
import com.vik.service.MyUserDetailsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MyUserDetailsServiceTest {

    @Mock
    private IUserManagementRepo repo;

    @InjectMocks
    private MyUserDetailsService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_WhenUserExists() {
        MyUser user = new MyUser("vik@gmail.com", "pass123", "ROLE_USER");
        when(repo.findByEmail("vik@gmail.com")).thenReturn(user);

        UserDetails result = service.loadUserByUsername("vik@gmail.com");

        assertNotNull(result);
        assertEquals("vik@gmail.com", result.getUsername());
        assertTrue(result instanceof UserPrinciple);
    }

    @Test
    void testLoadUserByUsername_WhenUserNotFound() {
        when(repo.findByEmail("notfound@gmail.com")).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () ->
            service.loadUserByUsername("notfound@gmail.com")
        );

        assertEquals("User not found", exception.getMessage());
    }
}
