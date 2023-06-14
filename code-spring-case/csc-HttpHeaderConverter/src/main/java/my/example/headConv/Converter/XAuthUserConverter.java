package my.example.headConv.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.example.headConv.model.XAuthUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class XAuthUserConverter implements Converter<String, XAuthUser> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public XAuthUser convert(String source) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(source, XAuthUser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
