package com.pandz.spring_kafka_producer.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pandz.spring_kafka_producer.constant.GeneralConstant.ENTER_SPACE;

@Slf4j
public class LogUtil {
    private LogUtil() {}

    private static final String DOUBLE_LINE = "====================================================================================";
    private static final String REQUEST_LINE = "---------------------------------- REQUEST -----------------------------------------";
    private static final String RESPONSE_LINE = "---------------------------------- RESPONSE ----------------------------------------";

    public static String logRequestHeader(ContentCachingRequestWrapper request) {
        List<String> list = Collections.list(request.getHeaderNames());
        Map<Object, Object> map = list.stream().collect(Collectors.toMap(
            String::valueOf,
            s -> String.valueOf(s).equals("authorization") || String.valueOf(s).equals("key") ? "*************************" : request.getHeader(String.valueOf(s))));
        return JsonUtil.objectAsStringJson(map);
    }

    public static String logRequest(byte[] body) {
        return new String(body, StandardCharsets.UTF_8);
    }

    public static String logResponse(ContentCachingResponseWrapper response) {
        return new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
    }

    public static String logResponseHeader(ContentCachingResponseWrapper response) {
        List<String> list = new ArrayList<>(response.getHeaderNames()).stream().filter(l -> !l.equalsIgnoreCase("Vary")).toList();
        Map<Object, Object> map = list.stream().collect(Collectors.toMap(
            String::valueOf,
            s -> String.valueOf(s).equals("authorization") || String.valueOf(s).equals("key") ? "*************************" : response.getHeader(String.valueOf(s))));
        return JsonUtil.objectAsStringJson(map);
    }

    public static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        return request instanceof ContentCachingRequestWrapper ? (ContentCachingRequestWrapper) request : new ContentCachingRequestWrapper(request);
    }

    public static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        return response instanceof ContentCachingResponseWrapper ? (ContentCachingResponseWrapper) response : new ContentCachingResponseWrapper(response);
    }

    public static void logging(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper,
                               String payloadRequest, String payloadResponse, long duration) {
        StringBuilder sb = new StringBuilder(ENTER_SPACE);
        String uriPath = requestWrapper.getRequestURI();
        String queryString = requestWrapper.getQueryString();
        if (queryString != null) {
            uriPath += "?" + queryString;
        }

        sb.append(DOUBLE_LINE).append(ENTER_SPACE);
        sb.append(String.format("%s : {%s}", requestWrapper.getMethod(), uriPath)).append(ENTER_SPACE);
        sb.append(REQUEST_LINE).append(ENTER_SPACE);
        sb.append(String.format("Request Header : {%s}", logRequestHeader(requestWrapper))).append(ENTER_SPACE);
        sb.append(String.format("Request Body : {%s}", payloadRequest)).append(ENTER_SPACE);
        sb.append(RESPONSE_LINE).append(ENTER_SPACE);
        sb.append(String.format("Response Header : {%s}", logResponseHeader(responseWrapper))).append(ENTER_SPACE);
        sb.append(String.format("Response Body : {%s}", payloadResponse)).append(ENTER_SPACE);
        sb.append(String.format("Duration: {%s}", duration + "ms")).append(ENTER_SPACE);
        sb.append(String.format("Response Status: %s", responseWrapper.getStatus())).append(ENTER_SPACE);
        sb.append(DOUBLE_LINE);
        log.info(sb.toString());
    }
}
