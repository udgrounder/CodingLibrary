package my.example.restapi.client.jsonConverter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Optional;

public class OptionalDeserializer extends JsonDeserializer<Optional<?>> {
    @Override
    public Optional<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentToken() == null) {
            return Optional.empty();
        } else {
            return Optional.of(jsonParser.readValueAs(Object.class));
        }
    }
}