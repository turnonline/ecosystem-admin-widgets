package biz.turnonline.ecosystem.widget.shared.ui;

import com.google.gwt.user.client.Window;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public enum Route
{
    MY_ACCOUNT( "/my-account", 1 ),
    INVOICES( "/invoices", "invoices", "edit-invoice", 2 ),
    ORDERS( "/orders", "orders", "edit-order", 3 ),
    PRODUCTS( "/products", "products", "edit-product", 4 ),
    CONTACTS( "/contacts", "contacts", "edit-contact", 5 ),
    DASHBOARD( "/dashboard" ),
    LOGOUT( "/logout" );

    private String url;

    private String listToken;

    private String detailToken;

    private Integer navBarOrder;

    Route( String url )
    {
        this.url = url;
    }

    Route( String url, Integer navBarOrder )
    {
        this.url = url;
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
        // super dev mode - local widget development only
        if ( Window.Location.getPort().equals( "8888" ) )
        {
            return Window.Location.getProtocol() + "//"
                    + Window.Location.getHost()
                    + url + ".html";
        }

        // wrapped by portal or on remote server
        return url;
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
