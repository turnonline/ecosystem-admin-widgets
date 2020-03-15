package biz.turnonline.ecosystem.widget.bill.ui;

import biz.turnonline.ecosystem.widget.shared.rest.account.Image;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.ui.BillTypeComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.CurrencyComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nonnull;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class BillDetail
        extends Composite
{
    private static DetailUiBinder binder = GWT.create( DetailUiBinder.class );

    @UiField
    MaterialTextBox billNumber;

    @UiField
    MaterialTextBox itemName;

    @UiField
    MaterialDoubleBox totalPrice;

    @UiField
    CurrencyComboBox currency;

    @UiField
    BillTypeComboBox billType;

    @UiField
    MaterialDatePicker dateOfIssue;

    @UiField
    MaterialDatePicker created;

    @UiField
    MaterialDatePicker modified;

    @UiField
    BillUploader billUploader;

    public BillDetail()
    {
        initWidget( binder.createAndBindUi( this ) );

        created.setReadOnly( true );
        modified.setReadOnly( true );
    }

    public void bind( @Nonnull Bill bill )
    {
        bill.setItemName( itemName.getValue() );
        bill.setBillNumber( billNumber.getValue() );
        bill.setDateOfIssue( dateOfIssue.getValue() );
        bill.setTotalPrice( totalPrice.getValue() );
        bill.setType( billType.getSingleValueByCode() != null ? Bill.TypeEnum.valueOf( billType.getSingleValueByCode() ) : null );
        bill.setCurrency( currency.getSingleValue() );

        bill.setServingUrl( billUploader.getValue().getServingUrl() );
        bill.setStorageName( billUploader.getValue().getStorageName() );
    }

    public void fill( @Nonnull Bill bill )
    {
        itemName.setValue( bill.getItemName() );
        billNumber.setValue( bill.getBillNumber() );
        dateOfIssue.setValue( bill.getDateOfIssue() );
        totalPrice.setValue( bill.getTotalPrice() );
        billType.setSingleValueByCode( bill.getType() != null ? bill.getType().name() : null );
        currency.setSingleValue( bill.getCurrency() );

        Image image = new Image();
        image.setServingUrl( bill.getServingUrl() );
        image.setStorageName( bill.getStorageName() );
        billUploader.setValue( image );

        created.setValue( bill.getCreatedDate() );
        modified.setValue( bill.getModificationDate() );
    }

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, BillDetail>
    {
    }
}
