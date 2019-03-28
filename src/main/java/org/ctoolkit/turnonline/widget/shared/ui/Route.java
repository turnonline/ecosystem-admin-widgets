package org.ctoolkit.turnonline.widget.shared.ui;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public enum Route
{
    INVOICES( "/invoices", 1 ),
    ORDERS( "/orders", 2 ),
    PRODUCTS( "/products", 3 ),
    CONTACTS( "/contacts", 4 ),
    DASHBOARD( "/dashboard" ),
    SETTINGS( "/settings" ),
    LOGOUT( "/logout" );

    private String url;

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

    public String url()
    {
        return url;
    }

    public Integer navBarOrder()
    {
        return navBarOrder;
    }}
