/*
 * Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.account.ContactCard;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Supplier;
import biz.turnonline.ecosystem.widget.shared.rest.search.SearchContact;
import biz.turnonline.ecosystem.widget.shared.ui.ContactAutoComplete;
import biz.turnonline.ecosystem.widget.shared.ui.CountryComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.InputSearchIcon;
import biz.turnonline.ecosystem.widget.shared.util.Maps;
import com.google.common.base.Strings;
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

import javax.annotation.Nonnull;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BillSupplier
        extends Composite
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static final SupplierUiBinder binder = GWT.create( SupplierUiBinder.class );

    // company

    @UiField( provided = true )
    ContactAutoComplete businessName;

    @UiField
    MaterialTextBox companyId;

    @UiField
    MaterialTextBox taxId;

    @UiField
    MaterialTextBox vatId;

    // address

    @UiField
    AddressLookup street;

    @UiField
    MaterialTextBox city;

    @UiField
    MaterialInputMask postCode;

    @UiField
    CountryComboBox country;

    private final EventBus eventBus;

    public BillSupplier( EventBus eventBus, AddressLookupListener addressLookup )
    {
        this.eventBus = eventBus;
        businessName = createAutocomplete();

        initWidget( binder.createAndBindUi( this ) );

        // Loading google map API
        addressLookup.onLoad( () -> street.load() );

        companyId.setReturnBlankAsNull( true );
        taxId.setReturnBlankAsNull( true );
        vatId.setReturnBlankAsNull( true );

        street.setReturnBlankAsNull( true );
        city.setReturnBlankAsNull( true );
        postCode.setReturnBlankAsNull( true );

        street.addPlaceChangedHandler( event -> {
            PlaceResult place = street.getPlace();

            street.setValue( Maps.findAddressComponent( place, "route" ) + " " + Maps.findAddressComponent( place, "street_number" ) );
            city.setValue( Maps.findAddressComponent( place, "locality", "sublocality" ) );
            postCode.setValue( Maps.findAddressComponent( place, "postal_code" ) );
            postCode.reload();
            country.setSingleValueByCode( Maps.findAddressComponent( place, "country" ) );
        } );
        street.add( new InputSearchIcon() );
    }

    public void bind( @Nonnull Bill bill )
    {
        Supplier supplier = new Supplier();

        supplier.setBusinessName( Strings.isNullOrEmpty( businessName.getItemBox().getValue() ) ? null : businessName.getItemBox().getValue() );
        supplier.setCompanyId( companyId.getValue() );
        supplier.setTaxId( taxId.getValue() );
        supplier.setVatId( vatId.getValue() );

        supplier.setStreet( street.getValue() );
        supplier.setCity( city.getValue() );
        supplier.setPostcode( postCode.getValue() );
        supplier.setCountry( country.getSingleValueByCode() );

        bill.setSupplierIf( supplier );
    }

    public void fill( @Nonnull Bill bill )
    {
        Supplier supplier = bill.getSupplier();
        if ( supplier == null )
        {
            businessName.setValue( null );
            companyId.setValue( null );
            taxId.setValue( null );
            vatId.setValue( null );

            street.setValue( null );
            city.setValue( null );
            postCode.setValue( null );
            country.setSingleValueByCode( null );
        }
        else
        {
            businessName.getItemBox().setValue( supplier.getBusinessName() );
            companyId.setValue( supplier.getCompanyId() );
            taxId.setValue( supplier.getTaxId() );
            vatId.setValue( supplier.getVatId() );

            street.setValue( supplier.getStreet() );
            city.setValue( supplier.getCity() );
            postCode.setValue( supplier.getPostcode() );
            country.setSingleValueByCode( supplier.getCountry() );
        }

        businessName.getLabelWidget().addStyleName( CssName.ACTIVE ); // fix visualization bug

        // evaluate as a last step
        readOnly( bill.isApproved() != null && bill.isApproved() );
    }

    public void readOnly( boolean approved )
    {
        businessName.setEnabled( !approved );
        companyId.setEnabled( !approved );
        taxId.setEnabled( !approved );
        vatId.setEnabled( !approved );
        street.setEnabled( !approved );
        city.setEnabled( !approved );
        postCode.setEnabled( !approved );
        country.setEnabled( !approved );
    }

    interface SupplierUiBinder
            extends UiBinder<HTMLPanel, BillSupplier>
    {
    }

    private ContactAutoComplete createAutocomplete()
    {
        ContactAutoComplete contactAutoComplete = new ContactAutoComplete( eventBus );
        contactAutoComplete.setTooltip( messages.tooltipSupplierAutocomplete() );
        contactAutoComplete.addSelectionHandler( event -> {
            SearchContact contact = ( ( ContactAutoComplete.ContactSuggest ) event.getSelectedItem() ).getContact();
            fillFrom( contact );
        } );

        return contactAutoComplete;
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
            businessName.getItemBox().setValue( contact.getBusinessName() );
            companyId.setValue( contact.getCompanyId() );
            taxId.setValue( contact.getTaxId() );
            vatId.setValue( contact.getVatId() );
            street.setValue( contact.getStreet() );
            city.setValue( contact.getCity() );
            postCode.setValue( contact.getPostcode() );
            country.setSingleValueByCode( contact.getCountry() );
        }
        else
        {
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
}
