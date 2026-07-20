package com.pwc.routing.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class CustomRestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CustomRestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request, 0);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

        long startTime = System.currentTimeMillis();

        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            logRequest(requestWrapper);
            logResponse(responseWrapper, duration);

            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String payload = getPayload(request.getContentAsByteArray(), request.getCharacterEncoding());

        logger.info("--> HTTP {} {} | Payload: {}", method, uri, payload.strip());
    }

    private void logResponse(ContentCachingResponseWrapper response, long duration) {
        int status = response.getStatus();
        String payload = getPayload(response.getContentAsByteArray(), response.getCharacterEncoding());

        logger.info("<-- HTTP STATUS {} ({}ms) | Response: {}", status, duration, payload.strip());
    }

    private String getPayload(byte[] buf, String encoding) {
        if (buf == null || buf.length == 0) {
            return "[empty]";
        }
        try {
            return new String(buf, 0, buf.length, encoding);
        } catch (UnsupportedEncodingException e) {
            return "[unknown encoding]";
        }
    }
}