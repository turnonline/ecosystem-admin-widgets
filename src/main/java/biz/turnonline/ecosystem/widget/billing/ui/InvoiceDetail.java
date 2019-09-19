package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.billing.event.InvoiceStatusChangeEvent;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePayment;
import biz.turnonline.ecosystem.widget.shared.rest.billing.InvoicePricing;
import biz.turnonline.ecosystem.widget.shared.rest.billing.PricingItem;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import biz.turnonline.ecosystem.widget.shared.ui.InvoiceTypeComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.PaymentMethodComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.PricingItemsPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.stepper.MaterialStep;
import gwt.material.design.addins.client.stepper.MaterialStepper;
import gwt.material.design.client.js.Window;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialLongBox;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.NEW;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.SENT;
import static biz.turnonline.ecosystem.widget.shared.rest.billing.Invoice.Status.valueOf;

/**
 * Invoice detail form.
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class InvoiceDetail
        extends Composite
        implements HasModel<Invoice>
{
    private static DetailUiBinder binder = GWT.create( DetailUiBinder.class );

    private static AppMessages messages = AppMessages.INSTANCE;

    private final EventBus bus;

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

    @UiField
    MaterialPanel stepperPanel;

    @UiField
    MaterialStepper stepper;

    @UiField
    MaterialStep stepNew;

    @UiField
    MaterialStep stepSent;

    @UiField
    MaterialStep lastStep;

    private Invoice invoice;

    private Invoice.Status currentStatus;

    private HandlerRegistration sentHandler;

    @Inject
    public InvoiceDetail( EventBus eventBus )
    {
        this.bus = eventBus;
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

        invoiceNumber.setReturnBlankAsNull( true );
        variableSymbol.setReturnBlankAsNull( true );
        finalText.setReturnBlankAsNull( true );
        introductoryText.setReturnBlankAsNull( true );
        priceExclVat.setReturnBlankAsNull( true );
        vatBase.setReturnBlankAsNull( true );
        priceInclVat.setReturnBlankAsNull( true );
        toPay.setReturnBlankAsNull( true );

        Window.addResizeHandler( resizeEvent -> detectAndApplyOrientation() );
        detectAndApplyOrientation();
    }

    private void detectAndApplyOrientation()
    {
        if ( Window.matchMedia( "(orientation: portrait)" ) )
        {
            stepperPanel.setHeight( "250px" );
        }
        else
        {
            stepperPanel.setHeight( "70px" );
        }
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
        invoice.setType( invoiceType.getSingleValueByCode() )
                .setDateOfIssue( dateOfIssue.getValue() )
                .setDateOfTaxable( dateOfTaxable.getValue() )
                .setFinalText( finalText.getValue() )
                .setIntroductoryText( introductoryText.getValue() );

        InvoicePayment payment = new InvoicePayment();
        payment.setDueDate( dueDate.getValue() )
                .setMethod( paymentMethod.getSingleValueByCode() );

        invoice.setPaymentIf( payment );
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

        try
        {
            currentStatus = invoice.getStatus() == null ? NEW : valueOf( invoice.getStatus() );
        }
        catch ( IllegalArgumentException e )
        {
            currentStatus = NEW;
        }

        setStatus( currentStatus );

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
     * Sets the current invoice status, visualized by 3 steps.
     * {@link Invoice.Status#SENT} step has click handlers added in order to give possibility to change.
     * <p>
     * {@link Invoice.Status#CANCELED} has a special handling, this status visually replaces
     * {@link Invoice.Status#PAID} step (last third step).
     *
     * @param status the current status to be set
     */
    public void setStatus( @Nonnull Invoice.Status status )
    {
        currentStatus = checkNotNull( status, "Invoice status can't be null" );
        stepSent.setEnabled( true );

        if ( invoice != null && invoice.getId() != null )
        {
            // if already stored in datastore yet, give hint to send invoice
            stepSent.setSuccessText( messages.descriptionInvoiceStatusSendTo() );
        }
        else
        {
            // if not stored in datastore yet, don't give hint to send invoice
            stepSent.setSuccessText( messages.descriptionInvoiceStatusSent() );
        }

        stepper.reset();
        setReadOnly( NEW != currentStatus );

        if ( sentHandler != null )
        {
            stepSent.removeHandler( sentHandler );
        }

        switch ( currentStatus )
        {
            case NEW:
            {
                if ( invoice != null && invoice.getId() != null )
                {
                    stepper.nextStep();
                    // this action is available only for persisted invoice
                    sentHandler = stepSent.addClickHandler( e -> fireInvoiceStatusSentChangeEvent() );
                }

                stepNew.setSuccessText( messages.descriptionInvoiceStatusNew() );

                break;
            }
            case SENT:
            {
                stepper.nextStep();
                stepSent.setSuccessText( messages.descriptionInvoiceStatusSent() );

                break;
            }
            case PAID:
            {
                stepper.nextStep();
                stepper.nextStep();
                stepper.nextStep();

                lastStep.setSuccessText( messages.descriptionInvoiceStatusPaid() );

                break;
            }
            case CANCELED:
            {
                stepper.nextStep();
                stepper.nextStep();
                stepper.nextStep();

                lastStep.setTitle( messages.labelInvoiceStatusCanceled() );
                lastStep.setSuccessText( messages.descriptionInvoiceStatusCanceled() );

                break;
            }
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
                null,
                items,
                priceExclVat,
                vatBase,
                priceInclVat,
                toPay );
    }

    private void fireInvoiceStatusSentChangeEvent()
    {
        if ( invoice != null && invoice.getOrderId() != null && invoice.getId() != null )
        {
            // this action is available only for persisted invoice
            stepSent.setEnabled( false );
            bus.fireEvent( new InvoiceStatusChangeEvent( currentStatus, SENT, invoice.getOrderId(), invoice.getId() ) );
        }
        else
        {
            // for this case component local status handling is sufficient
            setStatus( SENT );
        }
    }

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, InvoiceDetail>
    {
    }

}
