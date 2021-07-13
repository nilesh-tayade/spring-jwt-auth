package com.test.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetails customUserDetails;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("In auth Filter");
		String requestTokenHeader = request.getHeader("Authorization");
		String username =null;
		String  jwtToken =null;
		System.out.println(requestTokenHeader);
		
		if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer "))
		{
			System.out.println("Checking header");
			
			jwtToken = requestTokenHeader.substring(7);
			
			System.out.println("In auth Filter token : "+jwtToken);

			try {
				
				username = this.jwtUtil.extractUsername(jwtToken);
				System.out.println(username);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Invalid Token");
		}
		
	
	
	if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
	{
		UserDetails userDetails=	this.customUserDetails.loadUserByUsername(username);
		System.out.println("In auth Filter Userdetails : "+userDetails);
		
		if (jwtUtil.validateToken(jwtToken, userDetails)) {

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
	
    filterChain.doFilter(request, response);  
	
	
	
	}

}
