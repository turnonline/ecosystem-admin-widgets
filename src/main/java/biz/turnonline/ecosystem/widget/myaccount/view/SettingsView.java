/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.myaccount.view;

import biz.turnonline.ecosystem.widget.myaccount.event.SaveInvoicingEvent;
import biz.turnonline.ecosystem.widget.myaccount.presenter.SettingsPresenter;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.rest.account.InvoicingConfig;
import biz.turnonline.ecosystem.widget.shared.rest.account.InvoicingConfigBillingAddress;
import biz.turnonline.ecosystem.widget.shared.rest.account.InvoicingConfigBillingContact;
import biz.turnonline.ecosystem.widget.shared.ui.CountryComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.CurrencyComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.InputSearchIcon;
import biz.turnonline.ecosystem.widget.shared.ui.LogoUploader;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.util.Maps;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.inputmask.MaterialInputMask;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.incubator.client.google.addresslookup.AddressLookup;
import gwt.material.design.incubator.client.google.addresslookup.js.options.PlaceResult;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class SettingsView
        extends View<InvoicingConfig>
        implements SettingsPresenter.IView
{
    private static SettingsViewUiBinder binder = GWT.create( SettingsViewUiBinder.class );

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
    MaterialInputMask<String> billingAddressPostcode;

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
    LogoUploader logoUploader;

    @UiField
    LogoUploader stampUploader;

    @Inject
    public SettingsView( EventBus eventBus,
                         @Named( "SettingsBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                         AddressLookupListener addressLookup )
    {
        super( eventBus );

        this.breadcrumb = breadcrumb;
        setActive( Route.MY_ACCOUNT );

        add( binder.createAndBindUi( this ) );

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
    protected void beforeGetModel()
    {
        InvoicingConfig invoicing = getRawModel();

        invoicing.setCurrency( currency.getSingleValue() );
        invoicing.setNumberOfDays( numberOfDays.getValue() );
        invoicing.setHasBillingAddress( hasBillingAddress.getValue() );

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

        // set(introductoryText.getValue());
        // set(finalText.getValue());
        // set(logoUploader.getValue());
        // set(stampUploader.getValue());
    }

    @Override
    protected void afterSetModel()
    {
        InvoicingConfig invoicing = getRawModel();

        currency.setSingleValue( invoicing.getCurrency() );
        numberOfDays.setValue( invoicing.getNumberOfDays() );
        Boolean hasBillingAddress = invoicing.getHasBillingAddress();
        this.hasBillingAddress.setValue( hasBillingAddress == null ? false : hasBillingAddress );

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

    interface SettingsViewUiBinder
            extends UiBinder<HTMLPanel, SettingsView>
    {
    }
}