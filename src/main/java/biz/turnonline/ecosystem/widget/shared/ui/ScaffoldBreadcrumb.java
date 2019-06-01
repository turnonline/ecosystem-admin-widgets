package biz.turnonline.ecosystem.widget.shared.ui;

import com.google.gwt.dom.client.Style;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.client.base.viewport.Resolution;
import gwt.material.design.client.base.viewport.ViewPort;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialBreadcrumb;
import gwt.material.design.client.ui.MaterialNavBar;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ScaffoldBreadcrumb
        extends Composite
{
    private MaterialNavBar navBar;

    public ScaffoldBreadcrumb( List<BreadcrumbItem> items, PlaceController placeController )
    {
        navBar = new MaterialNavBar();
        navBar.setBackgroundColor( Color.GREY_LIGHTEN_5 );
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

        initWidget( navBar );
    }

    @Override
    protected void onLoad()
    {
        super.onLoad();

        navBar.getNavMenu().setDisplay( Display.NONE );
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
