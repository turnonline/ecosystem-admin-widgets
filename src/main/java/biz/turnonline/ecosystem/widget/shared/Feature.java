package biz.turnonline.ecosystem.widget.shared;

import java.util.Arrays;
import java.util.Optional;

/**
 * Holds a info about available feature
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class Feature
{
    public enum Name
    {
        PAYMENT_PROCESSOR_API_ENABLED( "FEATURE__PAYMENT_PROCESSOR_API_ENABLED" );

        private final String key;

        Name( String key )
        {
            this.key = key;
        }

        public static Optional<Name> fromKey( String key)
        {
            return Arrays.stream( values() ).filter(  value -> value.key.equals( key )  ).findFirst();
        }
    }

    private Name name;

    private boolean enabled;

    public Feature( Name name, boolean enabled )
    {
        this.name = name;
        this.enabled = enabled;
    }

    public Name getName()
    {
        return name;
    }

    public boolean isEnabled()
    {
        return enabled;
    }
}
