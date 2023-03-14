package my.example.sulzip.common.utils;

import lombok.SneakyThrows;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class LinkUtil {

    public static URI location(Long id) {
        String path = (id == null) ? "" : "/" + id;
        return getUri(path);
    }

    public static URI location(String token) {
        String path = (token == null) ? "" : "/" + token;
        return getUri(path);
    }

    @SneakyThrows
    private static URI getUri(String path) {
        String requestUri = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        return new URI(requestUri.substring(requestUri.indexOf("/", 8)) + path);
    }
}