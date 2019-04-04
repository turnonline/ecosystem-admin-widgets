package biz.turnonline.ecosystem.widget.shared.rest.productbilling;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface VatRateListMapper
        extends ObjectMapper<List<VatRate>>
{
    VatRateListMapper INSTANCE = GWT.create( VatRateListMapper.class );
}
