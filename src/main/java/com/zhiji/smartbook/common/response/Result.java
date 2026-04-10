package com.zhiji.smartbook.common.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 统一响应结果
 */
@Data
public class Result<T> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Integer code;
    private String message;
    private T data;
    private String requestId;
    private String timestamp;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage("success");
        result.setData(data);
        result.setRequestId(UUID.randomUUID().toString());
        result.setTimestamp(LocalDateTime.now().format(FORMATTER));
        return result;
    }

    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setRequestId(UUID.randomUUID().toString());
        result.setTimestamp(LocalDateTime.now().format(FORMATTER));
        return result;
    }
}
