package my.example.sulzip.common.res;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class Res<T> {

    private final boolean success;
    private final String message;
    private final String code;
    private final T data;

    public static <T> Res<T> success() {
        return Res.<T>builder()
                .success(ResponseType.SUCCESS.isSuccess())
                .code(ResponseType.SUCCESS.name())
                .message(ResponseType.SUCCESS.getMessage())
                .build();
    }

    public static <T> Res<T> success(T data) {
        return Res.<T>builder()
                .success(ResponseType.SUCCESS.isSuccess())
                .code(ResponseType.SUCCESS.name())
                .message(ResponseType.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    @Getter
    @RequiredArgsConstructor
    public enum ResponseType {
        SUCCESS(true, "성공"),
        FAILURE(false, "실패");

        private final boolean success;
        private final String message;
    }

    /**
     * 성공응답
     * 단건형태의 응답
     * {
     *  success: true,
     *  message: "성공",
     *  code: SUCCESS,
     *  data: {
     *      "memberId": 1,
     *      "memberName: "나패존"
     *  }
     *
     *  목록형태의 응답
     * {
     *  success: true,
     *  message: "성공",
     *  code: SUCCESS,
     *  data: {
     *      content: [
     *          "memberId": 1,
     *          "memberName: "나패존"
     *      ],
     *      pageable: {
     *          totalCount: 100,
     *          size: 10,
     *          page: 0
     *      }
     *  }
     * }
     *
     * 실패응답
     * {
     *  success: false,
     *  message: "존재하는 회원이 없습니다.",
     *  code: FAILURE,
     *  errors: [{
     *      "field": "memberName",
     *      "value": "",
     *      "reason": "필수값입니다."
     *  }, {
     *      "field": "memberAge",
     *      "value": "0",
     *      "reason": "0 이상의 값이어야 합니다."
     *  }]
     */
}