package biz.turnonline.ecosystem.widget.shared.ui;

import com.google.gwt.user.client.Window;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public enum Route
{
    MY_ACCOUNT( "/my-account", "my-account", 1 ),
    SETTINGS( "/my-account", "my-account/settings", 2 ),
    INVOICES( "/billing", "invoices", 3 ),
    ORDERS( "/billing", "orders", 4 ),
    BILLS( "/bills", "bills", 5 ),
    PRODUCTS( "/products", "products", 6 ),
    CONTACTS( "/contacts", "contacts", 7 ),
    PURCHASES( "/purchases", "invoices", 8 ),
    LOGOUT( "/logout" );

    private String url;

    private String fragment;

    private Integer navBarOrder;

    Route( String url )
    {
        this.url = url;
    }

    Route( String url, String fragment, Integer navBarOrder )
    {
        this.url = url;
        this.fragment = fragment;
        this.navBarOrder = navBarOrder;
    }

    public String url()
    {
        return path() + ( fragment != null ? "#" + fragment + ":" : "" );
    }

    public String path()
    {
        String path;
        if ( Window.Location.getPort().equals( "8888" ) )
        {
            path = Window.Location.getProtocol() + "//"
                    + Window.Location.getHost()
                    + url +
                    ".html";
        }
        else
        {
            path = url;
        }

        return path;
    }

    public Integer navBarOrder()
    {
        return navBarOrder;
    }
}
