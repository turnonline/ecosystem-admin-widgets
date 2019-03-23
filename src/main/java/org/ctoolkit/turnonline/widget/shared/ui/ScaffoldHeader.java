package org.ctoolkit.turnonline.widget.shared.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.client.ui.MaterialHeader;
import gwt.material.design.client.ui.MaterialNavBrand;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ScaffoldHeader
        extends Composite
{
    private static ScaffoldHeaderUiBinder binder = GWT.create( ScaffoldHeaderUiBinder.class );

    interface ScaffoldHeaderUiBinder
            extends UiBinder<MaterialHeader, ScaffoldHeader>
    {
    }

    @UiField
    MaterialNavBrand title;

    public ScaffoldHeader()
    {
        initWidget( binder.createAndBindUi( this ) );
    }

    public MaterialNavBrand getNavBrand()
    {
        return title;
    }
}
