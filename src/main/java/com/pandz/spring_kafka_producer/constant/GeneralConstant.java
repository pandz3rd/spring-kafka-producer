package com.pandz.spring_kafka_producer.constant;

public class GeneralConstant {
    private GeneralConstant() {}

    // TRACE
    public static final String TRACE_ID = "traceId";

    // RC
    public static final String RC_CODE_SUCCESS = "00";
    public static final String RC_CODE_DECLINE = "66";
    public static final String RC_CODE_SYSTEM_ERROR = "99";

    // MESSAGE
    public static final String ERROR_MESSAGE_SUCCESS = "Success";
    public static final String ERROR_MESSAGE_SYSTEM_ERROR = "Failed";

    // DESCRIPTION
    public static final String ERROR_DESCRIPTION_SYSTEM_ERROR = "Error adalah warna dalam kehidupan";

    // LOG
    public static final String ENTER_SPACE = "\n";

    // KAFKA
    public static final String TOPIC_GENERAL = "message-string-topic";
    public static final String TOPIC_CUSTOMER = "customer-string-topic";
}
