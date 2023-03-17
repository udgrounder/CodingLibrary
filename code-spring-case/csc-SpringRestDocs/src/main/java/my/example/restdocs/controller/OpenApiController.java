package com.yagaja.papi.openApi.camp.v1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.example.restdocs.common.restapi.ApiResponse;
import my.example.restdocs.model.BookingRequest;
import my.example.restdocs.model.BookingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/camp/v1")
public class OpenApiController {

    /**
     * 고객의 예약 정보를 받아서 리턴한다.
     * @param bookingRequest
     */
//    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/booking")
    public ApiResponse<List<BookingResponse>> getBookingList (@ModelAttribute @Valid BookingRequest bookingRequest) {

        BookingResponse bookingResponse = new BookingResponse(1L, "사이트01번", 20000, 10000, 10000,"20230101","20230102");
        BookingResponse bookingResponse2 = new BookingResponse(2L,"사이트02번", 30000, 20000, 10000,"20230101","20230102");

        return  ApiResponse.success(List.of(bookingResponse,bookingResponse2));
    }

}
