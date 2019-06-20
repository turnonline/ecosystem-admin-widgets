package biz.turnonline.ecosystem.widget.shared.rest;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;

/**
 * JSON convenient methods to serialize and deserialize objects.
 *
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

    public static <T> T clone( T object, ObjectMapper<T> mapper )
    {
        String string = JSON.stringify( object, mapper );
        return JSON.parse( string, mapper );
    }
}
