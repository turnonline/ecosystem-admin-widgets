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

package biz.turnonline.ecosystem.widget.billing.ui;

import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.account.ContactCard;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Customer;
import biz.turnonline.ecosystem.widget.shared.rest.billing.CustomerPostalAddress;
import biz.turnonline.ecosystem.widget.shared.rest.billing.HasCustomer;
import biz.turnonline.ecosystem.widget.shared.rest.search.SearchContact;
import biz.turnonline.ecosystem.widget.shared.ui.ContactAutoComplete;
import biz.turnonline.ecosystem.widget.shared.ui.CountryComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import biz.turnonline.ecosystem.widget.shared.ui.InputSearchIcon;
import biz.turnonline.ecosystem.widget.shared.util.Maps;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.inputmask.MaterialInputMask;
import gwt.material.design.client.constants.CssName;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.incubator.client.google.addresslookup.AddressLookup;
import gwt.material.design.incubator.client.google.addresslookup.js.options.PlaceResult;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CustomerPanel<T extends HasCustomer>
        extends Composite
        implements HasModel<T>
{
    private static CustomerPanelUiBinder binder = GWT.create( CustomerPanelUiBinder.class );

    // person

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

    // company

    @UiField( provided = true )
    ContactAutoComplete businessName;

    @UiField
    MaterialTextBox companyId;

    @UiField
    MaterialTextBox taxId;

    @UiField
    MaterialTextBox vatId;

    // contacts

    @UiField
    MaterialTextBox phone;

    @UiField
    MaterialTextBox email;

    @UiField
    MaterialTextBox ccEmail;

    // invoice address

    @UiField
    AddressLookup street;

    @UiField
    MaterialTextBox city;

    @UiField
    MaterialInputMask<String> postCode;

    @UiField
    CountryComboBox country;

    // postal address

    @UiField
    MaterialTextBox postalBusinessName;

    @UiField
    MaterialTextBox postalPrefix;

    @UiField
    MaterialTextBox postalFirstName;

    @UiField
    MaterialTextBox postalLastName;

    @UiField
    MaterialTextBox postalSuffix;

    @UiField
    AddressLookup postalStreet;

    @UiField
    MaterialTextBox postalCity;

    @UiField
    MaterialInputMask<String> postalPostCode;

    @UiField
    CountryComboBox postalCountry;

    private EventBus eventBus;

    public CustomerPanel( EventBus eventBus, AddressLookupListener addressLookup )
    {
        this.eventBus = eventBus;

        businessName = createAutocomplete();

        initWidget( binder.createAndBindUi( this ) );

        // Loading google map API
        addressLookup.onLoad( () -> {
            street.load();
            postalStreet.load();
        } );

        street.addPlaceChangedHandler( event -> {
            PlaceResult place = street.getPlace();

            street.setValue( Maps.findAddressComponent( place, "route" ) + " " + Maps.findAddressComponent( place, "street_number" ) );
            city.setValue( Maps.findAddressComponent( place, "locality", "sublocality" ) );
            postCode.setValue( Maps.findAddressComponent( place, "postal_code" ) );
            postCode.reload();
            country.setSingleValueByCode( Maps.findAddressComponent( place, "country" ) );
        } );
        street.getElement().setAttribute( "autocomplete", "off" );
        street.add( new InputSearchIcon() );

        postalStreet.addPlaceChangedHandler( event -> {
            PlaceResult place = postalStreet.getPlace();

            postalStreet.setValue( Maps.findAddressComponent( place, "route" ) + " " + Maps.findAddressComponent( place, "street_number" ) );
            postalCity.setValue( Maps.findAddressComponent( place, "locality", "sublocality" ) );
            postalPostCode.setValue( Maps.findAddressComponent( place, "postal_code" ) );
            postalPostCode.reload();
            postalCountry.setSingleValueByCode( Maps.findAddressComponent( place, "country" ) );
        } );
        postalStreet.add( new InputSearchIcon() );
        postalStreet.getElement().setAttribute( "autocomplete", "off" );
    }

    @Override
    public void bind( T model )
    {
        Customer contact = model.getCustomer();

        // person
        contact.setPrefix( prefix.getValue() );
        contact.setFirstName( firstName.getValue() );
        contact.setMiddleName( middleName.getValue() );
        contact.setLastName( lastName.getValue() );
        contact.setSuffix( suffix.getValue() );

        // company
        contact.setBusinessName( businessName.getItemBox().getValue() );
        contact.setCompanyId( companyId.getValue() );
        contact.setVatId( vatId.getValue() );
        contact.setTaxId( taxId.getValue() );

        // contacts
        contact.setContactPhone( phone.getValue() );
        contact.setContactEmail( email.getValue() );
        contact.setCcEmail( ccEmail.getValue() );

        // invoice address
        contact.setStreet( street.getValue() );
        contact.setCity( city.getValue() );
        contact.setPostcode( postCode.getCleanValue() );
        contact.setCountry( country.getSingleValueByCode() );

        CustomerPostalAddress postalAddress = contact.getPostalAddress();
        if ( hasPostalAddress() )
        {
            if ( contact.getPostalAddress() == null )
            {
                postalAddress = new CustomerPostalAddress();
                contact.setPostalAddress( postalAddress );
            }

            postalAddress.setBusinessName( postalBusinessName.getValue() );
            postalAddress.setPrefix( postalPrefix.getValue() );
            postalAddress.setFirstName( postalFirstName.getValue() );
            postalAddress.setLastName( postalLastName.getValue() );
            postalAddress.setSuffix( postalSuffix.getValue() );
            postalAddress.setStreet( postalStreet.getValue() );
            postalAddress.setCity( postalCity.getValue() );
            postalAddress.setPostcode( postalPostCode.getCleanValue() );
            postalAddress.setCountry( postalCountry.getSingleValueByCode() );
        }
    }

    @Override
    public void fill( T model )
    {
        if ( model.getCustomer() == null )
        {
            model.setCustomer( new Customer() );
        }

        fill( model.getCustomer() );
    }

    private void fill( Customer customer )
    {
        // person
        prefix.setValue( customer.getPrefix() );
        firstName.setValue( customer.getFirstName() );
        middleName.setValue( customer.getMiddleName() );
        lastName.setValue( customer.getLastName() );
        suffix.setValue( customer.getSuffix() );

        // company
        businessName.getItemBox().setValue( customer.getBusinessName() );
        businessName.getLabel().addStyleName( CssName.ACTIVE ); // fix visualization bug
        companyId.setValue( customer.getCompanyId() );
        vatId.setValue( customer.getVatId() );
        taxId.setValue( customer.getTaxId() );

        // contacts
        phone.setValue( customer.getContactPhone() );
        email.setValue( customer.getContactEmail() );
        ccEmail.setValue( customer.getCcEmail() );

        // invoice address
        street.setValue( customer.getStreet() );
        city.setValue( customer.getCity() );
        postCode.setValue( customer.getPostcode() );
        postCode.reload();
        country.setSingleValueByCode( customer.getCountry() );

        // postall address
        CustomerPostalAddress postalAddress = customer.getPostalAddress();
        postalBusinessName.setValue( postalAddress != null ? postalAddress.getBusinessName() : null );
        postalPrefix.setValue( postalAddress != null ? postalAddress.getPrefix() : null );
        postalFirstName.setValue( postalAddress != null ? postalAddress.getFirstName() : null );
        postalLastName.setValue( postalAddress != null ? postalAddress.getLastName() : null );
        postalSuffix.setValue( postalAddress != null ? postalAddress.getSuffix() : null );
        postalStreet.setValue( postalAddress != null ? postalAddress.getStreet() : null );
        postalCity.setValue( postalAddress != null ? postalAddress.getCity() : null );
        postalPostCode.setValue( postalAddress != null ? postalAddress.getPostcode() : null );
        postalPostCode.reload();
        postalCountry.setSingleValueByCode( postalAddress != null ? postalAddress.getCountry() : null );
    }

    private boolean hasPostalAddress()
    {
        return postalBusinessName.getValue() != null ||
                postalPrefix.getValue() != null ||
                postalFirstName != null ||
                postalLastName != null ||
                postalSuffix != null ||
                postalStreet != null ||
                postalCity != null ||
                postalPostCode != null ||
                postalCountry != null;
    }

    // -- private helpers

    private void fillFrom( SearchContact searchContact )
    {
        ( ( AppEventBus ) eventBus ).account()
                .findById( Configuration.get().getLoginId(), Long.valueOf( searchContact.getId() ), this::updateView );
    }

    private void updateView( ContactCard contact, FacadeCallback.Failure failure )
    {
        if ( failure.isSuccess() )
        {
            fill( new Customer( contact ) );
        }
        else
        {
            fill( new Customer() );
            if ( failure.isNotFound() )
            {
                MaterialToast.fireToast( AppMessages.INSTANCE.msgErrorRecordDoesNotExists(), "red" );
            }
            else
            {
                MaterialToast.fireToast( AppMessages.INSTANCE.msgErrorRemoteServiceCall(), "red" );
            }
        }
    }

    private ContactAutoComplete createAutocomplete()
    {
        ContactAutoComplete contactAutoComplete = new ContactAutoComplete( eventBus );
        contactAutoComplete.addSelectionHandler( event -> {
            SearchContact contact = ( ( ContactAutoComplete.ContactSuggest ) event.getSelectedItem() ).getContact();
            fillFrom( contact );
        } );

        return contactAutoComplete;
    }

    interface CustomerPanelUiBinder
            extends UiBinder<HTMLPanel, CustomerPanel>
    {
    }
}