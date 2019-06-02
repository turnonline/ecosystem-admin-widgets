package biz.turnonline.ecosystem.widget.shared.ui;

import com.google.gwt.user.client.Window;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public enum Route
{
    MY_ACCOUNT( "/my-account", "my-account:", 1 ),
    SETTINGS( "/my-account", "my-account/settings:", 2 ),
    INVOICES( "/invoices", "invoices", "edit-invoice", 3 ),
    ORDERS( "/orders", "orders", "edit-order", 4 ),
    PRODUCTS( "/products", "products", "edit-product", 5 ),
    CONTACTS( "/contacts", "contacts", "edit-contact", 6 ),
    DASHBOARD( "/dashboard" ),
    LOGOUT( "/logout" );

    private String url;

    private String fragment;

    private String listToken;

    private String detailToken;

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

    Route( String url, String listToken, String detailToken, Integer navBarOrder )
    {
        this.url = url;
        this.listToken = listToken;
        this.detailToken = detailToken;
        this.navBarOrder = navBarOrder;
    }

    public String url()
    {
        String lUrl;
        // super dev mode - local widget development only
        if ( Window.Location.getPort().equals( "8888" ) )
        {
            lUrl = Window.Location.getProtocol() + "//"
                    + Window.Location.getHost()
                    + url +
                    ".html";
        }
        else
        {
            lUrl = url;
        }

        // wrapped by portal or on remote server
        return lUrl + ( fragment != null ? "#" + fragment : "" );
    }

    public String getListToken()
    {
        return listToken;
    }

    public String getDetailToken()
    {
        return detailToken;
    }

    public Integer navBarOrder()
    {
        return navBarOrder;
    }
}
