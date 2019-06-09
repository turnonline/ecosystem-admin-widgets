/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.shared;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
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
            throw new NullPointerException( String.valueOf( errorMessage ) );
        }
        return reference;
    }

    public static boolean isNullOrEmpty( @Nullable String string )
    {
        return string == null || string.isEmpty();
    }
}
