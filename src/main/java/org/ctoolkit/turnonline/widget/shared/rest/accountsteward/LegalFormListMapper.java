package org.ctoolkit.turnonline.widget.shared.rest.accountsteward;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface LegalFormListMapper
        extends ObjectMapper<List<LegalForm>>
{
    LegalFormListMapper INSTANCE = GWT.create( LegalFormListMapper.class );
}
