package biz.turnonline.ecosystem.widget.shared.rest.accountsteward;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface CountryListMapper
        extends ObjectMapper<List<Country>>
{
    CountryListMapper INSTANCE = GWT.create( CountryListMapper.class );
}
