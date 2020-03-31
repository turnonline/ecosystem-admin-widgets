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

import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPublishing;
import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.addins.client.richeditor.MaterialRichEditor;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Content
        extends Composite
{
    private static ContentUiBinder binder = GWT.create( ContentUiBinder.class );

    @UiField
    MaterialRichEditor editor;

    @Inject
    public Content()
    {
        initWidget( binder.createAndBindUi( this ) );
    }

    public ProductPublishing bind( @Nullable ProductPublishing publishing )
    {
        if ( publishing == null )
        {
            publishing = new ProductPublishing();
        }

        String html = editor.getHTML();
        publishing.setDescription( Strings.isNullOrEmpty( html ) ? null : html );
        return publishing;
    }

    public void fill( @Nullable ProductPublishing publishing )
    {
        if ( publishing == null )
        {
            editor.clear();
        }
        else
        {
            editor.setHTML( publishing.getDescription() );
        }
    }

    interface ContentUiBinder
            extends UiBinder<HTMLPanel, Content>
    {
    }
}
