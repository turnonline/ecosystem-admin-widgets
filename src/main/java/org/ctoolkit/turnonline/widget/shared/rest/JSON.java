package org.ctoolkit.turnonline.widget.shared.rest;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class JSON
{
    private static JsonDeserializationContext JSON_DESERIALIZATION_CTX = JsonDeserializationContext
            .builder()
            .failOnUnknownProperties( false )
            .build();

    public static <T> String stringify( T object, ObjectMapper<T> objectMapper )
    {
        return objectMapper.write( object );
    }

    public static <T> T parse( String json, ObjectMapper<T> objectMapper )
    {
        return objectMapper.read( json, JSON_DESERIALIZATION_CTX );
    }
}
