/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.Resources;
import biz.turnonline.ecosystem.widget.shared.presenter.UploaderTokenCallback;
import biz.turnonline.ecosystem.widget.shared.rest.account.Image;
import biz.turnonline.ecosystem.widget.shared.util.Uploader;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.FlowPanel;
import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.client.ui.MaterialImage;
import org.ctoolkit.gwt.client.facade.FirebaseAuthFacade;
import org.ctoolkit.gwt.client.facade.UploadItem;

import static biz.turnonline.ecosystem.widget.shared.Configuration.ACCOUNT_STEWARD_API_ROOT;
import static biz.turnonline.ecosystem.widget.shared.Configuration.ACCOUNT_STEWARD_STORAGE;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class LogoUploader
        extends MaterialFileUploader
        implements TakesValue<Image>
{
    private MaterialImage preview = new MaterialImage( Resources.INSTANCE.noImage() );

    private Image model;

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

        addAttachHandler( event -> {
            if ( event.isAttached() )
            {
                new FirebaseAuthFacade().getIdToken( ( UploaderTokenCallback ) this::setUrl, ACCOUNT_STEWARD_API_ROOT );
            }
        } );

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
    public void load()
    {
        // setUrl and than load widget, otherwise firebase will be executed after widget initialization
        new FirebaseAuthFacade().getIdToken( ( UploaderTokenCallback ) url -> {
                    setUrl( url );
                    super.load();
                }, ACCOUNT_STEWARD_STORAGE
        );
    }
}
