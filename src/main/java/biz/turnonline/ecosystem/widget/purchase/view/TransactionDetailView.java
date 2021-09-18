/*
 * Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.turnonline.ecosystem.widget.purchase.view;

import biz.turnonline.ecosystem.widget.purchase.event.BackTransactionEvent;
import biz.turnonline.ecosystem.widget.purchase.event.EditBillEvent;
import biz.turnonline.ecosystem.widget.purchase.presenter.TransactionDetailPresenter;
import biz.turnonline.ecosystem.widget.purchase.ui.CategoryBadge;
import biz.turnonline.ecosystem.widget.purchase.ui.TransactionAmountBox;
import biz.turnonline.ecosystem.widget.purchase.ui.TransactionStatusChip;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import biz.turnonline.ecosystem.widget.shared.ui.PriceTextBox;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.ui.SectionTitle;
import biz.turnonline.ecosystem.widget.shared.util.Router;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.addins.client.bubble.MaterialBubble;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialLongBox;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.inject.Inject;
import javax.inject.Named;

import static biz.turnonline.ecosystem.widget.shared.util.Router.Target.NEW_WINDOW;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class TransactionDetailView
        extends View<Transaction>
        implements TransactionDetailPresenter.IView
{
    private static final TransactionDetailViewUiBinder binder = GWT.create( TransactionDetailViewUiBinder.class );

    @UiField
    MaterialLongBox transactionId;

    @UiField
    MaterialTextBox key;

    @UiField
    TransactionStatusChip status;

    @UiField
    TransactionAmountBox amount;

    @UiField
    PriceTextBox balance;

    @UiField
    MaterialTextBox bankAccountCode;

    @UiField
    MaterialTextBox bankAccountIban;

    @UiField
    MaterialTextBox reference;

    @UiField
    MaterialDatePicker completedAt;

    @UiField
    MaterialTextBox merchantCategory;

    @UiField
    MaterialTextBox merchantCity;

    @UiField
    MaterialTextBox merchantName;

    @UiField
    MaterialContainer categories;

    @UiField
    SectionTitle categoryTitle;

    @UiField
    MaterialBubble bubble;

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialButton btnRedirectToBill;

    @UiField
    MaterialButton btnRedirectToInvoice;

    @UiField
    MaterialButton btnRedirectToOrder;

    @Inject
    public TransactionDetailView( @Named( "TransactionDetailBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;

        setActive( Route.TRANSACTIONS );

        add( binder.createAndBindUi( this ) );

        btnRedirectToBill.setVisible( false );
        btnRedirectToInvoice.setVisible( false );
        btnRedirectToOrder.setVisible( false );

        categories.getElement().getStyle().setDisplay( Style.Display.INLINE_BLOCK );

        categoryTitle.getTitleComponent().addMouseOverHandler( this::showCategories );
        categoryTitle.getTitleComponent().addMouseOutHandler( this::hideCategories );
    }

    @Override
    protected void afterSetModel( Transaction transaction )
    {
        transactionId.setValue( transaction.getTransactionId() );
        key.setValue( transaction.getKey() );
        status.setValue( transaction );
        amount.setValue( transaction );
        balance.setValue( transaction.getBalance(), transaction.getCurrency() );
        reference.setValue( transaction.getReference() );
        completedAt.setValue( transaction.getCompletedAt() );

        if ( transaction.getBankAccount() != null )
        {
            bankAccountCode.setValue( transaction.getBankAccount().getCode() );
            bankAccountIban.setValue( transaction.getBankAccount().getIban() );
        }
        else
        {
            bankAccountCode.setValue( null );
            bankAccountIban.setValue( null );
        }

        if ( transaction.getMerchant() != null )
        {
            merchantCategory.setValue( transaction.getMerchant().getCategory() );
            merchantCity.setValue( transaction.getMerchant().getCity() );
            merchantName.setValue( transaction.getMerchant().getName() );
        }
        else
        {
            merchantCategory.setValue( null );
            merchantCity.setValue( null );
            merchantName.setValue( null );
        }

        categories.clear();
        if ( transaction.getCategories() != null )
        {
            transaction.getCategories().forEach( category -> {
                CategoryBadge badge = new CategoryBadge( category );
                badge.setMarginRight( 10 );
                badge.setMarginBottom( 10 );
                categories.add( badge );
            } );
        }

        btnRedirectToBill.setVisible( transaction.getBill() != null && transaction.getBill().getReceipt() != null );
        btnRedirectToInvoice.setVisible( transaction.getBill() != null && transaction.getBill().getOrder() != null && transaction.getBill().getInvoice() != null );
        btnRedirectToOrder.setVisible( transaction.getBill() != null && transaction.getBill().getInvoice() != null );
    }

    @UiHandler( "btnBack" )
    public void handleBack( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new BackTransactionEvent() );
    }

    @UiHandler( "btnRedirectToBill" )
    public void handleRedirectToBill( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new EditBillEvent( getRawModel().getBill().getReceipt() ) );
    }

    @UiHandler( "btnRedirectToInvoice" )
    public void handleRedirectToInvoice( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        Router.routeToDetail( Route.INVOICES, new String[]{
                        getRawModel().getBill().getOrder().toString(),
                        getRawModel().getBill().getInvoice().toString(),
                        "tabDetail"},
                NEW_WINDOW );
    }

    @UiHandler( "btnRedirectToOrder" )
    public void handleRedirectToOrder( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        Router.routeToDetail( Route.ORDERS,
                new String[]{getRawModel().getBill().getOrder().toString(), "tabDetail"},
                NEW_WINDOW );
    }

    public void hideCategories( MouseOutEvent event )
    {
        bubble.setVisible( false );
        categories.setVisible( true );
    }

    public void showCategories( MouseOverEvent event )
    {
        if ( !event.getNativeEvent().getCtrlKey() )
        {
            return;
        }

        ( ( AppEventBus ) bus() ).paymentProcessor().getCategoriesForTransaction( getRawModel().getTransactionId(), response -> {
            bubble.clear();

            response.getItems().forEach( category -> {
                MaterialContainer container = new MaterialContainer();
                bubble.add( container );

                CategoryBadge badge = new CategoryBadge( category );
                container.add( badge );
            } );

            if ( !response.getItems().isEmpty() )
            {
                bubble.setVisible( true );
                categories.setVisible( false );
            }
        } );
    }

    interface TransactionDetailViewUiBinder
            extends UiBinder<HTMLPanel, TransactionDetailView>
    {
    }
}