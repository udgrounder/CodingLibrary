package my.example.restdocs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 예약 정보 조회 Request 객체
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotBlank
    @Size(message="이름은 최대 16자 입니다.", min=1, max =16)
    private String ordererName; // 주문자명

    @NotBlank
    @Size(min=10, max =11)
    @Pattern(message = "전화번호는 숫자만 입력해 주셔야 합니다.", regexp = "([0-9]{10,11})")
    private String phoneNumber; // 주문자 전화 번호

    @NotBlank
    @Pattern(message = "입실일자는 YYYYMMDD 형태의 숫자이어야 합니다.", regexp = "([0-9]{8})")
    private String checkInDate;     // 입실일자  YYYYMMDD
}
