package com.boot.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component //스프링에 등록해야함
public class RequestLogfilter implements Filter{

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

    //ContentCachingResponseWrapper는 Spring에서 제공하는 유틸리티 클래스로, 서블릿 응답(HttpServletResponse)의 내용을 캐싱하여 여러 번 읽을 수 있도록 도와줍니다. 기본적으로 HttpServletResponse는 한 번 스트림에 쓰면 다시 읽을 수 없습니다. ContentCachingResponseWrapper는 이런 문제를 해결해줍니다.
    ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    log.info("[filter][REQUEST] [{}] {} {}", httpRequest.getMethod(), httpRequest.getRequestURI(), httpRequest.getQueryString() != null ? httpRequest.getQueryString() : "");

    long start = System.currentTimeMillis();
    chain.doFilter(requestWrapper, responseWrapper);
    long end = System.currentTimeMillis();

    log.info("[filter][RESPONSE] [{}] {} {} - {}ms", httpRequest.getMethod(), httpRequest.getRequestURI(), responseWrapper.getStatus(), (end-start)/1000.0 );

    //ContentCachingResponseWrapper는 서블릿 응답 스트림을 감싸서 요청이 처리되는 동안 응답 데이터를 캐싱합니다. 이렇게 캐싱된 데이터를 여러 번 읽을 수 있게 해주는데, 응답이 클라이언트로 전달될 때 copyBodyToResponse() 메서드를 호출해 저장된 응답을 실제 응답 객체로 다시 전달합니다.
    //중요한 메서드로, 이 메서드를 호출하지 않으면 응답 본문이 클라이언트에게 전송되지 않기 때문에 반드시 호출해야 합니다.
    responseWrapper.copyBodyToResponse();
  }

}
