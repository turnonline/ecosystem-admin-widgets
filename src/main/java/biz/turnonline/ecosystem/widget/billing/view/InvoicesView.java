/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.billing.view;

import biz.turnonline.ecosystem.widget.billing.event.EditInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.presenter.InvoicesPresenter;
import biz.turnonline.ecosystem.widget.billing.ui.ColumnInvoiceActions;
import biz.turnonline.ecosystem.widget.billing.ui.ColumnInvoiceId;
import biz.turnonline.ecosystem.widget.billing.ui.ColumnInvoicePrice;
import biz.turnonline.ecosystem.widget.billing.ui.ColumnInvoiceStatus;
import biz.turnonline.ecosystem.widget.billing.ui.InvoicesDataSource;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.ui.ColumnCustomer;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.ui.SmartTable;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialButton;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class InvoicesView
        extends View
        implements InvoicesPresenter.IView
{
    private static InvoicesViewUiBinder binder = GWT.create( InvoicesViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    MaterialButton btnNew;

    @UiField
    SmartTable<Invoice> table;

    @Override
    public void refresh()
    {
        table.refresh();
    }

    interface InvoicesViewUiBinder
            extends UiBinder<HTMLPanel, InvoicesView>
    {
    }

    @Inject
    public InvoicesView( EventBus eventBus, @Named( "InvoicesBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super( eventBus );

        this.breadcrumb = breadcrumb;
        setActive( Route.INVOICES );

        add( binder.createAndBindUi( this ) );
        initTable();
    }

    private void initTable()
    {
        ColumnInvoiceId id = new ColumnInvoiceId();
        id.setWidth( "20%" );

        ColumnInvoiceStatus status = new ColumnInvoiceStatus();
        status.setWidth( "20%" );

        ColumnCustomer<Invoice> customer = new ColumnCustomer<>();
        customer.setWidth( "25%" );

        ColumnInvoicePrice price = new ColumnInvoicePrice();
        price.setWidth( "20%" );

        ColumnInvoiceActions actions = new ColumnInvoiceActions( bus() );
        actions.setWidth( "5%" );

        table.addColumn( id, messages.labelId() );
        table.addColumn( status, messages.labelStatus() );
        table.addColumn( customer, messages.labelCustomer() );
        table.addColumn( price, messages.labelPrice() );
        table.addColumn( actions );

        table.configure( new InvoicesDataSource( ( AppEventBus ) bus() ) );
    }

    @SuppressWarnings( "unused" )
    @UiHandler( "btnNew" )
    public void handleNew( ClickEvent event )
    {
        bus().fireEvent( new EditInvoiceEvent() );
    }
}