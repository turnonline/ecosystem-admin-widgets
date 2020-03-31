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

package biz.turnonline.ecosystem.widget.shared;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gwt.core.client.GWT;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings( "ALL" )
public class Preconditions
{
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull( T reference )
    {
        if ( reference == null )
        {
            throw new NullPointerException();
        }
        return reference;
    }

    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T reference, @Nullable Object errorMessage )
    {
        if ( reference == null )
        {
            String error = String.valueOf( errorMessage );
            NullPointerException exception = new NullPointerException( error );
            GWT.log( error, exception );
            throw exception;
        }
        return reference;
    }

    public static boolean isNullOrEmpty( @Nullable String string )
    {
        return string == null || string.isEmpty();
    }
}
