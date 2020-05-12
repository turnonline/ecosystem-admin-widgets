package biz.turnonline.ecosystem.widget.myaccount.event;

import biz.turnonline.ecosystem.widget.myaccount.ui.ImportBankAccount;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ImportBankAccountEvent
        extends GwtEvent<ImportBankAccountEventHandler>
{
    public static Type<ImportBankAccountEventHandler> TYPE = new Type<ImportBankAccountEventHandler>();

    private ImportBankAccount importBankAccount;

    public ImportBankAccountEvent( ImportBankAccount importBankAccount )
    {
        this.importBankAccount = importBankAccount;
    }

    public Type<ImportBankAccountEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( ImportBankAccountEventHandler handler )
    {
        handler.onImport( this );
    }

    public ImportBankAccount getImportBankAccount()
    {
        return importBankAccount;
    }
}
