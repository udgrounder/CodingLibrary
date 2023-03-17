package my.example.restdocs.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 예약 정보 조회 Response 객체
 */
@Data
@AllArgsConstructor
public class BookingResponse {

    private final Long orderId;             // 주문번호
    private final String siteName;          // 사이트명
    private final Integer orderPrice;       // 주문 금액
    private final Integer orderPriceSettle; // 캠핑톡 결제 금액
    private final Integer orderPriceOffline;// 오프라인 결제 금액
    private final String checkInDate;       // 체크인 날짜
    private final String checkOutDate;      // 체크아웃 날짜


}
