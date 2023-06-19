package my.example.restapi.client.jsonConverter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Optional;

public class OptionalSerializer extends StdSerializer<Optional<?>> {


    public OptionalSerializer() {
        super((Class<Optional<?>>) (Class<?>) Optional.class);
    }

    @Override
    public void serialize(Optional<?> optional, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (optional.isPresent()) {
            jsonGenerator.writeObject(optional.get());
        }
        else {
            jsonGenerator.writeNull();
        }
    }

    @Override
    public boolean isEmpty(SerializerProvider provider, Optional<?> value) {
        return value == null || !value.isPresent();
    }
}
