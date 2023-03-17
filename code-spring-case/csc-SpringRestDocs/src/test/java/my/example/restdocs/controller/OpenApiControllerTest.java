package my.example.restdocs.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.example.restdocs.model.BookingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static my.example.restdocs.utils.ApiDocumentUtils.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {com.yagaja.papi.openApi.camp.v1.controller.OpenApiController.class})
@ExtendWith(RestDocumentationExtension.class)
class OpenApiControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUpRestdocs(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(uriModifyingOperationPreprocessor(), prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @Test
    @DisplayName("[RestDocs] 캠핑장 예약 정보 조회")
    void getBookingList() throws Exception {

//        BookingRequest bookingRequest = BookingRequest.builder()
//                .ordererName("홍길동")
//                .phoneNumber("01012341234")
//                .checkInDate("20230101")
//                .build();

        // when & then
//        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/camp/v1/booking?ordererName=홍깅동&phoneNumber=01012341234&checkInDate=20230101")
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/camp/v1/booking")
//                        .accept(String.format("%s;charset=%s", MediaType.APPLICATION_JSON, StandardCharsets.UTF_8.name()))
//                        .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic dfadsfoiuqewmosijasdlfjoasdfjoij")
                                .characterEncoding(StandardCharsets.UTF_8.name())
                                .queryParam("ordererName", "홍길동")
                                .queryParam("phoneNumber", "01012341234")
                                .queryParam("checkInDate", "20230101")
                )
                .andExpect(status().isOk())
//                .andDo(MockMvcRestDocumentationWrapper.document("camp-get-booking",
//                        requestHeaders(
//                                headerWithName("Authorization").description(
//                                        "Basic auth credentials")),
//                        responseHeaders(
////                                headerWithName("X-RateLimit-Limit").description(
////                                        "The total number of requests permitted per period"),
////                                headerWithName("X-RateLimit-Remaining").description(
////                                        "Remaining requests permitted in current period"),
////                                headerWithName("X-RateLimit-Reset").description(
////                                        "Time at which the rate limit period will reset")
//                        )))
                .andDo(print())
                .andDo(MockMvcRestDocumentationWrapper.document("camp-get-booking",
                        "고객의 정보를 이용해 해당 날짜에 입실 하는 고객이 맞는 지 확인하는 API 입니다. " +
                                "리턴값이 배열인 이유는 한 고객이 2개 이상의 사이트는 예약할 수 있기 때문에 배열로 작성됩니다. " ,
                        requestHeaders(
                                headerWithName("Authorization").description(
                                        "Basic auth credentials token")),
                        responseHeaders(
//                                headerWithName("X-RateLimit-Limit").description(
//                                        "The total number of requests permitted per period"),
//                                headerWithName("X-RateLimit-Remaining").description(
//                                        "Remaining requests permitted in current period"),
//                                headerWithName("X-RateLimit-Reset").description(
//                                        "Time at which the rate limit period will reset")
                        ),
                        requestParameters(
                                parameterWithName("ordererName").description("주문자명")
                                        .attributes(key("type").value("String"))
                                        .attributes(key("constraints").value(descriptionsForNameProperty(BookingRequest.class, "ordererName"))),
                                parameterWithName("phoneNumber").description("전화번호")
                                        .attributes(key("type").value("String"))
                                        .attributes(getPhoneFormat())
                                        .attributes(key("constraints").value(descriptionsForNameProperty(BookingRequest.class, "phoneNumber"))),
                                parameterWithName("checkInDate").description("체크인 날짜")
                                        .attributes(key("type").value("String"))
                                        .attributes(getDateFormat())
                                        .attributes(key("constraints").value(descriptionsForNameProperty(BookingRequest.class, "checkInDate")))
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메시지"),
                                fieldWithPath("data.[].orderId").type(JsonFieldType.NUMBER).description("주문번호"),
                                fieldWithPath("data.[].siteName").type(JsonFieldType.STRING).description("사이트명"),
                                fieldWithPath("data.[].orderPrice").type(JsonFieldType.NUMBER).description("주문 금액"),
                                fieldWithPath("data.[].orderPriceSettle").type(JsonFieldType.NUMBER).description("캠핑톡 결제 금액"),
                                fieldWithPath("data.[].orderPriceOffline").type(JsonFieldType.NUMBER).description("오프라인 결제금액"),
                                fieldWithPath("data.[].checkInDate").type(JsonFieldType.STRING).description("체크인 날짜").attributes(getDateFormat()),
                                fieldWithPath("data.[].checkOutDate").type(JsonFieldType.STRING).description("체크아웃 날짜").attributes(getDateFormat())
                        )
                ));


    }
}