/*
 *  Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
