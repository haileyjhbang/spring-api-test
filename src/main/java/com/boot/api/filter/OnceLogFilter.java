package com.boot.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OnceLogFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

    log.info("[once-filter][REQUEST] [{}] {}{}", request.getMethod(), request.getRequestURI(),
        request.getQueryString() != null ? request.getQueryString() : "");
    long start = System.currentTimeMillis();

    filterChain.doFilter(requestWrapper, response);

    long end = System.currentTimeMillis();
    log.info("[once-filter][RESPONSE] [{}] {} {} - {}ms", request.getMethod(), request.getRequestURI(),
        responseWrapper.getStatus(),
        (end - start) / 1000.0);

    responseWrapper.copyBodyToResponse();
  }

}
