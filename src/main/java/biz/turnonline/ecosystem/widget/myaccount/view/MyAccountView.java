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

import biz.turnonline.ecosystem.widget.myaccount.event.SaveAccountEvent;
import biz.turnonline.ecosystem.widget.myaccount.presenter.MyAccountPresenter;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.rest.account.Account;
import biz.turnonline.ecosystem.widget.shared.rest.account.AccountBusiness;
import biz.turnonline.ecosystem.widget.shared.rest.account.AccountPersonalAddress;
import biz.turnonline.ecosystem.widget.shared.rest.account.AccountPostalAddress;
import biz.turnonline.ecosystem.widget.shared.rest.account.AccountPublicContact;
import biz.turnonline.ecosystem.widget.shared.ui.CountryComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.InputSearchIcon;
import biz.turnonline.ecosystem.widget.shared.ui.LanguageComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.LegalFormComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.LogoUploader;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.ui.SectionTitle;
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
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.incubator.client.google.addresslookup.AddressLookup;
import gwt.material.design.incubator.client.google.addresslookup.js.options.PlaceResult;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

import static gwt.material.design.client.constants.IconType.BUSINESS;
import static gwt.material.design.client.constants.IconType.PERSON;

/**
 * My account and settings form. Settings visibility is based on the account scope (role).
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class MyAccountView
        extends View<Account>
        implements MyAccountPresenter.IView
{
    private static final MyAccountViewUiBinder binder = GWT.create( MyAccountViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    // account login email
    @UiField
    MaterialLabel email;

    @UiField
    MaterialTextBox contactEmail;

    @UiField
    LanguageComboBox language;

    // Personal / Company account switch
    @UiField
    MaterialSwitch company;

    /**
     * Personal data panel as a form wrapper
     */
    @UiField
    MaterialPanel personalData;

    @UiField
    MaterialTextBox prefix;

    @UiField
    MaterialTextBox firstName;

    @UiField
    MaterialTextBox middleName;

    @UiField
    MaterialTextBox lastName;

    @UiField
    MaterialTextBox suffix;

    /**
     * Company data panel as a form wrapper
     */
    @UiField
    MaterialPanel companyData;

    @UiField
    SectionTitle accountType;

    @UiField
    MaterialTextBox businessName;

    @UiField
    LegalFormComboBox legalForm;

    @UiField
    MaterialTextBox companyId;

    @UiField
    MaterialTextBox taxId;

    @UiField
    MaterialTextBox vatId;

    @UiField
    MaterialSwitch vatPayer;

    // headquarters
    @UiField
    AddressLookup companyStreet;

    @UiField
    MaterialTextBox companyCity;

    @UiField
    MaterialInputMask companyPostcode;

    @UiField
    CountryComboBox domicile;

    // public contact
    @UiField
    MaterialTextBox publicContactEmail;

    @UiField
    MaterialTextBox publicContactName;

    @UiField
    MaterialTextBox publicContactPhone;

    @UiField
    MaterialTextBox publicContactWebsite;

    // personal address
    @UiField
    AddressLookup personalStreet;

    @UiField
    MaterialTextBox personalCity;

    @UiField
    MaterialInputMask personalPostcode;

    @UiField
    CountryComboBox personalCountry;

    // postal address
    @UiField
    MaterialSwitch postalAddressSame;

    @UiField
    MaterialTextBox postalBusinessName;

    @UiField
    MaterialTextBox postalFirstName;

    @UiField
    MaterialTextBox postalLastName;

    @UiField
    MaterialTextBox postalPrefix;

    @UiField
    MaterialTextBox postalSuffix;

    @UiField
    AddressLookup postalStreet;

    @UiField
    MaterialTextBox postalCity;

    @UiField
    MaterialInputMask postalPostcode;

    @UiField
    CountryComboBox postalCountry;

    @UiField
    MaterialRow postalAddressPanel;

    @UiField( provided = true )
    LogoUploader logoUploader = new LogoUploader()
    {
        @Override
        protected void append( @Nonnull UploaderWithAuthorization.Headers headers )
        {
            headers.setLogoImage( String.valueOf( true ) );
            headers.setStampImage( String.valueOf( false ) );
        }
    };

    private boolean reloadPage;

    @Inject
    public MyAccountView( @Named( "MyAccountBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                          AddressLookupListener addressLookup )
    {
        super();

        this.breadcrumb = breadcrumb;
        setActive( Route.MY_ACCOUNT );

        add( binder.createAndBindUi( this ) );

        contactEmail.setReturnBlankAsNull( true );
        prefix.setReturnBlankAsNull( true );
        firstName.setReturnBlankAsNull( true );
        middleName.setReturnBlankAsNull( true );
        lastName.setReturnBlankAsNull( true );
        suffix.setReturnBlankAsNull( true );
        businessName.setReturnBlankAsNull( true );
        companyId.setReturnBlankAsNull( true );
        taxId.setReturnBlankAsNull( true );
        vatId.setReturnBlankAsNull( true );
        companyStreet.setReturnBlankAsNull( true );
        companyCity.setReturnBlankAsNull( true );
        companyPostcode.setReturnBlankAsNull( true );
        publicContactEmail.setReturnBlankAsNull( true );
        publicContactName.setReturnBlankAsNull( true );
        publicContactPhone.setReturnBlankAsNull( true );
        publicContactWebsite.setReturnBlankAsNull( true );
        personalStreet.setReturnBlankAsNull( true );
        personalCity.setReturnBlankAsNull( true );
        personalPostcode.setReturnBlankAsNull( true );
        postalBusinessName.setReturnBlankAsNull( true );
        postalFirstName.setReturnBlankAsNull( true );
        postalLastName.setReturnBlankAsNull( true );
        postalPrefix.setReturnBlankAsNull( true );
        postalSuffix.setReturnBlankAsNull( true );
        postalStreet.setReturnBlankAsNull( true );
        postalCity.setReturnBlankAsNull( true );
        postalPostcode.setReturnBlankAsNull( true );

        // Loading google map API
        addressLookup.onLoad( () -> {
            companyStreet.load();
            personalStreet.load();
            postalStreet.load();
        } );

        // company address lookup handler
        companyStreet.addPlaceChangedHandler( event -> {
            PlaceResult place = companyStreet.getPlace();

            companyStreet.setValue( Maps.findAddressComponent( place, "route" )
                    + " "
                    + Maps.findAddressComponent( place, "street_number" ) );

            companyCity.setValue( Maps.findAddressComponent( place, "locality", "sublocality" ) );
            companyPostcode.setValue( Maps.findAddressComponent( place, "postal_code" ) );
            companyPostcode.reload();
            domicile.setSingleValueByCode( Maps.findAddressComponent( place, "country" ) );
        } );
        companyStreet.getElement().setAttribute( "autocomplete", "off" );
        companyStreet.add( new InputSearchIcon() );

        // personal address lookup handler
        personalStreet.addPlaceChangedHandler( event -> {
            PlaceResult place = personalStreet.getPlace();

            personalStreet.setValue( Maps.findAddressComponent( place, "route" )
                    + " "
                    + Maps.findAddressComponent( place, "street_number" ) );

            personalCity.setValue( Maps.findAddressComponent( place, "locality", "sublocality" ) );
            personalPostcode.setValue( Maps.findAddressComponent( place, "postal_code" ) );
            personalPostcode.reload();
            personalCountry.setSingleValueByCode( Maps.findAddressComponent( place, "country" ) );
        } );
        personalStreet.getElement().setAttribute( "autocomplete", "off" );
        personalStreet.add( new InputSearchIcon() );

        // postal address lookup handler
        postalStreet.addPlaceChangedHandler( event -> {
            PlaceResult place = postalStreet.getPlace();

            postalStreet.setValue( Maps.findAddressComponent( place, "route" )
                    + " "
                    + Maps.findAddressComponent( place, "street_number" ) );

            postalCity.setValue( Maps.findAddressComponent( place, "locality", "sublocality" ) );
            postalPostcode.setValue( Maps.findAddressComponent( place, "postal_code" ) );
            postalPostcode.reload();
            postalCountry.setSingleValueByCode( Maps.findAddressComponent( place, "country" ) );
        } );
        postalStreet.add( new InputSearchIcon() );
        postalStreet.getElement().setAttribute( "autocomplete", "off" );

        language.addValueChangeHandler( ( event ) -> reloadPage = true );
    }

    @Override
    protected void beforeGetModel()
    {
        Account account = getRawModel();

        // basic account
        account.setContactEmail( contactEmail.getValue() );
        account.setCompany( company.getValue() );
        account.setLocale( language.getSingleValueByCode() );

        account.setPrefix( prefix.getValue() );
        account.setFirstName( firstName.getValue() );
        account.setMiddleName( middleName.getValue() );
        account.setLastName( lastName.getValue() );
        account.setSuffix( suffix.getValue() );

        // business
        AccountBusiness business = new AccountBusiness();
        business.setBusinessName( businessName.getValue() );
        business.setCompanyId( companyId.getValue() );
        business.setTaxId( taxId.getValue() );
        business.setVatId( vatId.getValue() );

        business.setStreet( companyStreet.getValue() );
        business.setCity( companyCity.getValue() );
        business.setPostcode( companyPostcode.getValue() );
        business.setLogo( logoUploader.getValue() );

        if ( account.setBusinessIf( business ) )
        {
            // set only if there are another values, sending default values makes no sense
            business.setLegalForm( legalForm.getSingleValueByCode() );
            business.setVatPayer( vatPayer.getValue() );
            business.setDomicile( domicile.getSingleValueByCode() );
        }

        // personal address
        AccountPersonalAddress personalAddress = new AccountPersonalAddress();
        personalAddress.setStreet( personalStreet.getValue() );
        personalAddress.setCity( personalCity.getValue() );
        personalAddress.setPostcode( personalPostcode.getValue() );

        if ( account.setPersonalAddressIf( personalAddress ) )
        {
            // set only if there are another values, sending default values makes no sense
            personalAddress.setCountry( personalCountry.getSingleValueByCode() );
        }

        // public contact
        AccountPublicContact publicContact = new AccountPublicContact();
        publicContact.setEmail( publicContactEmail.getValue() );
        publicContact.setName( publicContactName.getValue() );
        publicContact.setPhone( publicContactPhone.getValue() );
        publicContact.setWebsite( publicContactWebsite.getValue() );

        account.setPublicContactIf( publicContact );

        // postal address
        AccountPostalAddress postalAddress = new AccountPostalAddress();
        postalAddress.setBusinessName( postalBusinessName.getValue() );
        postalAddress.setFirstName( postalFirstName.getValue() );
        postalAddress.setLastName( postalLastName.getValue() );
        postalAddress.setPrefix( postalPrefix.getValue() );
        postalAddress.setSuffix( postalSuffix.getValue() );

        postalAddress.setStreet( postalStreet.getValue() );
        postalAddress.setCity( postalCity.getValue() );
        postalAddress.setPostcode( postalPostcode.getValue() );

        account.setHasPostalAddress( postalAddressSame.getValue() );
        if ( account.setPostalAddressIf( postalAddress ) )
        {
            // set only if there are another values, sending default values makes no sense
            postalAddress.setCountry( postalCountry.getSingleValueByCode() );
        }

        // invoicing config, not handled here
        account.setInvoicing( null );
    }

    @Override
    protected void afterSetModel()
    {
        this.reloadPage = false;

        Account account = getRawModel();
        email.setValue( account.getEmail() );
        contactEmail.setValue( account.getContactEmail() );
        company.setValue( account.getCompany() != null && account.getCompany() );
        language.setSingleValueByCode( account.getLocale() );

        prefix.setValue( account.getPrefix() );
        firstName.setValue( account.getFirstName() );
        middleName.setValue( account.getMiddleName() );
        lastName.setValue( account.getLastName() );
        suffix.setValue( account.getSuffix() );

        AccountBusiness business = account.getBusiness();
        if ( business == null )
        {
            businessName.setValue( null );
            legalForm.setSingleValueByCode( null );
            companyId.setValue( null );
            taxId.setValue( null );
            vatId.setValue( null );
            vatPayer.setValue( false );

            companyStreet.setValue( null );
            companyCity.setValue( null );
            companyPostcode.setValue( null );
            domicile.setValue( null );
        }
        else
        {
            businessName.setValue( business.getBusinessName() );
            legalForm.setSingleValueByCode( business.getLegalForm() );
            companyId.setValue( business.getCompanyId() );
            taxId.setValue( business.getTaxId() );
            vatId.setValue( business.getVatId() );
            vatPayer.setValue( business.getVatPayer() != null && business.getVatPayer() );

            companyStreet.setValue( business.getStreet() );
            companyCity.setValue( business.getCity() );
            companyPostcode.setValue( business.getPostcode() );
            domicile.setSingleValueByCode( business.getDomicile() );
            logoUploader.setValue( business.getLogo() );
        }

        AccountPersonalAddress personalAddress = account.getPersonalAddress();
        if ( personalAddress == null )
        {
            personalStreet.setValue( null );
            personalCity.setValue( null );
            personalPostcode.setValue( null );
            personalCountry.setSingleValueByCode( null );
        }
        else
        {
            personalStreet.setValue( personalAddress.getStreet() );
            personalCity.setValue( personalAddress.getCity() );
            personalPostcode.setValue( personalAddress.getPostcode() );
            personalCountry.setSingleValueByCode( personalAddress.getCountry() );
        }

        AccountPublicContact publicContact = account.getPublicContact();
        if ( publicContact == null )
        {
            publicContactEmail.setValue( null );
            publicContactName.setValue( null );
            publicContactPhone.setValue( null );
            publicContactWebsite.setValue( null );
        }
        else
        {
            publicContactEmail.setValue( publicContact.getEmail() );
            publicContactName.setValue( publicContact.getName() );
            publicContactPhone.setValue( publicContact.getPhone() );
            publicContactWebsite.setValue( publicContact.getWebsite() );
        }

        AccountPostalAddress postalAddress = account.getPostalAddress();
        Boolean hasPostalAddress = account.getHasPostalAddress();
        postalAddressSame.setValue( hasPostalAddress == null || hasPostalAddress );

        if ( postalAddress == null )
        {
            postalBusinessName.setValue( null );
            postalFirstName.setValue( null );
            postalLastName.setValue( null );
            postalPrefix.setValue( null );
            postalSuffix.setValue( null );

            postalStreet.setValue( null );
            postalCity.setValue( null );
            postalPostcode.setValue( null );
            postalCountry.setSingleValueByCode( null );
        }
        else
        {
            postalBusinessName.setValue( postalAddress.getBusinessName() );
            postalFirstName.setValue( postalAddress.getFirstName() );
            postalLastName.setValue( postalAddress.getLastName() );
            postalPrefix.setValue( postalAddress.getPrefix() );
            postalSuffix.setValue( postalAddress.getSuffix() );

            postalStreet.setValue( postalAddress.getStreet() );
            postalCity.setValue( postalAddress.getCity() );
            postalPostcode.setValue( postalAddress.getPostcode() );
            postalCountry.setSingleValueByCode( postalAddress.getCountry() );
        }

        handleVatPayer();
        handleAccountType();
        handlePostalAddressVisibility();
    }

    private void handleVatPayer()
    {
        vatId.setEnabled( vatPayer.getValue() );
    }

    private void handleAccountType()
    {
        Boolean isCompany = company.getValue();
        String prefix = messages.labelAccountType() + ": ";

        if ( isCompany )
        {
            accountType.setTitle( prefix + messages.labelCompanyAccount() );
            accountType.setIconType( BUSINESS );
            personalData.setVisible( false );
            companyData.setVisible( true );

            postalBusinessName.setVisible( true );
            postalFirstName.setVisible( false );
            postalLastName.setVisible( false );
            postalPrefix.setVisible( false );
            postalSuffix.setVisible( false );
            postalAddressSame.setOnLabel( messages.labelSameCompanyAddress() );
        }
        else
        {
            accountType.setTitle( prefix + messages.labelPersonalAccount() );
            accountType.setIconType( PERSON );
            personalData.setVisible( true );
            companyData.setVisible( false );

            postalBusinessName.setVisible( false );
            postalFirstName.setVisible( true );
            postalLastName.setVisible( true );
            postalPrefix.setVisible( true );
            postalSuffix.setVisible( true );
            postalAddressSame.setOnLabel( messages.labelSamePersonalAddress() );
        }
    }

    private void handlePostalAddressVisibility()
    {
        postalAddressPanel.setVisible( !postalAddressSame.getValue() );
    }

    @UiHandler( "company" )
    void onCompanyChange( ValueChangeEvent<Boolean> e )
    {
        handleAccountType();
    }

    @UiHandler( "vatPayer" )
    void onVatPayerChange( ValueChangeEvent<Boolean> e )
    {
        handleVatPayer();
    }

    @UiHandler( "postalAddressSame" )
    public void postalAddressSameClick( ValueChangeEvent<Boolean> e )
    {
        handlePostalAddressVisibility();
    }

    @UiHandler( "btnSave" )
    public void btnSaveClick( ClickEvent event )
    {
        bus().fireEvent( new SaveAccountEvent( getModel(), reloadPage ) );
    }

    interface MyAccountViewUiBinder
            extends UiBinder<HTMLPanel, MyAccountView>
    {
    }
}