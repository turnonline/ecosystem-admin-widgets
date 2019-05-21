package biz.turnonline.ecosystem.widget.contact.ui;

import biz.turnonline.ecosystem.widget.shared.Resources;
import biz.turnonline.ecosystem.widget.shared.rest.account.Logo;
import biz.turnonline.ecosystem.widget.shared.util.Uploader;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.FlowPanel;
import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.client.ui.MaterialImage;
import org.ctoolkit.gwt.client.facade.UploadItem;
import org.fusesource.restygwt.client.ServiceRoots;

import static biz.turnonline.ecosystem.widget.shared.Configuration.ACCOUNT_STEWARD_API_ROOT;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class LogoUploader
        extends MaterialFileUploader
        implements TakesValue<Logo>
{
    private MaterialImage preview = new MaterialImage( Resources.INSTANCE.noImage() );

    private Logo model;

    public LogoUploader()
    {
        setShadow( 0 );
        setMarginTop( 10 );
        setMarginBottom( 0 );
        setHeight( "150px" );
        setBorder( "1px dashed #ccc" );
        getElement().getStyle().setCursor( Style.Cursor.POINTER );

        setPadding( 10 );
        addStyleName( "valign-wrapper" );

        setUrl( ServiceRoots.get( ACCOUNT_STEWARD_API_ROOT ) + "storage-upload" );

        FlowPanel previewWrapper = new FlowPanel();
        previewWrapper.addStyleName( "valign center" );
        previewWrapper.getElement().getStyle().setProperty( "margin", "auto" );
        add( previewWrapper );

        preview.setMaxHeight( "120px" );
        previewWrapper.add( preview );

        addSuccessHandler( event -> {
            UploadItem uploadItem = Uploader.handleAndGetUploadItem( event );
            if ( uploadItem != null )
            {
                setPreview( uploadItem );
            }
        } );
    }

    @Override
    public void setValue( Logo value )
    {
        this.model = value;
        preview.setUrl( value != null && value.getServingUrl() != null ? value.getServingUrl() : Resources.INSTANCE.noImage().getSafeUri().asString() );
    }

    @Override
    public Logo getValue()
    {
        return model;
    }

    private void setPreview( UploadItem uploadItem )
    {
        if ( model == null )
        {
            model = new Logo();
        }

        model.setServingUrl( uploadItem.getServingUrl() );
        model.setStorageName( uploadItem.getStorageName() );

        preview.setUrl( uploadItem.getServingUrl() );
    }
}
