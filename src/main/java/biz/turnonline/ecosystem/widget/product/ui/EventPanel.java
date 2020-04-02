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

package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.AddressLookupListener;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Event;
import biz.turnonline.ecosystem.widget.shared.rest.billing.EventBegin;
import biz.turnonline.ecosystem.widget.shared.rest.billing.EventEnd;
import biz.turnonline.ecosystem.widget.shared.rest.billing.EventLocation;
import biz.turnonline.ecosystem.widget.shared.ui.CountryComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.InputSearchIcon;
import biz.turnonline.ecosystem.widget.shared.util.Time;
import com.google.common.base.Strings;
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

import javax.annotation.Nullable;
import javax.inject.Inject;

import static biz.turnonline.ecosystem.widget.shared.util.Maps.findAddressComponent;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class EventPanel
        extends Composite
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

        seats.setReturnBlankAsNull( true );
        locationName.setReturnBlankAsNull( true );
        locationPhone.setReturnBlankAsNull( true );
        locationEmail.setReturnBlankAsNull( true );
        locationStreet.setReturnBlankAsNull( true );
        locationCity.setReturnBlankAsNull( true );
        locationPostCode.setReturnBlankAsNull( true );
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

    public Event bind( @Nullable Event event )
    {
        if ( event == null )
        {
            event = new Event();
        }

        event.setDeadline( deadline.getValue() );
        event.setSeats( seats.getValue() );

        EventBegin begin = new EventBegin();
        begin.setOn( beginOn.getValue() );
        begin.setFrom( Time.dateToInteger( beginFrom.getValue() ) );
        begin.setTo( Time.dateToInteger( beginTo.getValue() ) );
        begin.setShow( beginShow.getValue() );
        event.setBeginIf( begin );

        EventEnd end = new EventEnd();
        end.setOn( endOn.getValue() );
        end.setFrom( Time.dateToInteger( endFrom.getValue() ) );
        end.setTo( Time.dateToInteger( endTo.getValue() ) );
        end.setShow( endShow.getValue() );
        event.setEndIf( end );

        EventLocation location = new EventLocation();
        location.setName( locationName.getValue() );
        location.setInfoPhone( locationPhone.getValue() );
        location.setInfoEmail( locationEmail.getValue() );
        location.setStreet( locationStreet.getValue() );
        // setReturnBlankAsNull is not working for getCleanValue()
        String postcodeValue = locationPostCode.getCleanValue();
        location.setPostcode( Strings.isNullOrEmpty( postcodeValue ) ? null : postcodeValue );
        location.setCity( locationCity.getValue() );
        location.setCountry( locationCountry.getSingleValueByCode() );
        event.setLocationIf( location );

        return event;
    }

    public void fill( @Nullable Event event )
    {
        event = adjustEvent( event );

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

    private Event adjustEvent( Event event )
    {
        if ( event == null )
        {
            event = new Event();
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
