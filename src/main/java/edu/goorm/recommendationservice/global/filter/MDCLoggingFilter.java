package edu.goorm.recommendationservice.global.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class MDCLoggingFilter implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;

    try {
      // 1. 고유한 traceId 생성
      String traceId = UUID.randomUUID().toString();
      MDC.put("traceId", traceId);

      // 2. userId 추출 (JWT 또는 Header 기반)
      String userId = request.getHeader("X-USER-Email"); // 없으면 null
      if (userId != null && !userId.isBlank()) {
        MDC.put("userId", userId);
      }

      chain.doFilter(req, res);
    } finally {
      // 꼭 클리어! (스레드 풀 사용 시 중요)
      MDC.clear();
    }
  }
}
