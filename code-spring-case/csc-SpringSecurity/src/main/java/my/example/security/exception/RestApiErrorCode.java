package my.example.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestApiErrorCode {

    ///////////////////////
    // Common Error & System Error
    COMMON_ERROR(500,"C-1000","알수 없는 에러가 발생 하였습니다."),

    ///////////////////////
    // Authorize Error
    AUTH_ERROR(401,"A-2000","인증 에러가 발생 하였습니다."),            // 인증 단계에서 벌생하는 기본 에러
    ACCOUNT_ERROR(401,"A-2001","계정이 사용 불가능한 상태 입니다."),
    ACCOUNT_INACTIVE_ERROR(401,"A-2002","계정이 비활성화 상태 입니다."),
    ACCOUNT_LOCKED_ERROR(401,"A-2003","계정이 잠금 상태 입니다."),
    ACCOUNT_BLOCKED_ERROR(401,"A-2004","계정이 정지 상태 입니다."),

    ///////////////////////
    // - TOKEN error
    AUTH_TOKEN_ERROR(401,"A-2100","토큰 인증 에러가 발생 하였습니다."),
    AUTH_TOKEN_NOT_EXIST_ERROR(401,"A-2101","토큰이 존재 하지 않습니다."),
    AUTH_TOKEN_FORMAT_INVALID_ERROR(401,"A-2102","토큰 포맷이 잘못 되었습니다."),
    AUTH_TOKEN_INVALID_ERROR(401,"A-2103","비 정상 토큰 정보 입니다."),
    AUTH_TOKEN_INFO_MISMATCH_ERROR(401,"A-2104","토큰 정보가 일치하지 않습니다."),
    AUTH_TOKEN_INACTIVE_ERROR(401,"A-2105","사용이 비 활성화된 토큰입니다."),
    AUTH_TOKEN_EXPIRED_ERROR(401,"A-2106","토큰이 만료 되었습니다."),
    AUTH_TOKEN_BLOCKED_ERROR(401,"A-2107","사용이 중지된 토큰 입니다."),

    ACCESS_DENIED_ERROR(403,"A-2201","접근 권한이 없습니다."),

    ///////////////////////
    // Resource Error
    RESOURCE_NOT_FOUND(404,"R-3001","요청 하신 리소스가 없습니다."),

    ///////////////////////
    // Business Error
    BUSINESS_ERROR(500,"B-4000","API 처리 중 문제가 발생 하였습니다."),

    // - data, format, validation
    INVALID_PARAMETER_ERROR(500,"B-4100","파라메터가 잘못되었습니다."),

    // - process noti


    ;

    private int httpStatus;
    private String errorCode;
    private String message;
}
