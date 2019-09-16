package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.product.event.RemovePictureEvent;
import biz.turnonline.ecosystem.widget.shared.rest.Firebase;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPicture;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPublishing;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
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
import org.ctoolkit.gwt.client.facade.UploadItem;
import org.fusesource.restygwt.client.ServiceRoots;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static biz.turnonline.ecosystem.widget.shared.Configuration.PRODUCT_BILLING_API_ROOT;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ProductPictureUploader
        extends Composite
        implements HasModel<ProductPublishing>
{
    interface ImageUploaderUiBinder
            extends UiBinder<HTMLPanel, ProductPictureUploader>
    {
    }

    private static ImageUploaderUiBinder binder = GWT.create( ImageUploaderUiBinder.class );

    @UiField
    MaterialRow images;

    @UiField
    MaterialFileUploader uploader;

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

        Firebase.getIdToken( token -> uploader.setUrl( Uploader.constructUploadUrl( ServiceRoots.get( PRODUCT_BILLING_API_ROOT ), token ) ) );
    }

    @Override
    public void bind( ProductPublishing model )
    {
        model.getPictures().clear();

        int order = 1;
        for ( Map.Entry<MaterialColumn, ProductPicture> entry : imagesMap.entrySet() )
        {
            ProductPicture picture = entry.getValue();
            picture.setOrder( order );
            model.getPictures().add( picture );

            order++;
        }
    }

    @Override
    public void fill( ProductPublishing model )
    {
        images.clear();
        imagesMap.clear();

        if ( model.getPictures() != null )
        {
            model.getPictures().stream()
                    .sorted( Comparator.comparing( ProductPicture::getOrder ) )
                    .forEach( this::addImage );
        }
        else
        {
            model.setPictures( new ArrayList<>() );
        }
    }

    public MaterialFileUploader getUploader()
    {
        return uploader;
    }

    protected void removeImage( MaterialColumn column )
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
}
