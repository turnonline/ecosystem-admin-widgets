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

package biz.turnonline.ecosystem.widget.invoice.view;

import biz.turnonline.ecosystem.widget.invoice.event.BackEvent;
import biz.turnonline.ecosystem.widget.invoice.event.SaveInvoiceEvent;
import biz.turnonline.ecosystem.widget.invoice.presenter.EditInvoicePresenter;
import biz.turnonline.ecosystem.widget.invoice.ui.EditInvoiceTabs;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Invoice;
import biz.turnonline.ecosystem.widget.shared.ui.CustomerPanel;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialButton;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class EditInvoiceView
        extends View<Invoice>
        implements EditInvoicePresenter.IView
{
    private static EditInvoiceViewUiBinder binder = GWT.create( EditInvoiceViewUiBinder.class );

    private PlaceController controller;

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    EditInvoiceTabs tabs;

    // -- tab contents

//    @UiField
//    Detail detail;

    @UiField( provided = true )
    CustomerPanel customer;

//    @UiField( provided = true )
//    Items items;

    // -- buttons

    @UiField
    MaterialButton btnSave;

    @UiField
    MaterialButton btnBack;

    interface EditInvoiceViewUiBinder
            extends UiBinder<HTMLPanel, EditInvoiceView>
    {
    }

    @Inject
    public EditInvoiceView( EventBus eventBus,
                            PlaceController controller,
                            @Named( "EditInvoiceBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super( eventBus );

        this.controller = controller;

        this.breadcrumb = breadcrumb;
        scaffoldNavBar.setActive( Route.INVOICES );

        customer = new CustomerPanel( eventBus );

        add( binder.createAndBindUi( this ) );
    }

    @Override
    protected void bind()
    {
//        Product product = getRawModel();
//
//        detail.bind( product );
//        content.bind( product );
//        publishing.bind( product );
//        pricing.bind( product );
//        invoicing.bind( product );
//        event.bind( product );
    }

    @Override
    protected void fill()
    {
//        Product product = getRawModel();
//
//        detail.fill( product );
//        content.fill( product );
//        publishing.fill( product );
//        pricing.fill( product );
//        invoicing.fill( product );
//        event.fill( product );
//
//        Scheduler.get().scheduleDeferred( () -> {
//            EditProduct where = ( EditProduct ) controller.getWhere();
//            tabs.selectTab( where.getTab() );
//        } );
    }

    @UiHandler( "btnBack" )
    public void handleBack( ClickEvent event )
    {
        bus().fireEvent( new BackEvent() );
    }

    @UiHandler( "btnSave" )
    public void handleSave( ClickEvent event )
    {
        bus().fireEvent( new SaveInvoiceEvent( getModel() ) );
    }
}