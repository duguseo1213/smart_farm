package com.ssafy.WeCanDoFarm.server.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // common
    SERVER_ERROR(1000,HttpStatus.INTERNAL_SERVER_ERROR,"서버에러가 발생하였습니다"),
    INVALID_INPUT_VALUE(1001, HttpStatus.BAD_REQUEST, "유효하지 않은 입력 값입니다."),

    // Authentication, Authorization
    UNAUTHORIZED(2000, HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    INVALID_TOKEN(2002, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    EXPIRED_TOKEN(2003, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),

    EXPIRED_REFRESH_TOKEN(2005, HttpStatus.UNAUTHORIZED, "만료된 리프레시 토큰입니다."),
    NOT_FOUND_USER(2007, HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_ACCESS_TOKEN(2008, HttpStatus.UNAUTHORIZED, "유효하지 않은 액세스 토큰입니다."),
    ACCESS_DENIED(2010, HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    // S3
    EMPTY_FILE(8000, HttpStatus.BAD_REQUEST, "파일이 비어있습니다."),
    FAIL_TO_DELETE_FILE(8001, HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제에 실패하였습니다."),
    NOT_SUPPORTED_EXTENSION(8002, HttpStatus.BAD_REQUEST, "지원하지 않는 확장자입니다."),
    FAIL_TO_CREATE_FILE(8003, HttpStatus.INTERNAL_SERVER_ERROR, "파일 생성에 실패하였습니다."),

    //Device
    NO_AVAILABLE_DEVICE(9000,HttpStatus.BAD_REQUEST,"사용가능한 기기가 없습니다.");

    private final int code;
    private final HttpStatus status;
    private final String message;
}
