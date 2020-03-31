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
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.base.viewport.Resolution;
import gwt.material.design.client.base.viewport.ViewPort;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.Position;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialBreadcrumb;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavSection;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ScaffoldBreadcrumb
        extends Composite
{
    private MaterialLink refresh;

    private MaterialLink clearFilter;

    private MaterialNavSection navSection;

    public ScaffoldBreadcrumb( List<BreadcrumbItem> items, PlaceController placeController )
    {
        this( items, placeController, null );
    }

    public ScaffoldBreadcrumb( List<BreadcrumbItem> items, PlaceController placeController, @Nullable Widget child )
    {
        MaterialNavBar navBar = new MaterialNavBar();
        navBar.setBackgroundColor( Color.WHITE );
        navBar.setPaddingLeft( 20 );

        for ( int i = 0; i < items.size(); i++ )
        {
            BreadcrumbItem item = items.get( i );

            MaterialBreadcrumb breadcrumb = new MaterialBreadcrumb();
            breadcrumb.setIconPosition( IconPosition.LEFT );
            breadcrumb.setTextColor( Color.GREY_DARKEN_3 );
            breadcrumb.setText( item.getText() );
            breadcrumb.getSpan().setHideOn( HideOn.HIDE_ON_SMALL_DOWN );
            breadcrumb.getIcon().getElement().getStyle().setMarginRight( 10, Style.Unit.PX );
            ViewPort.when( Resolution.ALL_MOBILE ).then( e -> {
                breadcrumb.getIcon().getElement().getStyle().setMarginRight( 0, Style.Unit.PX );
            } );

            if ( item.getPlace() != null )
            {
                breadcrumb.addClickHandler( event -> placeController.goTo( item.getPlace() ) );
            }

            if ( item.getIcon() != null )
            {
                breadcrumb.setIconType( item.getIcon() );
            }

            if ( items.size() - 1 == i )
            {
                breadcrumb.setTextColor( Color.BLUE );
            }

            navBar.add( breadcrumb );
        }

        navSection = new MaterialNavSection();
        navSection.setFloat( Style.Float.RIGHT );
        navSection.setVisible( false );
        navSection.setHideOn( HideOn.NONE );
        navBar.add( navSection );

        clearFilter = new MaterialLink();
        clearFilter.setIconType( IconType.CANCEL );
        clearFilter.setIconColor( Color.BLACK );
        clearFilter.setWaves( WavesType.LIGHT );
        clearFilter.setPaddingRight( 0 );
        navSection.add( clearFilter );

        refresh = new MaterialLink();
        refresh.setIconType( IconType.REFRESH );
        refresh.setIconColor( Color.BLACK );
        refresh.setWaves( WavesType.LIGHT );
        refresh.setPaddingRight( 0 );
        navSection.add( refresh );

        if ( child != null )
        {
            navSection.add( child );
        }

        initWidget( navBar );
    }

    public void setNavSectionVisible( boolean visible )
    {
        navSection.setVisible( visible );
    }

    public void setRefreshTooltip( String tooltip )
    {
        refresh.setTooltip( tooltip );
        refresh.setTooltipPosition( Position.LEFT );
        refresh.setTooltipDelayMs( 700 );
    }

    public void addRefreshClickHandler( ClickHandler handler )
    {
        refresh.addClickHandler( handler );
    }

    public void setClearFilterEnabled( boolean enabled )
    {
        clearFilter.setEnabled( enabled );
    }

    public void setClearFilterTooltip( String tooltip )
    {
        clearFilter.setTooltip( tooltip );
        clearFilter.setTooltipPosition( Position.LEFT );
        clearFilter.setTooltipDelayMs( 700 );
    }

    public void addClearFilterClickHandler( ClickHandler handler )
    {
        clearFilter.addClickHandler( handler );
    }

    public void setClearFilterVisible( boolean visible )
    {
        clearFilter.setVisible( visible );
    }

    public static class BreadcrumbItem
    {
        private Place place;

        private IconType icon;

        private String text;

        public BreadcrumbItem( String text )
        {
            this.text = text;
        }

        public BreadcrumbItem( Place place, String text )
        {
            this.place = place;
            this.text = text;
        }

        public BreadcrumbItem( IconType icon, String text )
        {
            this.icon = icon;
            this.text = text;
        }

        public BreadcrumbItem( Place place, IconType icon, String text )
        {
            this.place = place;
            this.icon = icon;
            this.text = text;
        }

        public Place getPlace()
        {
            return place;
        }

        public void setPlace( Place place )
        {
            this.place = place;
        }

        public IconType getIcon()
        {
            return icon;
        }

        public void setIcon( IconType icon )
        {
            this.icon = icon;
        }

        public String getText()
        {
            return text;
        }

        public void setText( String text )
        {
            this.text = text;
        }
    }

}
