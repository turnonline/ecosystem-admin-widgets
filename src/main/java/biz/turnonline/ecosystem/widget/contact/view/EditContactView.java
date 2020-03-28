/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
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

package biz.turnonline.ecosystem.widget.contact.view;

import biz.turnonline.ecosystem.widget.contact.event.BackEvent;
import biz.turnonline.ecosystem.widget.contact.event.DeleteContactEvent;
import biz.turnonline.ecosystem.widget.contact.event.SaveContactEvent;
import biz.turnonline.ecosystem.widget.contact.presenter.EditContactPresenter;
import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.account.ContactCard;
import biz.turnonline.ecosystem.widget.shared.rest.account.ContactCardPostalAddress;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
import biz.turnonline.ecosystem.widget.shared.ui.CountryComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.InputSearchIcon;
import biz.turnonline.ecosystem.widget.shared.ui.LogoUploader;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.util.Maps;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.addins.client.inputmask.MaterialInputMask;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
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
public class EditContactView
        extends View<ContactCard>
        implements EditContactPresenter.IView
{
    private static EditContactsViewUiBinder binder = GWT.create( EditContactsViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    ConfirmationWindow confirmation;

    @UiField
    MaterialButton btnSave;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialAnchorButton deleteContact;

    @UiField
    MaterialSwitch company;

    // person

    @UiField
    MaterialTextBox prefix;

    @UiField
    MaterialTextBox firstName;

    @UiField
    MaterialTextBox lastName;

    @UiField
    MaterialTextBox suffix;

    // company

    @UiField
    MaterialTextBox businessName;

    @UiField
    MaterialTextBox companyId;

    @UiField
    MaterialTextBox taxId;

    @UiField
    MaterialTextBox vatId;

    @UiField
    MaterialSwitch vatPayer;

    // contacts

    @UiField
    MaterialTextBox phone;

    @UiField
    MaterialTextBox email;

    @UiField
    MaterialTextBox ccEmail;

    // invoicing

    @UiField
    MaterialIntegerBox numberOfDays;

    // logo
    @UiField
    LogoUploader logoUploader;

    // invoice address

    @UiField
    MaterialSwitch postalSame;

    @UiField
    AddressLookup street;

    @UiField
    MaterialTextBox city;

    @UiField
    MaterialInputMask postCode;

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
    MaterialInputMask postalPostCode;

    @UiField
    CountryComboBox postalCountry;

    @Inject
    public EditContactView( @Named( "EditContactBreadcrumb" ) ScaffoldBreadcrumb breadcrumb,
                            AddressLookupListener addressLookup )
    {
        super();

        this.breadcrumb = breadcrumb;
        setActive( Route.CONTACTS );

        add( binder.createAndBindUi( this ) );

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

        confirmation.getBtnOk().addClickHandler( event -> bus().fireEvent( new DeleteContactEvent( getRawModel() ) ) );
    }

    @Override
    protected void beforeGetModel()
    {
        ContactCard contact = getRawModel();

        contact.setCompany( company.getValue() );

        // person
        contact.setPrefix( prefix.getValue() );
        contact.setFirstName( firstName.getValue() );
        contact.setLastName( lastName.getValue() );
        contact.setSuffix( suffix.getValue() );

        // company
        contact.setBusinessName( businessName.getValue() );
        contact.setCompanyId( companyId.getValue() );
        contact.setVatId( vatId.getValue() );
        contact.setTaxId( taxId.getValue() );
        contact.setVatPayer( vatPayer.getValue() );

        // contacts
        contact.setContactPhone( phone.getValue() );
        contact.setContactEmail( email.getValue() );
        contact.setCcEmail( ccEmail.getValue() );

        // invoicing
        contact.setNumberOfDays( numberOfDays.getValue() );

        // logo
        contact.setLogo( logoUploader.getValue() );

        // invoice address
        contact.setStreet( street.getValue() );
        contact.setCity( city.getValue() );
        contact.setPostcode( postCode.getCleanValue() );
        contact.setCountry( country.getSingleValueByCode() );

        // postal address
        contact.setHasPostalAddress( !postalSame.getValue() );

        ContactCardPostalAddress postalAddress = contact.getPostalAddress();
        if ( contact.getHasPostalAddress() )
        {
            if ( contact.getPostalAddress() == null )
            {
                postalAddress = new ContactCardPostalAddress();
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
    protected void afterSetModel()
    {
        ContactCard contact = getRawModel();

        deleteContact.setEnabled( contact.getId() != null );

        company.setValue( contact.getCompany() );

        // person
        prefix.setValue( contact.getPrefix() );
        firstName.setValue( contact.getFirstName() );
        lastName.setValue( contact.getLastName() );
        suffix.setValue( contact.getSuffix() );

        // company
        businessName.setValue( contact.getBusinessName() );
        companyId.setValue( contact.getCompanyId() );
        vatId.setValue( contact.getVatId() );
        taxId.setValue( contact.getTaxId() );
        vatPayer.setValue( contact.getVatPayer() );

        // contacts
        phone.setValue( contact.getContactPhone() );
        email.setValue( contact.getContactEmail() );
        ccEmail.setValue( contact.getCcEmail() );

        // invoicing
        numberOfDays.setValue( contact.getNumberOfDays() );

        // logo
        logoUploader.setValue( contact.getLogo() );

        // invoice address
        street.setValue( contact.getStreet() );
        city.setValue( contact.getCity() );
        postCode.setValue( contact.getPostcode() );
        postCode.reload();
        country.setSingleValueByCode( contact.getCountry() );

        // postal address
        postalSame.setValue( !contact.getHasPostalAddress() );

        ContactCardPostalAddress postalAddress = contact.getPostalAddress();
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

        handleHasPostalAddress();
        handleVatPayer();

        vatPayer.addValueChangeHandler( event -> handleVatPayer() );
        postalSame.addValueChangeHandler( event -> handleHasPostalAddress() );
    }

    @UiHandler( "btnBack" )
    public void handleBack( ClickEvent event )
    {
        bus().fireEvent( new BackEvent() );
    }

    @UiHandler( "btnSave" )
    public void handleSave( ClickEvent event )
    {
        bus().fireEvent( new SaveContactEvent( getModel() ) );
    }

    @UiHandler( "deleteContact" )
    public void deleteContact( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        confirmation.open( AppMessages.INSTANCE.questionDeleteRecord() );
    }

    private void handleHasPostalAddress()
    {
        boolean enabled = !postalSame.getValue();

        postalBusinessName.setEnabled( enabled );
        postalPrefix.setEnabled( enabled );
        postalFirstName.setEnabled( enabled );
        postalLastName.setEnabled( enabled );
        postalSuffix.setEnabled( enabled );
        postalStreet.setEnabled( enabled );
        postalCity.setEnabled( enabled );
        postalPostCode.setEnabled( enabled );
        postalCountry.setEnabled( enabled );
    }

    private void handleVatPayer()
    {
        vatId.setEnabled( vatPayer.getValue() );
    }

    interface EditContactsViewUiBinder
            extends UiBinder<HTMLPanel, EditContactView>
    {
    }
}