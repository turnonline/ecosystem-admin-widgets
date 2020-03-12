package biz.turnonline.ecosystem.widget.bill.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.ui.BillTypeComboBox;
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

    private static AppMessages messages = AppMessages.INSTANCE;

    @UiField
    MaterialTextBox billNumber;

    @UiField
    MaterialTextBox itemName;

    @UiField
    MaterialDoubleBox totalPrice;

    @UiField
    BillTypeComboBox billType;

    @UiField
    MaterialDatePicker dateOfIssue;

    @UiField
    MaterialDatePicker created;

    @UiField
    MaterialDatePicker modified;

    public BillDetail()
    {
        initWidget( binder.createAndBindUi( this ) );
    }

    public void bind( @Nonnull Bill bill )
    {
        bill.setItemName( itemName.getValue() );
        bill.setBillNumber( billNumber.getValue() );
        bill.setDateOfIssue( dateOfIssue.getValue() );
        bill.setTotalPrice( totalPrice.getValue() );
        bill.setType( billType.getSingleValueByCode() != null ? Bill.TypeEnum.valueOf( billType.getSingleValueByCode() ) : null );
    }

    public void fill( @Nonnull Bill bill )
    {
        itemName.setValue( bill.getItemName() );
        billNumber.setValue( bill.getBillNumber() );
        dateOfIssue.setValue( bill.getDateOfIssue() );
        totalPrice.setValue( bill.getTotalPrice() );
        billType.setSingleValueByCode( bill.getType() != null ? bill.getType().name() : null );

        created.setValue( bill.getCreatedDate() );
        modified.setValue( bill.getModificationDate() );
    }

    interface DetailUiBinder
            extends UiBinder<HTMLPanel, BillDetail>
    {
    }
}
