package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Event;
import biz.turnonline.ecosystem.widget.shared.rest.billing.EventBegin;
import biz.turnonline.ecosystem.widget.shared.rest.billing.EventEnd;
import biz.turnonline.ecosystem.widget.shared.rest.billing.EventLocation;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.ui.CountryComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import biz.turnonline.ecosystem.widget.shared.ui.InputSearchIcon;
import biz.turnonline.ecosystem.widget.shared.util.Time;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.addins.client.inputmask.MaterialInputMask;
import gwt.material.design.addins.client.timepicker.MaterialTimePicker;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.incubator.client.google.addresslookup.AddressLookup;
import gwt.material.design.incubator.client.google.addresslookup.js.options.PlaceResult;

import javax.inject.Inject;

import static biz.turnonline.ecosystem.widget.shared.util.Maps.findAddressComponent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EventPanel
        extends Composite
        implements HasModel<Product>
{
    private static EventUiBinder binder = GWT.create( EventUiBinder.class );

    // -- description

    @UiField
    MaterialDatePicker deadline;

    @UiField
    MaterialIntegerBox seats;

    // -- begin

    @UiField
    MaterialDatePicker beginOn;

    @UiField
    MaterialTimePicker beginFrom;

    @UiField
    MaterialTimePicker beginTo;

    @UiField
    MaterialSwitch beginShow;

    // -- end

    @UiField
    MaterialDatePicker endOn;

    @UiField
    MaterialTimePicker endFrom;

    @UiField
    MaterialTimePicker endTo;

    @UiField
    MaterialSwitch endShow;

    // -- location

    @UiField
    MaterialTextBox locationName;

    @UiField
    MaterialTextBox locationPhone;

    @UiField
    MaterialTextBox locationEmail;

    @UiField
    AddressLookup locationStreet;

    @UiField
    MaterialTextBox locationCity;

    @UiField
    MaterialInputMask locationPostCode;

    @UiField
    CountryComboBox locationCountry;

    @Inject
    public EventPanel()
    {
        initWidget( binder.createAndBindUi( this ) );
    }

    public void init( AddressLookupListener addressLookup )
    {
        // Loading google map API
        addressLookup.onLoad( () -> locationStreet.load() );

        locationStreet.addPlaceChangedHandler( event -> {
            PlaceResult place = locationStreet.getPlace();

            locationStreet.setValue( findAddressComponent( place, "route" ) + " " + findAddressComponent( place, "street_number" ) );
            locationCity.setValue( findAddressComponent( place, "locality", "sublocality" ) );
            locationPostCode.setValue( findAddressComponent( place, "postal_code" ) );
            locationPostCode.reload();
            locationCountry.setSingleValueByCode( findAddressComponent( place, "country" ) );
        } );
        locationStreet.add( new InputSearchIcon() );
    }

    @Override
    public void bind( Product product )
    {
        Event event = product.getEvent();

        event.setDeadline( deadline.getValue() );
        event.setSeats( seats.getValue() );

        EventBegin begin = event.getBegin();
        begin.setOn( beginOn.getValue() );
        begin.setFrom( Time.dateToInteger( beginFrom.getValue() ) );
        begin.setTo( Time.dateToInteger( beginTo.getValue() ) );
        begin.setShow( beginShow.getValue() );

        EventEnd end = event.getEnd();
        end.setOn( endOn.getValue() );
        end.setFrom( Time.dateToInteger( endFrom.getValue() ) );
        end.setTo( Time.dateToInteger( endTo.getValue() ) );
        end.setShow( endShow.getValue() );

        EventLocation location = event.getLocation();
        location.setName( locationName.getValue() );
        location.setInfoPhone( locationPhone.getValue() );
        location.setInfoEmail( locationEmail.getValue() );
        location.setStreet( locationStreet.getValue() );
        location.setPostcode( locationPostCode.getValue() );
        location.setCity( locationCity.getValue() );
        location.setCountry( locationCountry.getSingleValueByCode() );
    }

    @Override
    public void fill( Product product )
    {
        Event event = getEvent( product );

        deadline.setValue( event.getDeadline() );
        seats.setValue( event.getSeats() );

        EventBegin begin = event.getBegin();
        beginOn.setValue( begin.getOn() );
        beginFrom.setValue( Time.integerToDate( begin.getFrom() ) );
        beginTo.setValue( Time.integerToDate( begin.getTo() ) );
        beginShow.setValue( begin.getShow() );

        EventEnd end = event.getEnd();
        endOn.setValue( end.getOn() );
        endFrom.setValue( Time.integerToDate( end.getFrom() ) );
        endTo.setValue( Time.integerToDate( end.getTo() ) );
        endShow.setValue( end.getShow() );

        EventLocation location = event.getLocation();
        locationName.setValue( location.getName() );
        locationPhone.setValue( location.getInfoPhone() );
        locationEmail.setValue( location.getInfoEmail() );
        locationStreet.setValue( location.getStreet() );
        locationPostCode.setValue( location.getPostcode() );
        locationCity.setValue( location.getCity() );
        locationCountry.setSingleValueByCode( location.getCountry() );
    }

    private Event getEvent( Product product )
    {
        Event event = product.getEvent();
        if ( event == null )
        {
            event = new Event();
            product.setEvent( event );
        }

        if ( event.getBegin() == null )
        {
            EventBegin begin = new EventBegin();
            begin.setShow( true );
            event.setBegin( begin );
        }

        if ( event.getEnd() == null )
        {
            EventEnd end = new EventEnd();
            end.setShow( true );
            event.setEnd( end );
        }

        if ( event.getLocation() == null )
        {
            event.setLocation( new EventLocation() );
        }

        return event;
    }

    interface EventUiBinder
            extends UiBinder<HTMLPanel, EventPanel>
    {
    }
}
