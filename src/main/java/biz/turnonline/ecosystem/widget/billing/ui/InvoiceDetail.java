package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePayment;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
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
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class InvoiceDetail
        extends Composite
        implements HasModel<Invoice>
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
    MaterialTextArea finalText;

    @UiField
    MaterialTextArea introductoryText;

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

    private Invoice invoice;

    @Inject
    public InvoiceDetail()
    {
        initWidget( binder.createAndBindUi( this ) );

        paymentMethod.setPaddingBottom( 7 );
        invoiceType.setPaddingBottom( 7 );

        created.setReadOnly( true );
        modified.setReadOnly( true );
        invoiceNumber.setReadOnly( true );
        variableSymbol.setReadOnly( true );
        priceExclVat.setReadOnly( true );
        vatBase.setReadOnly( true );
        priceInclVat.setReadOnly( true );
        toPay.setReadOnly( true );
    }

    /**
     * If {@code true} sets all editable fields read only.
     */
    public void setReadOnly( boolean readOnly )
    {
        invoiceType.setReadOnly( readOnly );
        dateOfIssue.setReadOnly( readOnly );
        dateOfTaxable.setReadOnly( readOnly );
        finalText.setReadOnly( readOnly );
        introductoryText.setReadOnly( readOnly );
        dueDate.setReadOnly( readOnly );
        paymentMethod.setReadOnly( readOnly );
    }

    /**
     * Returns the current invoice model used to render this panel.
     *
     * @return the current invoice model or {@code null} if none yet
     */
    public Invoice getInvoice()
    {
        return invoice;
    }

    @Override
    public void bind( Invoice invoice )
    {
        // description
        invoice.setType( invoiceType.getSingleValueByCode() );

        // dates
        invoice.setDateOfIssue( dateOfIssue.getValue() );
        invoice.setDateOfTaxable( dateOfTaxable.getValue() );

        // texts
        invoice.setFinalText( finalText.getValue() );
        invoice.setIntroductoryText( introductoryText.getValue() );

        // payment
        if ( dueDate != null )
        {
            ensurePayment( invoice ).setDueDate( dueDate.getValue() );
        }
        if ( paymentMethod != null )
        {
            ensurePayment( invoice ).setMethod( paymentMethod.getSingleValueByCode() );
        }
    }

    @Override
    public void fill( Invoice invoice )
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

        // texts
        finalText.setValue( invoice.getFinalText() );
        introductoryText.setValue( invoice.getIntroductoryText() );

        // payment
        dueDate.setValue( payment != null ? payment.getDueDate() : null );
        paymentMethod.setSingleValueByCode( payment != null ? payment.getMethod() : null );

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

    private InvoicePayment ensurePayment( Invoice invoice )
    {
        InvoicePayment payment = invoice.getPayment();
        if ( payment == null )
        {
            invoice.setPayment( new InvoicePayment() );
            payment = invoice.getPayment();
        }

        return payment;
    }

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, InvoiceDetail>
    {
    }

}
