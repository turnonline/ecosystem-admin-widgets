package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.Resources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialSideNavPush;

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

    @UiField
    MaterialNavBrand logoLink;

    @UiField
    MaterialImage logo;

    public ScaffoldNavBar()
    {
        initWidget( binder.createAndBindUi( this ) );

        nav().add( newNavLink( messages.labelMyAccount(), Route.MY_ACCOUNT.url(), IconType.PERSON ) );
        nav().add( newNavLinkWithSeparator( messages.labelSettings(), Route.SETTINGS.url(), IconType.SETTINGS ) );
        nav().add( newNavLink( messages.labelInvoices(), Route.INVOICES.url(), IconType.ASSIGNMENT ) );
        nav().add( newNavLink( messages.labelOrders(), Route.ORDERS.url(), IconType.ASSIGNMENT_TURNED_IN ) );
        nav().add( newNavLink( messages.labelProducts(), Route.PRODUCTS.url(), IconType.TABLET_MAC ) );
        nav().add( newNavLinkWithSeparator( messages.labelContacts(), Route.CONTACTS.url(), IconType.CONTACT_PHONE ) );
        nav().add( newNavLink( messages.labelLogout(), Route.LOGOUT.url(), IconType.POWER_SETTINGS_NEW ) );

        // logo
        String logoUrl = Configuration.get().getLogo();
        if ( logoUrl == null || "".equals( logoUrl.trim() ) )
        {
            logoUrl = Resources.INSTANCE.logo().getSafeUri().asString(); // fallback to turnonline logo
        }
        logo.setUrl( logoUrl );

        logoLink.addStyleName( "valign-wrapper" );
        logoLink.getElement().getStyle().setDisplay( Style.Display.FLEX );
    }

    public void setActive( Route route )
    {
        nav().setActive( route.navBarOrder() );
    }

    protected MaterialLink newNavLink( String text, String href, IconType iconType )
    {
        MaterialLink link = new MaterialLink( text );
        link.setHref( href );
        link.setIconType( iconType );
        return link;
    }

    protected MaterialLink newNavLinkWithSeparator( String text, String href, IconType iconType )
    {
        MaterialLink link = new MaterialLink( text );
        link.setHref( href );
        link.setIconType( iconType );
        link.setSeparator( true );
        return link;
    }

    private MaterialSideNavPush nav()
    {
        return ( MaterialSideNavPush ) getWidget();
    }
}
