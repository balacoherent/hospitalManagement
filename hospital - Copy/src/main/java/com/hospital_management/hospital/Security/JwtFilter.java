package com.hospital_management.hospital.Security;

import com.google.common.base.Strings;
import com.hospital_management.hospital.Service.UserService;
import com.hospital_management.hospital.Util.Constants;
import com.hospital_management.hospital.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtFilter extends OncePerRequestFilter {

    //@Autowired
  //  private UserRoleRepository userRoleRepository;

    JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    private UserService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String SIGNING_KEY = "DOC-COHERENT";

        try {
            String token = jwtUtil.getJwtFromRequest(request);

            if (StringUtils.hasText(token) && jwtUtil.validateToken(token, SIGNING_KEY)) {
                String username = jwtUtil.getUserNameFromJWTs(token, SIGNING_KEY);
                if (Strings.isNullOrEmpty(username)) {
                    request.getRequestDispatcher("/error?errorCode=0&errorMessage=" + Constants.JWT_USER_MESSAGE)
                            .forward(request, response);
                }
                UserDetails userDetails = userDetailsService.loadByUserName(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            request.getRequestDispatcher("/error?errorCode=0&errorMessage=" + ex.getMessage()).forward(request,
                    response);
        }
    }
}