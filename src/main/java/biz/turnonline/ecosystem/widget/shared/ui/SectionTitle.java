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

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialSection;
import gwt.material.design.client.ui.MaterialTitle;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class SectionTitle
        extends Composite
{
    private static SectionTitle.SectionTitleUiBinder binder = GWT.create( SectionTitle.SectionTitleUiBinder.class );

    @UiField
    MaterialIcon icon;

    @UiField
    MaterialTitle title;

    public SectionTitle()
    {
        initWidget( binder.createAndBindUi( this ) );

        title.getParagraph().setPadding( 0 );
    }

    public void setIconType( IconType icon )
    {
        this.icon.setIconType( icon );
    }

    public void setTitle( String title )
    {
        this.title.setDescription( title );
    }

    public MaterialTitle getTitleComponent()
    {
        return title;
    }

    public void setIconMarginLeft( double marginLeft )
    {
        icon.setMarginLeft( marginLeft );
    }

    public void setTextColor( Color color )
    {
        this.icon.setIconColor( color );
        this.title.setTextColor( color );
    }

    public void setPaddingBottom( double value) {
        icon.setPaddingBottom( value );
        title.setPaddingBottom( value );
    }

    interface SectionTitleUiBinder
            extends UiBinder<MaterialSection, SectionTitle>
    {
    }
}
