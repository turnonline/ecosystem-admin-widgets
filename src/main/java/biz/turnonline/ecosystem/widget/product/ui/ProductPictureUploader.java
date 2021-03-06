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

import biz.turnonline.ecosystem.widget.product.event.RemovePictureEvent;
import biz.turnonline.ecosystem.widget.shared.event.UploaderAssociatedIdChangeEvent;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPicture;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPublishing;
import biz.turnonline.ecosystem.widget.shared.ui.UploaderWithAuthorization;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialRow;
import org.ctoolkit.gwt.client.facade.UploadItem;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static biz.turnonline.ecosystem.widget.shared.Configuration.PRODUCT_BILLING_STORAGE;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ProductPictureUploader
        extends Composite
{
    private static final ImageUploaderUiBinder binder = GWT.create( ImageUploaderUiBinder.class );

    private final Map<MaterialColumn, ProductPicture> imagesMap = new HashMap<>();

    private final EventBus eventBus;

    @UiField
    MaterialRow images;

    @UiField( provided = true )
    UploaderWithAuthorization uploader = new UploaderWithAuthorization( PRODUCT_BILLING_STORAGE );

    private Long productId;

    public ProductPictureUploader( EventBus eventBus )
    {
        initWidget( binder.createAndBindUi( this ) );

        this.eventBus = eventBus;

        uploader.addAppendHeadersCallback( this::append );
        uploader.addSuccessCallback( event -> {
            addImage( event.getUploadItem() );
            productId = event.getAssociatedId();
        } );

        eventBus.addHandler( UploaderAssociatedIdChangeEvent.TYPE, event -> this.productId = event.getId() );
    }

    /**
     * The product ID that that's associated with product's images.
     *
     * @return the product ID.
     */
    public Long getProductId()
    {
        return productId;
    }

    public void bind( ProductPublishing model )
    {
        if ( model.getPictures() != null )
        {
            model.getPictures().clear();
        }

        int order = 1;
        for ( Map.Entry<MaterialColumn, ProductPicture> entry : imagesMap.entrySet() )
        {
            ProductPicture picture = entry.getValue();
            picture.setOrder( order );
            List<ProductPicture> pictures = model.getPictures();
            if ( pictures == null )
            {
                pictures = new ArrayList<>();
                model.setPictures( pictures );
            }
            pictures.add( picture );

            order++;
        }
    }

    public void fill( ProductPublishing model )
    {
        images.clear();
        imagesMap.clear();

        if ( model.getPictures() != null )
        {
            // However order number will be populated by the backend, at client side still might be a null for a while
            model.getPictures().stream()
                    .sorted( Comparator.comparing( ProductPicture::getOrder, ( o1, o2 ) ->
                            o1 == null || o2 == null ? 0 : o1.compareTo( o2 ) ) )
                    .forEach( this::addImage );
        }
    }

    private void removeImage( MaterialColumn column )
    {
        ProductPicture picture = imagesMap.remove( column );
        column.removeFromParent();

        eventBus.fireEvent( new RemovePictureEvent( picture ) );
    }

    private void addImage( UploadItem uploadItem )
    {
        ProductPicture productPicture = new ProductPicture();
        productPicture.setServingUrl( uploadItem.getServingUrl() );
        productPicture.setStorageName( uploadItem.getStorageName() );
        productPicture.setOrder( imagesMap.keySet().size() + 1 );

        addImage( productPicture );
    }

    private void addImage( ProductPicture productPicture )
    {
        MaterialColumn column = new MaterialColumn();
        column.setGrid( "s12 m3" );
        images.add( column );

        MaterialIcon delete = new MaterialIcon( IconType.DELETE );
        delete.setIconColor( Color.WHITE );
        delete.setBackgroundColor( Color.RED );
        delete.setBorderRadius( "100%" );
        delete.setWidth( "40px" );
        delete.setHeight( "40px" );
        delete.setMargin( 5 );
        delete.setMarginTop( 25 );
        delete.setPaddingTop( 8 );
        delete.setPaddingLeft( 6 );
        delete.setPaddingBottom( 5 );
        delete.setPaddingRight( 5 );
        delete.setShadow( 1 );
        delete.setDisplay( Display.BLOCK );
        delete.setLayoutPosition( Style.Position.ABSOLUTE );
        delete.setWaves( WavesType.DEFAULT );
        delete.addClickHandler( event1 -> removeImage( column ) );
        column.add( delete );

        MaterialImage image = new MaterialImage( productPicture.getServingUrl() );
        image.setMarginTop( 20 );
        image.setBackgroundColor( Color.GREY_LIGHTEN_5 );
        image.setPadding( 20 );
        image.setBorderRadius( "2px" );
        column.add( image );

        imagesMap.put( column, productPicture );
    }

    private void append( @Nonnull UploaderWithAuthorization.Headers headers )
    {
        if ( productId != null )
        {
            headers.setAssociatedId( String.valueOf( productId ) );
        }
        else
        {
            // make sure any previous value will be cleared
            headers.setAssociatedId( null );
        }
    }

    interface ImageUploaderUiBinder
            extends UiBinder<HTMLPanel, ProductPictureUploader>
    {
    }
}
