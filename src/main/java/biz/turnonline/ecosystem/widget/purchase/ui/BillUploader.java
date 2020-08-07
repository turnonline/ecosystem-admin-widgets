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

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.Resources;
import biz.turnonline.ecosystem.widget.shared.event.UploaderAssociatedIdChangeEvent;
import biz.turnonline.ecosystem.widget.shared.rest.account.Image;
import biz.turnonline.ecosystem.widget.shared.ui.UploaderWithAuthorization;
import biz.turnonline.ecosystem.widget.shared.util.Uploader;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.FlowPanel;
import gwt.material.design.client.ui.MaterialImage;
import org.ctoolkit.gwt.client.facade.UploadItem;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Configuration.BILLING_PROCESSOR_STORAGE;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BillUploader
        extends UploaderWithAuthorization
        implements TakesValue<Image>
{
    private final MaterialImage preview = new MaterialImage( Resources.INSTANCE.noImage() );

    private Image model;

    private Long billId;

    public BillUploader()
    {
        super( BILLING_PROCESSOR_STORAGE );
        setShadow( 0 );
        setMarginTop( 10 );
        setMarginBottom( 0 );
        setBorder( "1px dashed #ccc" );
        getElement().getStyle().setCursor( Style.Cursor.POINTER );

        setAcceptedFiles( "image/*,application/pdf" );

        setPadding( 10 );
        addStyleName( "valign-wrapper" );

        FlowPanel previewWrapper = new FlowPanel();
        previewWrapper.addStyleName( "valign center" );
        previewWrapper.getElement().getStyle().setProperty( "margin", "auto" );
        add( previewWrapper );

        previewWrapper.getElement().getStyle().setHeight( 100, Style.Unit.PCT );
        previewWrapper.getElement().getStyle().setOverflow( Style.Overflow.SCROLL );
        previewWrapper.add( preview );

        addSuccessHandler( event -> {
            UploadItem uploadItem = Uploader.handleAndGetUploadItem( event );
            if ( uploadItem != null )
            {
                setPreview( uploadItem );
            }
        } );

        AppEventBus.get().addHandler( UploaderAssociatedIdChangeEvent.TYPE, event -> this.billId = event.getId() );
    }

    @Override
    public Image getValue()
    {
        return model;
    }

    @Override
    public void setValue( Image value )
    {
        this.model = value;
        preview.setUrl( value != null && value.getServingUrl() != null ? value.getServingUrl() : Resources.INSTANCE.noImage().getSafeUri().asString() );
    }

    private void setPreview( UploadItem uploadItem )
    {
        if ( model == null )
        {
            model = new Image();
        }

        model.setServingUrl( uploadItem.getServingUrl() );
        model.setStorageName( uploadItem.getStorageName() );

        preview.setUrl( uploadItem.getServingUrl() );
    }

    @Override
    protected void append( @Nonnull Headers headers )
    {
        if ( billId != null )
        {
            headers.setAssociatedId( String.valueOf( billId ) );
        }
    }
}
