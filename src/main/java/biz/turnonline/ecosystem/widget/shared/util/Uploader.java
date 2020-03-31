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
}
