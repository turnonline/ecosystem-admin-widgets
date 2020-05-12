package biz.turnonline.ecosystem.widget.myaccount.event;

import biz.turnonline.ecosystem.widget.shared.rest.payment.BankAccount;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SaveBankAccountEvent
        extends GwtEvent<SaveBankAccountEventHandler>
{
    public static Type<SaveBankAccountEventHandler> TYPE = new Type<SaveBankAccountEventHandler>();

    private BankAccount bankAccount;

    public SaveBankAccountEvent( BankAccount bankAccount )
    {
        this.bankAccount = bankAccount;
    }

    public Type<SaveBankAccountEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveBankAccountEventHandler handler )
    {
        handler.onSave( this );
    }

    public BankAccount getBankAccount()
    {
        return bankAccount;
    }
}
