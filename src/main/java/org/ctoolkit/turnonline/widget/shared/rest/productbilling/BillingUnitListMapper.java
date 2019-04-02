package org.ctoolkit.turnonline.widget.shared.rest.productbilling;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface BillingUnitListMapper
        extends ObjectMapper<List<BillingUnit>>
{
    BillingUnitListMapper INSTANCE = GWT.create( BillingUnitListMapper.class );
}
