package com.hospital_management.hospital.Security;

import com.google.common.base.Strings;
import com.hospital_management.hospital.Service.UserService;
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

    @Autowired
    private UserService userService;

    JwtUtil jwtUtil = new JwtUtil();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        try {
            String token = jwtUtil.getJwtFromRequest(request);

            if (StringUtils.hasText(token) && jwtUtil.validateToken(token, "secret")) {
                String userName = jwtUtil.getUserNameFromJWTs(token, "secret");

                if (Strings.isNullOrEmpty(userName)) {
                    request.getRequestDispatcher("/error" + "invalid")
                            .forward(request, response);
                }

                UserDetails userDetails = userService.loadByuserName(userName);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            request.getRequestDispatcher("/error" + e.getMessage()).forward(request,
                    response);
        }
    }
}
