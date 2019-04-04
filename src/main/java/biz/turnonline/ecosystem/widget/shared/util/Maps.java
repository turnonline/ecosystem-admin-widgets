package biz.turnonline.ecosystem.widget.shared.util;

import gwt.material.design.incubator.client.google.addresslookup.js.options.PlaceResult;
import gwt.material.design.incubator.client.google.addresslookup.js.options.result.GeocoderAddressComponent;

import java.util.Arrays;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Maps
{
    public static String findAddressComponent( PlaceResult place, String... types )
    {
        GeocoderAddressComponent component = Arrays.stream( place.getAddressComponents() )
                .filter( addressComponent -> Arrays.stream( addressComponent.getTypes() )
                        .anyMatch( s -> Arrays.asList( types ).contains( s ) ) )
                .findFirst()
                .orElse( null );

        if ( component != null )
        {
            return component.getShortName();
        }

        return null;
    }
}
