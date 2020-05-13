/*
 *  Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package biz.turnonline.ecosystem.widget.myaccount.ui;

import biz.turnonline.ecosystem.widget.myaccount.event.DeleteBankAccountEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.MarkBankAccountAsPrimaryEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.SaveBankAccountEvent;
import biz.turnonline.ecosystem.widget.shared.rest.CodeBookRestFacade;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Bank;
import biz.turnonline.ecosystem.widget.shared.rest.payment.BankAccount;
import biz.turnonline.ecosystem.widget.shared.rest.payment.BankCode;
import biz.turnonline.ecosystem.widget.shared.ui.CurrencyComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.inputmask.MaterialInputMask;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Order overview card component.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class BankAccountCard
        extends Composite
        implements TakesValue<BankAccount>
{
    private static OrderCardUiBinder binder = GWT.create( OrderCardUiBinder.class );

    @UiField
    MaterialCard card;

    @UiField
    MaterialTextBox name;

    @UiField
    CurrencyComboBox currency;


    @UiField
    MaterialInputMask iban;

    @UiField
    MaterialTextBox bic;

    @UiField
    MaterialTextBox bankCode;

    @UiField
    MaterialLink saveLink;

    @UiField
    MaterialLink deleteLink;

    @UiField
    MaterialLink markAsPrimaryLink;

    @UiField
    MaterialIcon primary;

    private BankAccount bankAccount;

    private List<BankCode> bankCodes;

    private BankCode selectedBankCode;

    private EventBus bus;

    public BankAccountCard( @Nonnull BankAccount bankAccount, @Nonnull EventBus bus )
    {
        this.bankAccount = bankAccount;
        this.bus = bus;

        initWidget( binder.createAndBindUi( this ) );
    }

    @UiHandler( "saveLink" )
    public void save( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus.fireEvent( new SaveBankAccountEvent( getValue() ) );
    }

    @UiHandler( "deleteLink" )
    public void delete( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        if ( bankAccount.getId() != null )
        {
            bus.fireEvent( new DeleteBankAccountEvent( getValue() ) );
        }
        else
        {
            onDelete( getValue() );
        }
    }

    @UiHandler( "markAsPrimaryLink" )
    public void markAsPrimary( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        if ( bankAccount.getId() != null )
        {
            bus.fireEvent( new MarkBankAccountAsPrimaryEvent( getValue() ) );
        }
    }

    @UiHandler( "iban" )
    public void ibanAutocomplete( @SuppressWarnings( "unused" ) KeyUpEvent event )
    {
        String value = iban.getValue();
        if ( value != null && value.length() >= 8 )
        {
            String bankCodeValue = value.substring( 4, 8 );

            if ( bankCodes == null )
            {
                CodeBookRestFacade.getCodeBook( BankCode.class, response -> {
                    bankCodes = response.getItems();
                    setBankCode( bankCodeValue );
                } );
            }
            else
            {
                setBankCode( bankCodeValue );
            }
        }
    }

    protected void onDelete( BankAccount bankAccount )
    {
        getParent().removeFromParent();
    }

    @Override
    public void setValue( BankAccount value )
    {
        name.setValue( bankAccount.getName() );
        currency.setSingleValue( bankAccount.getCurrency() );

        iban.setValue( bankAccount.getIban() );
        bic.setValue( bankAccount.getBic() );
        bankCode.setValue( bankAccount.getBank().getLabel() );

        markAsPrimaryLink.setVisible( true );
        primary.setVisible( false );
        if ( bankAccount.isPrimary() )
        {
            markAsPrimaryLink.setVisible( false );
            primary.setVisible( true );
        }

        if ( value.getId() == null )
        {
            markAsPrimaryLink.setVisible( false );
        }

        boolean importedBankAccount = isImportedBankAccount( value.getBank().getCode() );
        setReadOnly( importedBankAccount );
    }

    @Override
    public BankAccount getValue()
    {
        bankAccount.setName( name.getValue() );
        bankAccount.setCurrency( currency.getSingleValue() );

        bankAccount.setIban( iban.getValue() );
        bankAccount.setBic( bic.getValue() );
        bankAccount.setBank( getBank() );

        return bankAccount;
    }

    private Bank getBank()
    {
        Bank bank = Optional.ofNullable( bankAccount.getBank() ).orElse( new Bank() );

        if ( selectedBankCode != null )
        {
            bank.setCode( selectedBankCode.getCode() );
            bank.setCountry( selectedBankCode.getCountry() );
            bank.setLabel( selectedBankCode.getLabel() );
        }

        return bank;
    }

    interface OrderCardUiBinder
            extends UiBinder<MaterialCard, BankAccountCard>
    {
    }

    private void setBankCode( String bankCodeValue )
    {
        BankCode bankCodeFiltered = bankCodes.stream()
                .filter( bc -> bc.getCode().equals( bankCodeValue ) )
                .findFirst()
                .orElse( null );

        if ( bankCodeFiltered != null )
        {
            selectedBankCode = bankCodeFiltered;
            bankCode.setValue( bankCodeFiltered.getLabel() );

            if (name.getValue().isEmpty())
            {
                name.setValue( bankCodeFiltered.getLabel() );
            }
        }
    }

    private boolean isImportedBankAccount( String bankCode) {
        return Arrays.asList("REVO").contains( bankCode );
    }

    private void setReadOnly(boolean readOnly) {
        name.setEnabled( !readOnly );
        currency.setEnabled( !readOnly );
        iban.setEnabled( !readOnly );
        bic.setEnabled( !readOnly );

        saveLink.setVisible( !readOnly );
        deleteLink.setVisible( !readOnly );
    }
}