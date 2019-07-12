package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePayment;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import biz.turnonline.ecosystem.widget.shared.ui.InvoiceTypeComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.PaymentMethodComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialLongBox;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.inject.Inject;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class InvoiceDetail
        extends Composite
        implements HasModel<Invoice>
{
    private static DetailUiBinder binder = GWT.create( DetailUiBinder.class );

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, InvoiceDetail>
    {
    }

    // description

    @UiField
    InvoiceTypeComboBox invoiceType;

    @UiField
    MaterialTextBox invoiceNumber;

    @UiField
    MaterialLongBox variableSymbol;

    // Dates

    @UiField
    MaterialDatePicker dateOfIssue;

    @UiField
    MaterialDatePicker dateOfTaxable;

    // Texts

    @UiField
    MaterialTextArea finalText;

    @UiField
    MaterialTextArea introductoryText;

    // payment

    @UiField
    MaterialDatePicker dueDate;

    @UiField
    PaymentMethodComboBox paymentMethod;

    @Inject
    public InvoiceDetail()
    {
        initWidget( binder.createAndBindUi( this ) );

        invoiceNumber.setEnabled( false );
        variableSymbol.setEnabled( false );
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
        InvoicePayment payment = invoice.getPayment();

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

}
