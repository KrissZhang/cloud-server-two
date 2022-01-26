package com.self.cloudserver.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.self.cloudserver.utils.DateTimeUtils;

import java.io.IOException;
import java.util.Date;

public class CustomDateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (date != null) {
            jsonGenerator.writeString(DateTimeUtils.dateFormat.format(date));
        }
    }

    @Override
    public void serializeWithType(Date value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        serialize(value, g, provider);
    }

}
