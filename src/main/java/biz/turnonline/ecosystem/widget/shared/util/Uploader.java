package biz.turnonline.ecosystem.widget.shared.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import gwt.material.design.addins.client.fileuploader.base.UploadFile;
import gwt.material.design.addins.client.fileuploader.base.UploadResponse;
import gwt.material.design.addins.client.fileuploader.events.SuccessEvent;
import org.ctoolkit.gwt.client.facade.UploadItem;
import org.ctoolkit.gwt.client.facade.UploadItemsResponse;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Uploader
{
    public static UploadItem handleAndGetUploadItem( SuccessEvent<UploadFile> event )
    {
        UploadResponse response = event.getResponse();
        if ( response.getCode() == 401 )
        {
            GWT.log( "Unauthorized" );
            return null;
        }

        if ( response.getCode() != 201 )
        {
            GWT.log( "Response code: " + response.getCode() );
            return null;
        }

        UploadItemsResponse json = JsonUtils.safeEval( response.getBody() );
        if ( json.getItems().length() > 0 )
        {
            return json.getItems().get( 0 );
        }

        return null;
    }

    public static String constructUploadUrl( String pathPrefix, String token )
    {
        return pathPrefix + "storage-upload" + "?access_token=" + token;
    }
}
