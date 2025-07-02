package com.vik.filter;

// CLASS For Token based User authentication filter

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vik.service.JWTService;
import com.vik.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter
{
	@Autowired
	private JWTService jwtSer;
	
	@Autowired
	private ApplicationContext context;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		// To get [ Bearer TOKEN ]
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String email = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer "))
		{
			// To extract token from [ Bearer TOKEN ]
			token = authHeader.substring(7);
			email = jwtSer.extractEmail(token);
		}
		
		// USER AUTHENTICATION
		if(email != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(email);
			
			// Validate Token and user authentication
			if(jwtSer.validateToken(token, userDetails))
			{
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
	
		filterChain.doFilter(request, response);
	}

}
