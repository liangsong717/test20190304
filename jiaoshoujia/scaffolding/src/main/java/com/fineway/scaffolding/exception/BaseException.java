package com.fineway.scaffolding.exception;

import java.util.Map;

public class BaseException extends RuntimeException {
    private static final long                serialVersionUID = -1624206726332133105L;
    private              String              code;
    private              String              message;
    private              Map<String, Object> attrs;

    public BaseException() {
        this.code = "400";
        this.message = "处理失败";
    }

    public BaseException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseException(String code, String message, Map<String, Object> attrs) {
        this.code = code;
        this.message = message;
        this.attrs = attrs;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }
}
