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

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoiceType;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PurchaseOrder;
import biz.turnonline.ecosystem.widget.shared.ui.InvoiceTypeComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.OrderPeriodicityComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.addins.client.stepper.MaterialStep;
import gwt.material.design.addins.client.stepper.MaterialStepper;
import gwt.material.design.client.js.Window;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.ACTIVE;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.FINISHED;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.ISSUE;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.SUSPENDED;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.TRIALING;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity.MANUALLY;

/**
 * Management all of the single order properties.
 * Fires following events:
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseOrderDetails
        extends Composite
{
    private static DetailUiBinder binder = GWT.create( DetailUiBinder.class );

    private static AppMessages messages = AppMessages.INSTANCE;

    @UiField
    MaterialDatePicker beginOn;

    @UiField
    MaterialDatePicker lastBillingDate;

    @UiField
    MaterialDatePicker nextBillingDate;

    @UiField
    MaterialIntegerBox numberOfDays;

    @UiField
    OrderPeriodicityComboBox periodicity;

    @UiField
    InvoiceTypeComboBox invoiceType;

    @UiField
    MaterialDatePicker created;

    @UiField
    MaterialDatePicker modified;

    @UiField
    MaterialTextBox priceExclVat;

    @UiField
    MaterialTextBox vatBase;

    @UiField
    MaterialTextBox priceInclVat;

    @UiField
    MaterialStepper stepper;

    @UiField
    MaterialPanel stepperPanel;

    @UiField
    MaterialStep trialing;

    @UiField
    MaterialStep active;

    @UiField
    MaterialStep suspended;

    @UiField
    MaterialStep finished;

    private Order.Status currentStatus;

    @Inject
    public PurchaseOrderDetails()
    {
        initWidget( binder.createAndBindUi( this ) );

        invoiceType.setPaddingBottom( 7 );

        beginOn.setReadOnly( true );
        numberOfDays.setReadOnly( true );
        invoiceType.setReadOnly( true );
        periodicity.setReadOnly( true );
        lastBillingDate.setReadOnly( true );
        nextBillingDate.setReadOnly( true );

        created.setReadOnly( true );
        modified.setReadOnly( true );

        priceExclVat.setReadOnly( true );
        vatBase.setReadOnly( true );
        priceInclVat.setReadOnly( true );

        // trialing for now is not visible, go to next step
        trialing.setVisible( false );
        stepper.nextStep();

        Window.addResizeHandler( resizeEvent -> detectAndApplyOrientation() );
        detectAndApplyOrientation();
    }

    private void detectAndApplyOrientation()
    {
        if ( Window.matchMedia( "(orientation: portrait)" ) )
        {
            if ( trialing.isVisible() )
            {
                stepperPanel.setHeight( "350px" );
            }
            else
            {
                stepperPanel.setHeight( "250px" );
            }
        }
        else
        {
            stepperPanel.setHeight( "70px" );
        }
    }

    public void bind( @Nonnull PurchaseOrder order )
    {
        order.setBeginOn( beginOn.getValue() );
        order.setNumberOfDays( numberOfDays.getValue() );
        order.setPeriodicity( periodicity.getSingleValueByCode() );
        order.setInvoiceType( invoiceType.getSingleValueByCode() );
        order.setStatus( currentStatus == null ? SUSPENDED.name() : currentStatus.name() );
    }

    public void fill( @Nonnull PurchaseOrder order )
    {
        beginOn.setValue( order.getBeginOn() );
        lastBillingDate.setValue( order.getLastBillingDate() );
        nextBillingDate.setValue( order.getNextBillingDate() );

        String oPeriodicity = order.getPeriodicity() == null ? MANUALLY.name() : order.getPeriodicity();
        String type = order.getInvoiceType() == null ? InvoiceType.PROFORMA.name() : order.getInvoiceType();

        numberOfDays.setValue( order.getNumberOfDays() );
        periodicity.setSingleValueByCode( oPeriodicity );
        invoiceType.setSingleValueByCode( type );

        created.setValue( order.getCreatedDate() );
        modified.setValue( order.getModificationDate() );

        try
        {
            currentStatus = order.getStatus() == null ? SUSPENDED : Order.Status.valueOf( order.getStatus() );
        }
        catch ( IllegalArgumentException e )
        {
            currentStatus = SUSPENDED;
        }

        nextBillingDate.setVisible( currentStatus == TRIALING
                || currentStatus == ACTIVE
                || currentStatus == SUSPENDED
                || currentStatus == ISSUE );

        setStatus( currentStatus );
        updatePricing( order.getTotalPriceExclVat(), order.getTotalVatBase(), order.getTotalPrice(), order.getItems() );
    }

    /**
     * Sets the current order status, visualized by 4 steps ({@link Order.Status#TRIALING} is for time being hidden).
     *
     * @param status the current status to be set
     */
    public void setStatus( @Nonnull Order.Status status )
    {
        currentStatus = checkNotNull( status, "Order status can't be null" );
        suspended.setSuccessText( messages.descriptionOrderStatusSuspend() );
        stepper.reset();

        switch ( currentStatus )
        {
            case TRIALING:
            {
                break;
            }
            case ISSUE:
            {
                stepper.nextStep();

                active.setSuccessText( messages.descriptionOrderStatusActive() );
                active.clearStatusText();
                suspended.setErrorText( messages.descriptionOrderStatusIssue() );

                break;
            }
            case SUSPENDED:
            {
                // difference comparing to ISSUE status is setErrorText has different description
                stepper.nextStep();

                active.setSuccessText( messages.descriptionOrderStatusActive() );
                active.clearStatusText();
                suspended.setErrorText( messages.descriptionOrderStatusSuspended() );

                break;
            }
            case ACTIVE:
            {
                stepper.nextStep();
                stepper.nextStep();
                active.setSuccessText( messages.descriptionOrderStatusActive() );

                break;
            }
            case FINISHED:
            case COMPLETED:
                stepper.nextStep();
                stepper.nextStep();
                stepper.nextStep();

                active.setSuccessText( messages.descriptionOrderStatusActive() );
                suspended.setSuccessText( messages.descriptionOrderStatusSuspended() );
                if ( currentStatus == FINISHED )
                {
                    finished.setSuccessText( messages.descriptionOrderStatusFinished() );
                }
                else
                {
                    finished.setSuccessText( messages.descriptionOrderStatusCompleted() );
                }

                break;
        }
    }

    /**
     * Updates total price details.
     */
    public void updatePricing( @Nullable Double totalPriceExclVat,
                               @Nullable Double totalVatBase,
                               @Nullable Double totalPrice,
                               @Nullable List<PricingItem> items )
    {
        PricingItemsPanel.updatePricing(
                totalPriceExclVat,
                totalVatBase,
                totalPrice,
                items,
                priceExclVat, vatBase, priceInclVat );
    }

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, PurchaseOrderDetails>
    {
    }
}
