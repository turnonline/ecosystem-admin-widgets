package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.presenter.UploaderTokenCallback;
import biz.turnonline.ecosystem.widget.shared.util.Uploader;
import com.google.gwt.dom.client.Style;
import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.addins.client.fileuploader.MaterialUploadLabel;
import org.ctoolkit.gwt.client.facade.FirebaseAuthFacade;
import org.ctoolkit.gwt.client.facade.UploadItem;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class BatchDropBox
        extends MaterialFileUploader
{
    private AppMessages messages = AppMessages.INSTANCE;

    private String url;

    public BatchDropBox( String url )
    {
        this.url = url;

        setShadow( 0 );
        setMarginTop( 10 );
        setMarginBottom( 10 );
        setBorder( "1px dashed #ccc" );
        getElement().getStyle().setCursor( Style.Cursor.POINTER );
        setWidth( "100%" );
        setHeight( "200px" );

        MaterialUploadLabel label = new MaterialUploadLabel( messages.labelUploadBatch(), messages.labelUploadBatchDescription() );
        label.setTop( 60 );
        label.setMinWidth( "40%" );
        add( label );

        setPadding( 10 );
        addStyleName( "valign-wrapper" );

        addAttachHandler( event -> {
            if ( event.isAttached() )
            {
                new FirebaseAuthFacade().getIdToken( ( UploaderTokenCallback ) this::setUrl, url );
            }
        } );

        addSuccessHandler( event -> {
            UploadItem uploadItem = Uploader.handleAndGetUploadItem( event );
            if ( uploadItem != null )
            {
                onUpload( uploadItem );
            }
        } );
    }

    public void onUpload( UploadItem uploadItem )
    {
        // do nothing
    }

    @Override
    public void load()
    {
        // setUrl and than load widget, otherwise firebase will be executed after widget initialization
        new FirebaseAuthFacade().getIdToken( ( UploaderTokenCallback ) url -> {
                    setUrl( url );
                    super.load();
                }, url
        );
    }
}
