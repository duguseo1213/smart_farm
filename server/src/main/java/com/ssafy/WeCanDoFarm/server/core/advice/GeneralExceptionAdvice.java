package com.ssafy.WeCanDoFarm.server.core.advice;

import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import com.ssafy.WeCanDoFarm.server.core.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ErrorResponse exception(Exception e) {
        if (!e.getMessage().contains("No static resource")) {
            log.error("message: {}", e.getMessage(), e);
        }
        log.info("message: {}", e.getMessage());
        return ErrorResponse.of(ErrorCode.SERVER_ERROR);
    }
}
