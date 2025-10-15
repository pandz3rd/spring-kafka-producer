package com.pandz.spring_kafka_producer.util;

import com.pandz.spring_kafka_producer.model.internal.Metadata;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class MetadataUtil {
    private MetadataUtil() {}

    public static Metadata constructMetadata(HttpServletRequest httpServletRequest) {
        return Metadata.builder()
            .ipAddress(StringUtils.isEmpty(httpServletRequest.getHeader("X-Forwarded-For")) ? httpServletRequest.getRemoteAddr() : httpServletRequest.getHeader("X-Forwarded-For"))
            .userAgent(httpServletRequest.getHeader("User-Agent"))
            .appVersion(httpServletRequest.getHeader("app_version"))
            .channel(httpServletRequest.getHeader("channel"))
            .signature(httpServletRequest.getHeader("Signature"))
            .timestamp(httpServletRequest.getHeader("request-at"))
            .requestId(httpServletRequest.getHeader("request-id"))
            .bearerToken(httpServletRequest.getHeader("Authorization"))
            .build();
    }
}
