package org.ctoolkit.turnonline.widget.shared.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialSideNavPush;
import org.ctoolkit.turnonline.widget.shared.AppMessages;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ScaffoldNavBar
        extends Composite
{
    private AppMessages messages = AppMessages.INSTANCE;

    private static ScaffoldNavBarUiBinder binder = GWT.create( ScaffoldNavBarUiBinder.class );

    interface ScaffoldNavBarUiBinder
            extends UiBinder<MaterialSideNavPush, ScaffoldNavBar>
    {
    }

    public ScaffoldNavBar()
    {
        initWidget( binder.createAndBindUi( this ) );

        nav().add( newNavLink( messages.labelInvoices(), Route.INVOICES.url(), IconType.ASSIGNMENT ) );
        nav().add( newNavLink( messages.labelOrders(), Route.ORDERS.url(), IconType.SHOPPING_CART ) );
        nav().add( newNavLink( messages.labelProducts(), Route.PRODUCTS.url(), IconType.TABLET_MAC ) );
        nav().add( newNavLink( messages.labelContacts(), Route.CONTACTS.url(), IconType.CONTACT_PHONE ) );
    }

    public void setActive( Route route )
    {
        nav().setActive( route.navBarOrder() );
    }

    protected MaterialLink newNavLink( String text, String href, IconType iconType )
    {
        MaterialLink link = new MaterialLink( text, href );
        link.setIconType( iconType );
        return link;
    }

    private MaterialSideNavPush nav()
    {
        return ( MaterialSideNavPush ) getWidget();
    }
}
