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

package biz.turnonline.ecosystem.widget.billing.presenter;

import biz.turnonline.ecosystem.widget.billing.event.DueDateNumberOfDaysEvent;
import biz.turnonline.ecosystem.widget.billing.event.IssueOrderInvoiceEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderInvoicesEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderListEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderScheduleChangeEvent;
import biz.turnonline.ecosystem.widget.billing.event.OrderStatusChangeEvent;
import biz.turnonline.ecosystem.widget.billing.event.SaveOrderEvent;
import biz.turnonline.ecosystem.widget.billing.place.EditOrder;
import biz.turnonline.ecosystem.widget.billing.place.Invoices;
import biz.turnonline.ecosystem.widget.billing.place.Orders;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.event.ProductAutoCompleteEvent;
import biz.turnonline.ecosystem.widget.shared.event.RecalculatedPricingEvent;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity;
import biz.turnonline.ecosystem.widget.shared.rest.billing.OrderStatus;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.ui.TreeItemWithModel;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.datepicker.client.CalendarUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.ACTIVE;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Order.Status.SUSPENDED;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity.MANUALLY;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.OrderPeriodicity.valueOf;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditOrderPresenter
        extends Presenter<EditOrderPresenter.IView, AppEventBus>
{
    private static final int NUMBER_OF_DAYS_DEFAULT = 14;

    @Inject
    public EditOrderPresenter( AppEventBus eventBus,
                               IView view,
                               PlaceController placeController )
    {
        super( eventBus, view, placeController );
        setPlace( EditOrder.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( OrderListEvent.TYPE, event -> controller().goTo( new Orders() ) );
        bus().addHandler( SaveOrderEvent.TYPE, this::orderSaved );
        bus().addHandler( RecalculatedPricingEvent.TYPE, this::recalculated );
        bus().addHandler( OrderScheduleChangeEvent.TYPE, this::adjustNextBillingDate );
        bus().addHandler( DueDateNumberOfDaysEvent.TYPE, this::adjustDueDate );
        bus().addHandler( IssueOrderInvoiceEvent.TYPE, this::issueOrderInvoice );
        bus().addHandler( OrderInvoicesEvent.TYPE, this::showOrderInvoices );
        bus().addHandler( OrderStatusChangeEvent.TYPE, this::changeOrderStatus );
        bus().addHandler( ProductAutoCompleteEvent.TYPE, e -> view().addItem( e.getProduct(), e.getItem() ) );
    }

    @Override
    public void onBackingObject()
    {
        EditOrder where = ( EditOrder ) controller().getWhere();
        if ( where.getId() == null )
        {
            setModel( new Order() );
        }
        else
        {
            bus().billing().findOrderById( where.getId(), 1, ( SuccessCallback<Order> ) this::setModel );
        }

        onAfterBackingObject();
    }

    private void orderSaved( SaveOrderEvent event )
    {
        Order order = event.getOrder();

        if ( order.getId() == null )
        {
            bus().billing().createOrder( order, this::created );
        }
        else
        {
            bus().billing().updateOrder( order.getId(), order, this::updated );
        }
    }

    private void recalculated( RecalculatedPricingEvent event )
    {
        view().update( event.getPricing() );
    }

    private void created( Order response, FacadeCallback.Failure failure )
    {
        if ( failure.isSuccess() )
        {
            setModel( response );
        }

        success( messages.msgOrderCreated(), failure );
    }

    private void updated( Order response, FacadeCallback.Failure failure )
    {
        if ( failure.isSuccess() )
        {
            setModel( response );
        }

        success( messages.msgOrderUpdated(), failure );
    }

    private void setModel( Order model )
    {
        view().setModel( model );

        List<Invoice> invoices = model.getInvoices();
        if ( invoices != null && !invoices.isEmpty() )
        {
            view().lastInvoice( invoices.get( 0 ) );
        }

        // init as last
        OrderPeriodicity periodicity;
        try
        {
            periodicity = model.getPeriodicity() == null ? MANUALLY : valueOf( model.getPeriodicity() );
        }
        catch ( IllegalArgumentException e )
        {
            periodicity = MANUALLY;
        }

        adjustNextBillingDate(
                periodicity,
                model.getBeginOn() == null ? new Date() : model.getBeginOn(),
                model.getLastBillingDate(),
                model.getNumberOfDays() );
    }

    private void adjustNextBillingDate( OrderScheduleChangeEvent event )
    {
        adjustNextBillingDate(
                event.getPeriodicity(),
                event.getBeginOn(),
                event.getLastBillingDate(),
                event.getNumberOfDays() );
    }

    private void adjustNextBillingDate( @Nonnull OrderPeriodicity periodicity,
                                        @Nonnull Date beginOn,
                                        @Nullable Date last,
                                        @Nullable Integer days )
    {
        view().setBeginOnReadOnly( last != null );

        Date nextDate;
        if ( last == null )
        {
            last = beginOn;
        }

        switch ( periodicity )
        {
            case MANUALLY:
            {
                nextDate = beginOn;
                break;
            }
            case MONTHLY:
            {
                last = new Date( last.getTime() );
                CalendarUtil.addMonthsToDate( last, 1 );
                nextDate = last;
                break;
            }
            case QUARTERLY:
            {
                last = new Date( last.getTime() );
                CalendarUtil.addMonthsToDate( last, 3 );
                nextDate = last;
                break;
            }
            case ANNUALLY:
            {
                last = new Date( last.getTime() );
                CalendarUtil.addMonthsToDate( last, 12 );
                nextDate = last;
                break;
            }
            case SEMI_ANNUALLY:
            {
                last = new Date( last.getTime() );
                CalendarUtil.addMonthsToDate( last, 6 );
                nextDate = last;
                break;
            }
            case WEEKLY:
            {
                last = new Date( last.getTime() );
                CalendarUtil.addDaysToDate( last, 7 );
                nextDate = last;
                break;
            }
            default:
            {
                nextDate = null;
                break;
            }
        }

        view().setNextBillingDate( nextDate );

        if ( days == null )
        {
            days = NUMBER_OF_DAYS_DEFAULT;
            view().setNumberOfDays( days );
        }

        // + adjust due date
        adjustDueDate( nextDate, days );
    }

    private void adjustDueDate( DueDateNumberOfDaysEvent event )
    {
        adjustDueDate( event.getNextBillingDate(), event.getNumberOfDays() );
    }

    private void adjustDueDate( @Nonnull Date nextDate, @Nonnull Integer days )
    {
        Date due = new Date( nextDate.getTime() );
        CalendarUtil.addDaysToDate( due, days );

        view().setDueDate( due );
    }

    /**
     * Issue a new invoice from specified order and adds a newly issued invoice into local order's invoice list.
     */
    private void issueOrderInvoice( IssueOrderInvoiceEvent event )
    {
        bus().billing().createOrderInvoice( event.getOrderId(), new Invoice(), ( response, failure ) -> {
            if ( failure.isSuccess() )
            {
                view().lastInvoice( response );

                Order order = view().getModel();
                List<Invoice> invoices = order.getInvoices();
                if ( invoices == null )
                {
                    invoices = new ArrayList<>();
                    order.setInvoices( invoices );
                }

                if ( invoices.contains( response ) )
                {
                    invoices.add( response );
                }

                bus().billing().getOrderStatus( event.getOrderId(), status ->
                        view().setStatus( status.getStatusEnum() ) );
            }

            success( messages.msgInvoiceIssued(), failure );
        } );
    }

    private void showOrderInvoices( OrderInvoicesEvent event )
    {
        controller().goTo( new Invoices( event.getOrderId() ) );
    }

    private void changeOrderStatus( OrderStatusChangeEvent event )
    {
        Order.Status status = event.getOrderStatus();
        OrderStatus os = new OrderStatus();
        os.setStatus( status.name() );

        bus().billing().changeOrderStatus( event.getOrderId(), os,
                ( response, failure ) -> {
                    if ( ACTIVE == status )
                    {
                        success( messages.msgOrderStatusActive(), failure );
                    }
                    else if ( SUSPENDED == status )
                    {
                        warn( messages.msgOrderStatusSuspended(), failure );
                    }
                    view().setStatus( status );
                } );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<Order>
    {
        /**
         * Sets the invoice to be shown as a last issued invoice for this order.
         * Shown read only.
         *
         * @param invoice the last invoice if any
         */
        void lastInvoice( @Nullable Invoice invoice );

        /**
         * Updates the order's pricing (details and items) UI by recalculated price.
         *
         * @param pricing the recalculated price
         */
        void update( @Nonnull Pricing pricing );

        /**
         * Sets the order's Begin on date whether it's allowed to be edited by user or not.
         *
         * @param readOnly true to be read only
         */
        void setBeginOnReadOnly( boolean readOnly );

        /**
         * Sets the order's Next billing date, evaluated based on the current periodicity.
         *
         * @param next the next billing date to be set
         */
        void setNextBillingDate( @Nonnull Date next );

        /**
         * Sets order's due date (an issue date once placed at invoice).
         * Evaluated based on the due date number of days.
         *
         * @param dueDate the due date to be set
         */
        void setDueDate( @Nullable Date dueDate );

        /**
         * Sets the number of days to calculate due date.
         *
         * @param days the number of days to be set
         */
        void setNumberOfDays( @Nullable Integer days );

        /**
         * Sets the current order status. It have an impact on whether some action buttons will be enabled or not.
         *
         * @param status the current status to be set
         */
        void setStatus( @Nonnull Order.Status status );

        /**
         * Adds and populates a row and tree item. The final tree will be based on the product's template if defined.
         *
         * @param product    the product as a source of pricing item
         * @param parentItem the empty tree item as a parent item to be populated from given product
         */
        void addItem( @Nonnull Product product, @Nonnull TreeItemWithModel parentItem );
    }
}