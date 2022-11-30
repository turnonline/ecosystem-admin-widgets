/*
 *  Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.Feature;
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
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ScaffoldNavBar
        extends Composite
{
    private static ScaffoldNavBarUiBinder binder = GWT.create( ScaffoldNavBarUiBinder.class );

    @UiField
    MaterialNavBrand logoLink;

    @UiField
    MaterialImage logo;

    private AppMessages messages = AppMessages.INSTANCE;

    public ScaffoldNavBar()
    {
        initWidget( binder.createAndBindUi( this ) );

        nav().add( newNavLink( messages.labelDashboard(), Route.DASHBOARD.url(), IconType.DASHBOARD ) );
        nav().add( newNavLink( messages.labelMyAccount(), Route.MY_ACCOUNT.url(), IconType.PERSON ) );
        nav().add( newNavLinkWithSeparator( messages.labelSettings(), Route.SETTINGS.url(), IconType.SETTINGS ) );
        nav().add( newNavLink( messages.labelInvoices(), Route.INVOICES.url(), IconType.ASSIGNMENT ) );
        nav().add( newNavLink( messages.labelOrders(), Route.ORDERS.url(), IconType.ASSIGNMENT_TURNED_IN ) );
        nav().add( newNavLink( messages.labelProducts(), Route.PRODUCTS.url(), IconType.TABLET_MAC ) );
        nav().add( newNavLinkWithSeparator( messages.labelContacts(), Route.CONTACTS.url(), IconType.CONTACT_PHONE ) );
//        nav().add( newNavLink( messages.labelPurchases(), Route.PURCHASES.url(), IconType.LOCAL_OFFER ) );
        nav().add( newNavLink( messages.labelBills(), Route.BILLS.url(), IconType.RECEIPT ) );
        nav().add( newNavLinkWithSeparator( messages.labelExpenses(), Route.EXPENSES.url(), IconType.ASSIGNMENT ) );
        if (Configuration.get().isFeatureEnabled( Feature.Name.PAYMENT_PROCESSOR_API_ENABLED ))
        {
            nav().add( newNavLinkWithSeparator( messages.labelTransactions(), Route.TRANSACTIONS.url(), IconType.COMPARE_ARROWS ) );
        }
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

    interface ScaffoldNavBarUiBinder
            extends UiBinder<MaterialSideNavPush, ScaffoldNavBar>
    {
    }
}
