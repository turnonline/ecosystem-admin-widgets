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

import biz.turnonline.ecosystem.widget.myaccount.presenter.SettingsPresenter;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.rest.account.InvoicingConfig;
import biz.turnonline.ecosystem.widget.shared.rest.account.InvoicingConfigBillingAddress;
import biz.turnonline.ecosystem.widget.shared.rest.account.InvoicingConfigBillingContact;
import biz.turnonline.ecosystem.widget.shared.ui.CountryComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.CurrencyComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.InputSearchIcon;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.util.Maps;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.inputmask.MaterialInputMask;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialSwitch;
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

    @Inject
    public SettingsView( EventBus eventBus,
                         @Named( "SettingsBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                         AddressLookupListener addressLookup )
    {
        super( eventBus );

        this.breadcrumb = breadcrumb;
        setActive( Route.MY_ACCOUNT );

        add( binder.createAndBindUi( this ) );

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
    protected void afterSetModel()
    {
        InvoicingConfig invoicing = getRawModel();

        currency.setSingleValue( invoicing.getCurrency() );
        numberOfDays.setValue( invoicing.getNumberOfDays() );
        hasBillingAddress.setValue( invoicing.getHasBillingAddress() );

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
    }

    interface SettingsViewUiBinder
            extends UiBinder<HTMLPanel, SettingsView>
    {
    }
}