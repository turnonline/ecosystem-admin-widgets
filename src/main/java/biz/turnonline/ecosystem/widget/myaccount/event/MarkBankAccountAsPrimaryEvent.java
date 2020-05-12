package biz.turnonline.ecosystem.widget.myaccount.event;

import biz.turnonline.ecosystem.widget.shared.rest.payment.BankAccount;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class MarkBankAccountAsPrimaryEvent
        extends GwtEvent<MarkBankAccountAsPrimaryEventHandler>
{
    public static Type<MarkBankAccountAsPrimaryEventHandler> TYPE = new Type<MarkBankAccountAsPrimaryEventHandler>();

    private BankAccount bankAccount;

    public MarkBankAccountAsPrimaryEvent( BankAccount bankAccount )
    {
        this.bankAccount = bankAccount;
    }

    public Type<MarkBankAccountAsPrimaryEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( MarkBankAccountAsPrimaryEventHandler handler )
    {
        handler.onMarkAsPrimary( this );
    }

    public BankAccount getBankAccount()
    {
        return bankAccount;
    }
}
