package org.ctoolkit.turnonline.widget.shared.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.client.ui.MaterialFooter;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ScaffoldFooter
        extends Composite
{
    private static ScaffoldFooterBarUiBinder binder = GWT.create( ScaffoldFooterBarUiBinder.class );

    interface ScaffoldFooterBarUiBinder
            extends UiBinder<MaterialFooter, ScaffoldFooter>
    {
    }

    public ScaffoldFooter()
    {
        initWidget( binder.createAndBindUi( this ) );

        Style style = getElement().getStyle();
        style.setWidth( 100, Style.Unit.PCT );

        addHandler( event -> onLoad(), ResizeEvent.getType() );
    }
}
