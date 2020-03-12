package biz.turnonline.ecosystem.widget.bill.ui;

import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
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
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.incubator.client.google.addresslookup.AddressLookup;
import gwt.material.design.incubator.client.google.addresslookup.js.options.PlaceResult;

import javax.annotation.Nonnull;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class BillSupplier
        extends Composite
{
    private static SupplierUiBinder binder = GWT.create( SupplierUiBinder.class );

    // company

    @UiField
    MaterialTextBox businessName;

    @UiField
    MaterialTextBox companyId;

    @UiField
    MaterialTextBox taxId;

    @UiField
    MaterialTextBox vatId;

    // person

    @UiField
    MaterialTextBox prefix;

    @UiField
    MaterialTextBox firstName;

    @UiField
    MaterialTextBox lastName;

    @UiField
    MaterialTextBox suffix;

    // address

    @UiField
    AddressLookup street;

    @UiField
    MaterialTextBox city;

    @UiField
    MaterialInputMask<String> postCode;

    @UiField
    CountryComboBox country;

    public BillSupplier( AddressLookupListener addressLookup )
    {
        initWidget( binder.createAndBindUi( this ) );

        // Loading google map API
        addressLookup.onLoad( () -> {
            street.load();
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
    }

    public void bind( @Nonnull Bill bill )
    {
        Supplier supplier = bill.getSupplier();

        supplier.setBusinessName( businessName.getValue() );
        supplier.setCompanyId( companyId.getValue() );
        supplier.setTaxId( taxId.getValue() );
        supplier.setVatId( vatId.getValue() );

        supplier.setPrefix( prefix.getValue() );
        supplier.setFirstName( firstName.getValue() );
        supplier.setLastName( lastName.getValue() );
        supplier.setSuffix( suffix.getValue() );

        supplier.setStreet( street.getValue() );
        supplier.setCity( city.getValue() );
        supplier.setPostcode( postCode.getValue() );
        supplier.setCountry( country.getSingleValueByCode() );
    }

    public void fill( @Nonnull Bill bill )
    {
        Supplier supplier = bill.getSupplier();
        if ( supplier == null )
        {
            supplier = new Supplier();
            bill.setSupplier( supplier );
        }

        businessName.setValue( supplier.getBusinessName() );
        companyId.setValue( supplier.getCompanyId() );
        taxId.setValue( supplier.getTaxId() );
        vatId.setValue( supplier.getVatId() );

        prefix.setValue( supplier.getPrefix() );
        firstName.setValue( supplier.getFirstName() );
        lastName.setValue( supplier.getLastName() );
        suffix.setValue( supplier.getSuffix() );

        street.setValue( supplier.getStreet() );
        city.setValue( supplier.getCity() );
        postCode.setValue( supplier.getPostcode() );
        country.setSingleValueByCode( supplier.getCountry() );
    }

    interface SupplierUiBinder
            extends UiBinder<HTMLPanel, BillSupplier>
    {
    }
}
