package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.product.event.ProductIdChangeEvent;
import biz.turnonline.ecosystem.widget.product.event.RemovePictureEvent;
import biz.turnonline.ecosystem.widget.shared.presenter.UploaderTokenCallback;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPicture;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPublishing;
import biz.turnonline.ecosystem.widget.shared.util.Uploader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialRow;
import org.ctoolkit.gwt.client.facade.FirebaseAuthFacade;
import org.ctoolkit.gwt.client.facade.UploadItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static biz.turnonline.ecosystem.widget.shared.Configuration.PRODUCT_BILLING_API_ROOT;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ProductPictureUploader
        extends Composite
{
    private static ImageUploaderUiBinder binder = GWT.create( ImageUploaderUiBinder.class );

    @UiField
    MaterialRow images;

    private Long productId;

    @UiField( provided = true )
    MaterialFileUploader uploader = new MaterialFileUploader()
    {
        @Override
        public void load()
        {
            // setUrl and than load widget, otherwise firebase will be executed after widget initialization
            new FirebaseAuthFacade().getIdToken( ( UploaderTokenCallback ) url -> {
                setUrl( url + ( productId == null ? "" : "&productId=" + productId ) );
                        super.load();
                    }, PRODUCT_BILLING_API_ROOT
            );
        }
    };

    private Map<MaterialColumn, ProductPicture> imagesMap = new HashMap<>();

    private EventBus eventBus;

    public ProductPictureUploader( EventBus eventBus )
    {
        initWidget( binder.createAndBindUi( this ) );

        this.eventBus = eventBus;

        uploader.addSuccessHandler( event -> {
            UploadItem uploadItem = Uploader.handleAndGetUploadItem( event );
            if ( uploadItem != null )
            {
                addImage( uploadItem );
            }
        } );

        eventBus.addHandler( ProductIdChangeEvent.TYPE, event -> this.productId = event.getProductId() );
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

    interface ImageUploaderUiBinder
            extends UiBinder<HTMLPanel, ProductPictureUploader>
    {
    }
}
