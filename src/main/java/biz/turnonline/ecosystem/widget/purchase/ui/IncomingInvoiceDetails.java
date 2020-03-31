package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.IncomingInvoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePayment;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.ui.InvoiceTypeComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.PaymentMethodComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialLongBox;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

/**
 * Incoming invoice detail read only form panel.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class IncomingInvoiceDetails
        extends Composite
{
    private static DetailUiBinder binder = GWT.create( DetailUiBinder.class );

    @UiField
    InvoiceTypeComboBox invoiceType;

    @UiField
    MaterialTextBox invoiceNumber;

    @UiField
    MaterialLongBox variableSymbol;

    @UiField
    MaterialDatePicker dateOfIssue;

    @UiField
    MaterialDatePicker dateOfTaxable;

    @UiField
    MaterialDatePicker dueDate;

    @UiField
    PaymentMethodComboBox paymentMethod;

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
    MaterialTextBox toPay;

    private IncomingInvoice invoice;

    @Inject
    public IncomingInvoiceDetails()
    {
        initWidget( binder.createAndBindUi( this ) );

        paymentMethod.setPaddingBottom( 7 );
        invoiceType.setPaddingBottom( 7 );

        invoiceType.setReadOnly( true );
        dateOfIssue.setReadOnly( true );
        dateOfTaxable.setReadOnly( true );
        dueDate.setReadOnly( true );
        paymentMethod.setReadOnly( true );

        created.setReadOnly( true );
        modified.setReadOnly( true );
        invoiceNumber.setReadOnly( true );
        variableSymbol.setReadOnly( true );
        priceExclVat.setReadOnly( true );
        vatBase.setReadOnly( true );
        priceInclVat.setReadOnly( true );
        toPay.setReadOnly( true );

        invoiceNumber.setReturnBlankAsNull( true );
        variableSymbol.setReturnBlankAsNull( true );
        priceExclVat.setReturnBlankAsNull( true );
        vatBase.setReturnBlankAsNull( true );
        priceInclVat.setReturnBlankAsNull( true );
        toPay.setReturnBlankAsNull( true );
    }

    /**
     * Returns the current invoice model used to render this panel.
     *
     * @return the current invoice model or {@code null} if none yet
     */
    public IncomingInvoice getInvoice()
    {
        return invoice;
    }

    public void bind( @Nonnull IncomingInvoice invoice )
    {
        invoice.setType( invoiceType.getSingleValueByCode() )
                .setDateOfIssue( dateOfIssue.getValue() )
                .setDateOfTaxable( dateOfTaxable.getValue() );

        InvoicePayment payment = new InvoicePayment();
        payment.setDueDate( dueDate.getValue() )
                .setType( paymentMethod.getSingleValueByCode() );
    }

    public void fill( @Nonnull IncomingInvoice invoice )
    {
        this.invoice = invoice;

        InvoicePayment payment = invoice.getPayment();

        created.setValue( invoice.getCreatedDate() );
        modified.setValue( invoice.getModificationDate() );

        // description
        invoiceType.setSingleValueByCode( invoice.getType() );
        invoiceNumber.setValue( invoice.getInvoiceNumber() );
        variableSymbol.setValue( payment != null ? payment.getVariableSymbol() : null );

        // dates
        dateOfIssue.setValue( invoice.getDateOfIssue() );
        dateOfTaxable.setValue( invoice.getDateOfTaxable() );

        // payment
        dueDate.setValue( payment != null ? payment.getDueDate() : null );
        paymentMethod.setSingleValueByCode( payment != null ? payment.getType() : null );

        InvoicePricing pricing = invoice.getPricing();
        if ( pricing == null )
        {
            updatePricing( null, null, null, null );
        }
        else
        {
            Double amountToPay = payment == null ? null : payment.getTotalAmount();
            PricingItemsPanel.updatePricing( pricing.getTotalPriceExclVat(),
                    pricing.getTotalVatBase(),
                    pricing.getTotalPrice(),
                    amountToPay,
                    pricing.getItems(),
                    priceExclVat,
                    vatBase,
                    priceInclVat,
                    toPay );
        }
    }

    public void clear()
    {
        invoice = null;
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
                null,
                items,
                priceExclVat,
                vatBase,
                priceInclVat,
                toPay );
    }

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, IncomingInvoiceDetails>
    {
    }
}
