package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        try{
            //logging request details
            logRequestDetails(request);

            filterChain.doFilter(request, response);
            //logging response details
            logResponseDetails(response);
        }
        catch(Exception e){
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
    private void logRequestDetails(HttpServletRequest request) {
        logger.info("Incoming Request: {} {}", request.getMethod(), request.getRequestURI());

        logger.debug("Request Header: {}",
                Collections
                        .list(request.getHeaderNames())
                        .stream()
                        .collect(Collectors.toMap(h -> h, s -> request.getHeader(s))));

        logger.info("User Principal: {}", request.getUserPrincipal());
    }

    private void logResponseDetails(HttpServletResponse response) {
        logger.info("Outgoing Response: {}", response.getStatus());

        logger.info("Response Headers: {}", response.getHeaderNames());

        logger.info("Response Content Type: {}", response.getContentType());
    }
}
