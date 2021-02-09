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

package biz.turnonline.ecosystem.widget.myaccount.presenter;

import biz.turnonline.ecosystem.widget.myaccount.event.CreateDomainEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.DeleteBankAccountEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.DomainDeleteEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.ImportBankAccountEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.MarkBankAccountAsPrimaryEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.SaveBankAccountEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.SaveInvoicingEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.SaveWhyEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.SelectDomainType;
import biz.turnonline.ecosystem.widget.myaccount.place.Settings;
import biz.turnonline.ecosystem.widget.myaccount.ui.ImportBankAccount;
import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessOrAbsorbCallback;
import biz.turnonline.ecosystem.widget.shared.rest.account.Account;
import biz.turnonline.ecosystem.widget.shared.rest.account.Domain;
import biz.turnonline.ecosystem.widget.shared.rest.account.InvoicingConfig;
import biz.turnonline.ecosystem.widget.shared.rest.payment.BankAccount;
import com.google.gwt.place.shared.PlaceController;
import org.ctoolkit.gwt.client.facade.Items;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static biz.turnonline.ecosystem.widget.myaccount.event.SelectDomainType.DT.ROOT;

/**
 * Settings presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class SettingsPresenter
        extends Presenter<SettingsPresenter.IView>
{
    private static final int DOMAINS_LIMIT = 100;

    @Inject
    public SettingsPresenter( IView view, PlaceController controller )
    {
        super( view, controller );
        setPlace( Settings.class );
    }

    @Override
    public void bind()
    {
        // account
        bus().addHandler( SaveInvoicingEvent.TYPE,
                event -> bus()
                        .account()
                        .update( bus().config().getLoginId(), event.getInvoicing(),
                                ( response, failure ) -> {
                                    success( messages.msgRecordUpdated(), failure );
                                    Configuration.get().setCurrency( event.getInvoicing().getCurrency() );
                                } ) );

        // domains
        bus().addHandler( CreateDomainEvent.TYPE,
                event -> bus()
                        .account()
                        .create( bus().config().getLoginId(), event.getDomain(), this::domainCreated ) );

        bus().addHandler( DomainDeleteEvent.TYPE,
                event -> {
                    for ( String name : event.getDomainNames() )
                    {
                        bus().account()
                                .delete( bus().config().getLoginId(), name,
                                        ( response, failure ) -> domainDeleted( failure, name ) );
                    }
                } );

        bus().addHandler( SelectDomainType.TYPE, event -> loadDomains( event.getType() ) );

        // bank accounts
        bus().addHandler( SaveBankAccountEvent.TYPE, event -> {
            BankAccount bankAccount = event.getBankAccount();
            if ( bankAccount.getId() == null )
            {
                bus().paymentProcessor().createBankAccount( bankAccount,
                        ( response, failure ) -> bankAccountChange( messages.msgRecordCreated(), failure ) );
            }
            else
            {
                bus().paymentProcessor().updateBankAccount( bankAccount.getId(), bankAccount,
                        ( response, failure ) -> bankAccountChange( messages.msgRecordUpdated(), failure ) );
            }
        } );

        bus().addHandler( DeleteBankAccountEvent.TYPE, event -> {
            BankAccount bankAccount = event.getBankAccount();

            bus().paymentProcessor().deleteBankAccount( bankAccount.getId(),
                    ( response, failure ) -> bankAccountChange( messages.msgRecordDeleted( bankAccount.getName() ), failure ) );
        } );

        bus().addHandler( MarkBankAccountAsPrimaryEvent.TYPE, event -> {
            BankAccount bankAccount = event.getBankAccount();

            bus().paymentProcessor().markBankAccountAsPrimary( bankAccount.getId(),
                    ( response, failure ) -> bankAccountChange( messages.msgBankAccountMarkedAsPrimary( bankAccount.getName() ), failure ) );
        } );

        bus().addHandler( ImportBankAccountEvent.TYPE, event -> {
            ImportBankAccount importBankAccount = event.getImportBankAccount();

            bus().paymentProcessor().integrateWithBank( importBankAccount.getBankCode(), importBankAccount.getCertificate(),
                    ( response, failure ) -> bankAccountChange( messages.msgBankAccountImported( importBankAccount.getBankAccountName() ), failure ) );
        } );

        // why
        bus().addHandler( SaveWhyEvent.TYPE, event -> {
            Account account = event.getAccount();

            bus().account().update( bus().config().getLoginId(), account,
                    ( response, failure ) -> accountChange( messages.msgWhyCreated(), failure ) );
        } );
    }

    @Override
    public void onBackingObject()
    {
        bus().account().getInvoicingConfig( bus().config().getLoginId(), this::updateInvoicing );
        loadDomains( ROOT );
        loadBankAccounts();
        loadAccount();

        onAfterBackingObject();
    }

    /**
     * Retrieves domains from the remote server with specified limit of records and sets result into view.
     *
     * @param type the type of the domains to load
     */
    private void loadDomains( @Nonnull SelectDomainType.DT type )
    {
        switch ( type )
        {
            case ROOT:
            {
                String loginId = bus().config().getLoginId();
                // merge result of 2 calls
                bus().account().getFilteredDomains( loginId, DOMAINS_LIMIT, "naked",
                        naked -> bus().account().getFilteredDomains( loginId, DOMAINS_LIMIT, "subdomain",
                                subdomains -> {
                                    List<Domain> data = new ArrayList<>( naked.getItems() );
                                    data.addAll( subdomains.getItems() );
                                    view().setDomains( data, type );
                                } ) );
                break;
            }
            case PRODUCTS:
            {
                bus().account().getFilteredDomains( bus().config().getLoginId(), DOMAINS_LIMIT, "product",
                        r -> view().setDomains( r.getItems(), type ) );
                break;
            }
            case ALL:
            {
                bus().account().getDomains( bus().config().getLoginId(), DOMAINS_LIMIT,
                        r -> view().setDomains( r.getItems(), type ) );
                break;
            }
        }
    }

    private void domainCreated( Domain domain, FacadeCallback.Failure failure )
    {
        success( messages.msgRecordUpdated(), failure );
        if ( failure.isSuccess() )
        {
            loadDomains( ROOT );
        }
    }

    private void domainDeleted( FacadeCallback.Failure failure, String name )
    {
        success( messages.msgRecordDeleted( name ), failure );
        if ( failure.isSuccess() )
        {
            loadDomains( ROOT );
        }
    }

    private void bankAccountChange( String msg, FacadeCallback.Failure failure )
    {
        success( msg, failure );
        if ( failure.isSuccess() )
        {
            loadBankAccounts();
        }
    }

    private void accountChange( String msg, FacadeCallback.Failure failure )
    {
        success( msg, failure );
        if ( failure.isSuccess() )
        {
            loadAccount();
        }
    }

    private void updateInvoicing( InvoicingConfig invoicing, FacadeCallback.Failure failure )
    {
        if ( failure.isFailure() )
        {
            view().setModel( new InvoicingConfig() );

            if ( !failure.isNotFound() )
            {
                error( messages.msgErrorRemoteServiceCall() );
            }
        }
        else
        {
            view().setModel( invoicing );
        }
    }

    private void loadBankAccounts()
    {
        bus().paymentProcessor().getBankAccounts( 0, Integer.MAX_VALUE,
                ( SuccessOrAbsorbCallback<Items<BankAccount>> ) r -> view().setBankAccounts( r.getItems() ) );
    }

    private void loadAccount() {
        bus().account().getAccount( bus().config().getLoginId(),
                ( SuccessOrAbsorbCallback<Account> ) r -> view().setAccount( r ));
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<InvoicingConfig>
    {
        void setDomains( @Nonnull List<Domain> data, @Nonnull SelectDomainType.DT type );

        void setBankAccounts( @Nonnull List<BankAccount> data );

        void setAccount(@Nonnull Account account);
    }
}