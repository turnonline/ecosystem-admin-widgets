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

        addAppendHeadersCallback( this::append );
        setShadow( 0 );
        setMarginBottom( 0 );
        setBorder( "1px dashed #ccc" );
        getElement().getStyle().setCursor( Style.Cursor.POINTER );
        getElement().getStyle().setHeight( 100, Style.Unit.PCT );

        setAcceptedFiles( "image/*,application/pdf" );

        setPadding( 10 );

        FlowPanel previewWrapper = new FlowPanel();
        previewWrapper.getElement().getStyle().setTextAlign( Style.TextAlign.CENTER );
        previewWrapper.add( preview );
        add( previewWrapper );

        noImagePreviewSize();

        addSuccessCallback( event -> {
            setPreview( event.getUploadItem() );
            billId = event.getAssociatedId();
        } );

        AppEventBus.get().addHandler( UploaderAssociatedIdChangeEvent.TYPE, event -> {
            this.billId = event.getId();
            addAppendHeadersCallback( this::append );
        } );
    }

    public Long getBillId()
    {
        return billId;
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
        preview.setUrl( value != null && value.getServingUrl() != null ?
                servingUrlOriginalSize( value.getServingUrl() ) :
                Resources.INSTANCE.noImage().getSafeUri().asString()
        );

        if ( value != null && value.getServingUrl() != null )
        {
            originalPreviewSize();
        }
        else
        {
            noImagePreviewSize();
        }
    }

    private void setPreview( UploadItem uploadItem )
    {
        if ( model == null )
        {
            model = new Image();
        }

        model.setServingUrl( uploadItem.getServingUrl() );
        model.setStorageName( uploadItem.getStorageName() );

        preview.setUrl( servingUrlOriginalSize( uploadItem.getServingUrl() ) );

        originalPreviewSize();
    }

    private void originalPreviewSize()
    {
        preview.getElement().getStyle().setWidth( 100, Style.Unit.PCT );
    }

    private void noImagePreviewSize()
    {
        preview.getElement().getStyle().setWidth( 20, Style.Unit.PCT );
    }

    private String servingUrlOriginalSize( String url )
    {
        return url + "=s0";
    }

    private void append( @Nonnull Headers headers )
    {
        if ( billId != null )
        {
            headers.setAssociatedId( String.valueOf( billId ) );
        }
        else
        {
            // make sure any previous value will be cleared
            headers.setAssociatedId( null );
        }
    }
}
