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

package biz.turnonline.ecosystem.widget.myaccount.view;

import biz.turnonline.ecosystem.widget.myaccount.event.SaveInvoicingEvent;
import biz.turnonline.ecosystem.widget.myaccount.event.SelectDomainType;
import biz.turnonline.ecosystem.widget.myaccount.presenter.SettingsPresenter;
import biz.turnonline.ecosystem.widget.myaccount.ui.BankAccountsPanel;
import biz.turnonline.ecosystem.widget.myaccount.ui.DomainsPanel;
import biz.turnonline.ecosystem.widget.myaccount.ui.WhyPanel;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.rest.account.Account;
import biz.turnonline.ecosystem.widget.shared.rest.account.Domain;
import biz.turnonline.ecosystem.widget.shared.rest.account.InvoicingConfig;
import biz.turnonline.ecosystem.widget.shared.rest.account.InvoicingConfigBillingAddress;
import biz.turnonline.ecosystem.widget.shared.rest.account.InvoicingConfigBillingContact;
import biz.turnonline.ecosystem.widget.shared.rest.payment.BankAccount;
import biz.turnonline.ecosystem.widget.shared.ui.CountryComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.CurrencyComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.InputSearchIcon;
import biz.turnonline.ecosystem.widget.shared.ui.LogoUploader;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.ui.UploaderWithAuthorization;
import biz.turnonline.ecosystem.widget.shared.util.Maps;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.addins.client.inputmask.MaterialInputMask;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.incubator.client.google.addresslookup.AddressLookup;
import gwt.material.design.incubator.client.google.addresslookup.js.options.PlaceResult;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class SettingsView
        extends View<InvoicingConfig>
        implements SettingsPresenter.IView
{
    private static final SettingsViewUiBinder binder = GWT.create( SettingsViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    // invoicing configuration
    @UiField
    CurrencyComboBox currency;

    @UiField
    MaterialIntegerBox numberOfDays;

    @UiField
    MaterialSwitch hasBillingAddress;

    // billing address
    @UiField
    MaterialTextBox billingBusinessName;

    @UiField
    AddressLookup billingAddressStreet;

    @UiField
    MaterialTextBox billingAddressCity;

    @UiField
    MaterialInputMask billingAddressPostcode;

    @UiField
    CountryComboBox billingAddressCountry;

    // billing contact details
    @UiField
    MaterialTextBox billingContactEmail;

    @UiField
    MaterialTextBox billingContactPhone;

    @UiField
    MaterialTextBox billingContactPrefix;

    @UiField
    MaterialTextBox billingContactFirstName;

    @UiField
    MaterialTextBox billingContactMiddleName;

    @UiField
    MaterialTextBox billingContactLastName;

    @UiField
    MaterialTextBox billingContactSuffix;

    @UiField
    MaterialTextArea introductoryText;

    @UiField
    MaterialTextArea finalText;

    @UiField
    LogoUploader stampUploader;

    @UiField( provided = true )
    DomainsPanel domains;

    @UiField( provided = true )
    BankAccountsPanel bankAccounts;

    @UiField( provided = true )
    WhyPanel why;

    @Inject
    public SettingsView( @Named( "SettingsBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                         AddressLookupListener addressLookup )
    {
        super();

        this.breadcrumb = breadcrumb;
        this.domains = new DomainsPanel( bus() );
        this.bankAccounts = new BankAccountsPanel( bus() );
        this.why = new WhyPanel( bus() );

        setActive( Route.MY_ACCOUNT );

        add( binder.createAndBindUi( this ) );

        stampUploader.addAppendHeadersCallback( this::append );
        numberOfDays.setReturnBlankAsNull( true );
        billingBusinessName.setReturnBlankAsNull( true );
        billingAddressStreet.setReturnBlankAsNull( true );
        billingAddressCity.setReturnBlankAsNull( true );
        billingAddressPostcode.setReturnBlankAsNull( true );
        billingContactEmail.setReturnBlankAsNull( true );
        billingContactPhone.setReturnBlankAsNull( true );
        billingContactPrefix.setReturnBlankAsNull( true );
        billingContactFirstName.setReturnBlankAsNull( true );
        billingContactMiddleName.setReturnBlankAsNull( true );
        billingContactLastName.setReturnBlankAsNull( true );
        billingContactSuffix.setReturnBlankAsNull( true );
        introductoryText.setReturnBlankAsNull( true );
        finalText.setReturnBlankAsNull( true );

        // Loading google map API
        addressLookup.onLoad( () -> billingAddressStreet.load() );

        // company address lookup handler
        billingAddressStreet.addPlaceChangedHandler( event -> {
            PlaceResult place = billingAddressStreet.getPlace();

            billingAddressStreet.setValue( Maps.findAddressComponent( place, "route" )
                    + " "
                    + Maps.findAddressComponent( place, "street_number" ) );

            billingAddressCity.setValue( Maps.findAddressComponent( place, "locality", "sublocality" ) );
            billingAddressPostcode.setValue( Maps.findAddressComponent( place, "postal_code" ) );
            billingAddressPostcode.reload();
            billingAddressCountry.setSingleValueByCode( Maps.findAddressComponent( place, "country" ) );
        } );
        billingAddressStreet.getElement().setAttribute( "autocomplete", "off" );
        billingAddressStreet.add( new InputSearchIcon() );
    }

    @Override
    protected void beforeGetModel( InvoicingConfig invoicing )
    {
        invoicing.setCurrency( currency.getSingleValue() );
        invoicing.setIntroductoryText( introductoryText.getValue() );
        invoicing.setFinalText( finalText.getValue() );
        invoicing.setNumberOfDays( numberOfDays.getValue() );
        invoicing.setHasBillingAddress( hasBillingAddress.getValue() );
        invoicing.setStamp( stampUploader.getValue() );

        InvoicingConfigBillingAddress billingAddress = new InvoicingConfigBillingAddress();

        billingAddress.setBusinessName( billingBusinessName.getValue() );
        billingAddress.setStreet( billingAddressStreet.getValue() );
        billingAddress.setCity( billingAddressCity.getValue() );
        billingAddress.setPostcode( billingAddressPostcode.getValue() );
        if ( invoicing.setBillingAddressIf( billingAddress ) )
        {
            // set only if there are another values, sending default values makes no sense
            billingAddress.setCountry( billingAddressCountry.getSingleValueByCode() );
        }

        InvoicingConfigBillingContact billingContact = new InvoicingConfigBillingContact();
        billingContact.setEmail( billingContactEmail.getValue() );
        billingContact.setPhone( billingContactPhone.getValue() );
        billingContact.setPrefix( billingContactPrefix.getValue() );
        billingContact.setFirstName( billingContactFirstName.getValue() );
        billingContact.setMiddleName( billingContactMiddleName.getValue() );
        billingContact.setLastName( billingContactLastName.getValue() );
        billingContact.setSuffix( billingContactSuffix.getValue() );
        invoicing.setBillingContactIf( billingContact );
    }

    @Override
    protected void afterSetModel( InvoicingConfig invoicing )
    {
        currency.setSingleValue( invoicing.getCurrency() );
        introductoryText.setValue( invoicing.getIntroductoryText() );
        finalText.setValue( invoicing.getFinalText() );
        numberOfDays.setValue( invoicing.getNumberOfDays() );
        stampUploader.setValue( invoicing.getStamp() );

        Boolean hasBillingAddress = invoicing.getHasBillingAddress();
        this.hasBillingAddress.setValue( hasBillingAddress != null && hasBillingAddress );

        InvoicingConfigBillingAddress billingAddress = invoicing.getBillingAddress();
        if ( billingAddress != null )
        {
            billingBusinessName.setValue( billingAddress.getBusinessName() );
            billingAddressStreet.setValue( billingAddress.getStreet() );
            billingAddressCity.setValue( billingAddress.getCity() );
            billingAddressPostcode.setValue( billingAddress.getPostcode() );
            billingAddressCountry.setSingleValueByCode( billingAddress.getCountry() );
        }

        InvoicingConfigBillingContact billingContact = invoicing.getBillingContact();
        if ( billingContact != null )
        {
            billingContactEmail.setValue( billingContact.getEmail() );
            billingContactPhone.setValue( billingContact.getPhone() );
            billingContactPrefix.setValue( billingContact.getPrefix() );
            billingContactFirstName.setValue( billingContact.getFirstName() );
            billingContactMiddleName.setValue( billingContact.getMiddleName() );
            billingContactLastName.setValue( billingContact.getLastName() );
            billingContactSuffix.setValue( billingContact.getSuffix() );
        }

        handleHasBillingAddress();
    }

    private void handleHasBillingAddress()
    {
        Boolean isBillingAddress = hasBillingAddress.getValue();
        billingBusinessName.setEnabled( isBillingAddress );
        billingAddressStreet.setEnabled( isBillingAddress );
        billingAddressCity.setEnabled( isBillingAddress );
        billingAddressPostcode.setEnabled( isBillingAddress );
        billingAddressCountry.setEnabled( isBillingAddress );
    }

    private void append( @Nonnull UploaderWithAuthorization.Headers headers )
    {
        headers.setStampImage( String.valueOf( true ) );
        headers.setLogoImage( String.valueOf( false ) );
    }

    @UiHandler( "hasBillingAddress" )
    void onHasBillingAddress( ValueChangeEvent<Boolean> e )
    {
        handleHasBillingAddress();
    }

    @UiHandler( "btnSave" )
    public void onSaveClick( ClickEvent event )
    {
        bus().fireEvent( new SaveInvoicingEvent( getModel() ) );
    }

    @Override
    public void setDomains( @Nonnull List<Domain> data, @Nonnull SelectDomainType.DT type )
    {
        domains.setDomains( data, type );
    }

    @Override
    public void setBankAccounts( @Nonnull List<BankAccount> data )
    {
        bankAccounts.setBankAccounts( data );
    }

    @Override
    public void setAccount( @Nonnull Account account )
    {
        why.setValue( account );
    }

    interface SettingsViewUiBinder
            extends UiBinder<HTMLPanel, SettingsView>
    {
    }
}