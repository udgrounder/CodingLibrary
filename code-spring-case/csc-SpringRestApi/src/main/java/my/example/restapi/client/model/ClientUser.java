package my.example.restapi.client.model;

import lombok.*;

public class ClientUser {

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String resCode;
        private String resMsg;
    }



}
