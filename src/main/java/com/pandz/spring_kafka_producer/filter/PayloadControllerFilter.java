package com.pandz.spring_kafka_producer.filter;

import com.pandz.spring_kafka_producer.util.LogUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayloadControllerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
        } else {
            this.doFilterWrapped(LogUtil.wrapRequest(request), LogUtil.wrapResponse(response), filterChain);
        }
    }

    protected void doFilterWrapped(ContentCachingRequestWrapper httpServletRequest, ContentCachingResponseWrapper httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        long duration = 0;
        try {
            long startTime = System.currentTimeMillis();
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            duration = System.currentTimeMillis() - startTime;
        } catch (Exception ex) {
            log.error("Error when do filter wrapper");
        } finally {
            String payloadRequest = LogUtil.logRequest(httpServletRequest.getContentAsByteArray());
            String payloadResponse = LogUtil.logResponse(httpServletResponse);
            LogUtil.logging(httpServletRequest, httpServletResponse, payloadRequest, payloadResponse, duration);

            httpServletResponse.copyBodyToResponse();
        }
    }
}
