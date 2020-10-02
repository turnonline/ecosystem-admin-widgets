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

package biz.turnonline.ecosystem.widget.shared.ui;

import com.google.gwt.dom.client.Style;
import gwt.material.design.addins.client.fileuploader.MaterialUploadLabel;
import org.ctoolkit.gwt.client.facade.UploadItem;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.AppMessages.INSTANCE;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class BatchDropBox
        extends UploaderWithAuthorization
{
    public BatchDropBox( @Nonnull String urlKey )
    {
        super( urlKey );

        setShadow( 0 );
        setMarginTop( 10 );
        setMarginBottom( 10 );
        setBorder( "1px dashed #ccc" );
        getElement().getStyle().setCursor( Style.Cursor.POINTER );
        setWidth( "100%" );
        setHeight( "200px" );

        MaterialUploadLabel label = new MaterialUploadLabel( INSTANCE.labelUploadBatch(), INSTANCE.labelUploadBatchDescription() );
        label.setTop( 60 );
        label.setMinWidth( "40%" );
        add( label );

        setPadding( 10 );
        addStyleName( "valign-wrapper" );

        addSuccessCallback( event -> onUpload( event.getUploadItem() ) );
    }

    public void onUpload( UploadItem uploadItem )
    {
        // do nothing
    }
}
