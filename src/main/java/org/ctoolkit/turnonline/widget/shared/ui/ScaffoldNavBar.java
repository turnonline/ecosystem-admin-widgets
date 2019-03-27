package org.ctoolkit.turnonline.widget.shared.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.client.ui.MaterialSideNavPush;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ScaffoldNavBar
        extends Composite
{
    private static ScaffoldNavBarUiBinder binder = GWT.create( ScaffoldNavBarUiBinder.class );

    public enum Item
    {
        INVOICES,
        ORDERS,
        PRODUCTS,
        CONTACTS
    }

    interface ScaffoldNavBarUiBinder
            extends UiBinder<MaterialSideNavPush, ScaffoldNavBar>
    {
    }

    public ScaffoldNavBar()
    {
        initWidget( binder.createAndBindUi( this ) );
    }

    public void setActive( Item item )
    {
        MaterialSideNavPush nav = ( MaterialSideNavPush ) getWidget();
        nav.setActive( item.ordinal() + 1 );
    }
}
