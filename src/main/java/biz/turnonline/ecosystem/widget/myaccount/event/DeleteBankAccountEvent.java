package biz.turnonline.ecosystem.widget.myaccount.event;

import biz.turnonline.ecosystem.widget.shared.rest.payment.BankAccount;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DeleteBankAccountEvent
        extends GwtEvent<DeleteBankAccountEventHandler>
{
    public static Type<DeleteBankAccountEventHandler> TYPE = new Type<DeleteBankAccountEventHandler>();

    private BankAccount bankAccount;

    public DeleteBankAccountEvent( BankAccount bankAccount )
    {
        this.bankAccount = bankAccount;
    }

    public Type<DeleteBankAccountEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteBankAccountEventHandler handler )
    {
        handler.onDelete( this );
    }

    public BankAccount getBankAccount()
    {
        return bankAccount;
    }
}
