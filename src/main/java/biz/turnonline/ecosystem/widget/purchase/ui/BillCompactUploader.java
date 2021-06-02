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

import biz.turnonline.ecosystem.widget.purchase.event.BillCompactUploadedEvent;
import biz.turnonline.ecosystem.widget.purchase.event.BillCompactUploadedEventHandler;
import biz.turnonline.ecosystem.widget.purchase.event.HasBillCompactUploadedEventHandlers;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Bill;
import biz.turnonline.ecosystem.widget.shared.rest.bill.Scan;
import biz.turnonline.ecosystem.widget.shared.ui.UploaderWithAuthorization;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.shared.HandlerRegistration;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import org.ctoolkit.gwt.client.facade.UploadItem;

import javax.annotation.Nonnull;
import java.util.Collections;

import static biz.turnonline.ecosystem.widget.shared.Configuration.BILLING_PROCESSOR_STORAGE;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BillCompactUploader
        extends UploaderWithAuthorization
        implements HasBillCompactUploadedEventHandlers
{
    private final AppMessages messages = AppMessages.INSTANCE;

    private Bill bill;

    public BillCompactUploader()
    {
        super( BILLING_PROCESSOR_STORAGE );

        setShadow( 0 );
        setMarginBottom( 0 );
        getElement().getStyle().setCursor( Style.Cursor.POINTER );

        MaterialIcon icon = new MaterialIcon( IconType.CLOUD_UPLOAD );
        icon.setIconColor( Color.BLUE_DARKEN_3 );
        icon.setTextColor( Color.BLUE_DARKEN_3 );
        icon.setBorderRadius( "100px" );
        icon.setBorder( "1px dashed" );
        icon.setPadding( 5 );
        icon.setTooltip( messages.tooltipUploadBill() );
        add( icon );

        setAcceptedFiles( "image/*,application/pdf" );

        addSuccessCallback( event -> {
            UploadItem uploadItem = event.getUploadItem();

            Scan scan = new Scan();
            scan.setOrder( 1 );
            scan.setServingUrl( uploadItem.getServingUrl() );
            scan.setStorageName( uploadItem.getStorageName() );
            bill.setScans( Collections.singletonList( scan ) );

            fireEvent( new BillCompactUploadedEvent( bill ) );
        } );
    }

    public void setBill( Bill bill )
    {
        this.bill = bill;

        addAppendHeadersCallback( this::append );
    }

    private void append( @Nonnull Headers headers )
    {
        headers.setAssociatedId( String.valueOf( bill.getId() ) );
    }

    @Override
    protected void onLoad()
    {
        super.onLoad();

        getElement().getStyle().setDisplay( bill.isApproved() ? Style.Display.NONE : Style.Display.INLINE );
    }

    @Override
    public HandlerRegistration addUploadedHandler( BillCompactUploadedEventHandler handler )
    {
        return addDomHandler( handler, BillCompactUploadedEvent.TYPE );
    }
}
