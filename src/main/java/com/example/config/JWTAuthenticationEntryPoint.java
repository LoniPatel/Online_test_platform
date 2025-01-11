package com.example.config;

import com.example.dto.ResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(401);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus(String.valueOf(401));
        responseDTO.setMessage("Invalid User");
        String responseObject= """
                {
                "status": "${status}",
                "message":"${message}"
                }""";
        responseObject=responseObject.replace("${status}",responseDTO.getStatus()).replace("${message}",responseDTO.getMessage());
        response.getOutputStream().println(responseObject);
    }
}
