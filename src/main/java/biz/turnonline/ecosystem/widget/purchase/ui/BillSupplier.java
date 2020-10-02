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
import biz.turnonline.ecosystem.widget.shared.Resources;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Scan;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Supplier;
import biz.turnonline.ecosystem.widget.shared.ui.CountryComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.InputSearchIcon;
import biz.turnonline.ecosystem.widget.shared.util.Maps;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.addins.client.inputmask.MaterialInputMask;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.incubator.client.google.addresslookup.AddressLookup;
import gwt.material.design.incubator.client.google.addresslookup.js.options.PlaceResult;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BillSupplier
        extends Composite
{
    private static final SupplierUiBinder binder = GWT.create( SupplierUiBinder.class );

    @UiField
    MaterialImage billScan = new MaterialImage( Resources.INSTANCE.noImage() );

    // company

    @UiField
    MaterialTextBox businessName;

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

    public BillSupplier( AddressLookupListener addressLookup )
    {
        initWidget( binder.createAndBindUi( this ) );

        // Loading google map API
        addressLookup.onLoad( () -> street.load() );

        businessName.setReturnBlankAsNull( true );
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

        supplier.setBusinessName( businessName.getValue() );
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
        List<Scan> scans = Optional.ofNullable( bill.getScans() ).orElse( new ArrayList<>() );
        Scan scan = scans.isEmpty() ? new Scan() : scans.get( 0 );

        billScan.setUrl( scan.getServingUrl() != null ? scan.getServingUrl() : Resources.INSTANCE.noImage().getSafeUri().asString() );

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
            businessName.setValue( supplier.getBusinessName() );
            companyId.setValue( supplier.getCompanyId() );
            taxId.setValue( supplier.getTaxId() );
            vatId.setValue( supplier.getVatId() );

            street.setValue( supplier.getStreet() );
            city.setValue( supplier.getCity() );
            postCode.setValue( supplier.getPostcode() );
            country.setSingleValueByCode( supplier.getCountry() );
        }

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
}
