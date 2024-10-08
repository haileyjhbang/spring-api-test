package com.boot.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

    log.info("[interceptor][REQUEST] [{}] {}{}", request.getMethod(), request.getRequestURI(),
        request.getQueryString() != null ? request.getQueryString() : "");

    request.setAttribute("requestWrapper", requestWrapper);
    request.setAttribute("responseWrapper", responseWrapper);
    request.setAttribute("start", System.currentTimeMillis());
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    long startTime = (long) request.getAttribute("start");
    long endTime = System.currentTimeMillis();
    double duration = (endTime - startTime) / 1000.0;

    ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request.getAttribute("requestWrapper");
    ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) request.getAttribute("responseWrapper");

    log.info("[interceptor][RESPONSE] [{}] {} {} - {} ms", request.getMethod(), request.getRequestURI(), responseWrapper.getStatus(), duration);

    responseWrapper.copyBodyToResponse();
  }
}
