package biz.turnonline.ecosystem.widget.myaccount.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Bank;
import biz.turnonline.ecosystem.widget.shared.rest.payment.BankAccount;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialPanel;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class BankAccountsPanel
        extends Composite
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static BankAccountsPanelUiBinder binder = GWT.create( BankAccountsPanelUiBinder.class );

    @UiField
    MaterialColumn grid;

    @UiField
    MaterialButton btnNew;

    private EventBus bus;

    public BankAccountsPanel( EventBus bus )
    {
        initWidget( binder.createAndBindUi( this ) );
        this.bus = bus;
    }

    public void setBankAccounts( List<BankAccount> bankAccounts )
    {
        grid.clear();
        bankAccounts.forEach( this::addItem );
    }

    private void addItem( BankAccount bankAccount )
    {
        grid.add( createItem( bankAccount ) );
    }

    private MaterialWidget createItem( BankAccount bankAccount )
    {
        MaterialPanel panel = new MaterialPanel();
        panel.setGrid( "s12 m6" );

        BankAccountCard card = new BankAccountCard( bankAccount, bus );
        card.setValue( bankAccount );
        panel.add( card );

        return panel;
    }

    @UiHandler( "btnNew" )
    public void newItem( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setCurrency( Configuration.get().getCurrency() );
        bankAccount.setPrimary( grid.getChildrenList().size() == 0 );
        bankAccount.setBank( new Bank() );

        addItem( bankAccount );
    }

    interface BankAccountsPanelUiBinder
            extends UiBinder<HTMLPanel, BankAccountsPanel>
    {
    }
}
