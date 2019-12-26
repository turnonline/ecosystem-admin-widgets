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

package biz.turnonline.ecosystem.widget.purchase.view;

import biz.turnonline.ecosystem.widget.purchase.event.BackEvent;
import biz.turnonline.ecosystem.widget.purchase.event.DeclinePurchaseOrderEvent;
import biz.turnonline.ecosystem.widget.purchase.presenter.ViewPurchaseOrderPresenter;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PurchaseOrder;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Purchase order detail view form.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ViewPurchaseOrderView
        extends View<PurchaseOrder>
        implements ViewPurchaseOrderPresenter.IView
{
    private static EditContactsViewUiBinder binder = GWT.create( EditContactsViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    ConfirmationWindow confirmation;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialAnchorButton decline;

    @Inject
    public ViewPurchaseOrderView( @Named( "ViewPurchaseOrderBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;
        setActive( Route.CONTACTS );

        add( binder.createAndBindUi( this ) );

        confirmation.getBtnOk().addClickHandler( event -> bus().fireEvent( new DeclinePurchaseOrderEvent( getRawModel() ) ) );
    }

    @Override
    protected void beforeGetModel()
    {
    }

    @Override
    protected void afterSetModel()
    {
    }

    @UiHandler( "btnBack" )
    public void handleBack( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new BackEvent() );
    }

    @UiHandler( "decline" )
    public void deleteContact( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        confirmation.open( AppMessages.INSTANCE.questionDeleteRecord() );
    }

    interface EditContactsViewUiBinder
            extends UiBinder<HTMLPanel, ViewPurchaseOrderView>
    {
    }
}