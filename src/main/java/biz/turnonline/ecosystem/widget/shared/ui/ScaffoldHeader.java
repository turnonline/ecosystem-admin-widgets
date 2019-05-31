package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.event.RestCallEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.base.viewport.Resolution;
import gwt.material.design.client.base.viewport.ViewPort;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.ui.MaterialHeader;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.incubator.client.timer.TimerProgress;

import static gwt.material.design.client.base.viewport.Resolution.LAPTOP_4K;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ScaffoldHeader
        extends Composite
{
    private static ScaffoldHeaderUiBinder binder = GWT.create( ScaffoldHeaderUiBinder.class );

    @UiField( provided = true )
    FulltextSearch search;

    @UiField
    MaterialNavBrand title;

    @UiField
    MaterialLink email;

    @UiField
    MaterialImage avatar;

    @UiField
    MaterialLink btnSettings;

    @UiField
    MaterialLink btnLogout;

    @UiField
    TimerProgress progress;

    public ScaffoldHeader( EventBus eventBus )
    {
        search = new FulltextSearch( eventBus );
        initWidget( binder.createAndBindUi( this ) );

        email.setText( getFirebaseCurrentUserData( "email" ) );
        email.setHref( Route.MY_ACCOUNT.url() + "#my-account:" );
        avatar.setUrl( getFirebaseCurrentUserData( "photoURL" ) );
        avatar.getElement().setAttribute( "width", "40" );

        btnSettings.setHref( Route.MY_ACCOUNT.url() + "#my-account/settings:" );
        btnLogout.setHref( Route.LOGOUT.url() );

        progress.setVisibility( Style.Visibility.HIDDEN );

        eventBus.addHandler( RestCallEvent.TYPE, event ->
                progress.setVisibility( event.getDirection() == RestCallEvent.Direction.OUT ?
                        Style.Visibility.VISIBLE :
                        Style.Visibility.HIDDEN ) );

        modifyForMobile();
        modifyForTablet();
        modifyForLaptop();
    }

    public MaterialNavBrand getNavBrand()
    {
        return title;
    }

    public void setActive( Route route )
    {
        search.setRoute( route );
    }

    protected void modifyForMobile()
    {
        ViewPort.when( Resolution.ALL_MOBILE ).then( e -> {
            search.setMaxWidth( "80%" );
            search.setMarginRight( 0 );
            search.setMarginTop( 8 );

            title.setDisplay( Display.NONE );
        } );
    }

    protected void modifyForTablet()
    {
        ViewPort.when( Resolution.TABLET ).then( e -> {
            search.setMaxWidth( "80%" );
            search.setMarginTop( 12 );
            title.setDisplay( Display.NONE );

            email.getSpan().setDisplay( Display.NONE );
            btnLogout.getSpan().setDisplay( Display.NONE );
        } );

        ViewPort.when( Resolution.LAPTOP ).then( e -> {
            search.setMaxWidth( "50%" );
            search.setWidth( "50%" );
            search.setMarginRight( 0 );
            search.setMarginTop( 12 );
            search.getElement().getStyle().setLeft( 60, Style.Unit.PX );
            title.setDisplay( Display.NONE );

            email.getSpan().setDisplay( Display.NONE );
            btnLogout.getSpan().setDisplay( Display.NONE );
        } );
    }

    protected void modifyForLaptop()
    {
        ViewPort.when( Resolution.LAPTOP_LARGE, LAPTOP_4K ).then( e -> {
            search.setMaxWidth( "30%" );
            search.getElement().getStyle().setLeft( 30, Style.Unit.PCT );

            email.getSpan().setDisplay( Display.INLINE );
            btnLogout.getSpan().setDisplay( Display.INLINE );
        } );
    }

    protected native String getFirebaseCurrentUserData( String key ) /*-{
        var firebase = $wnd.firebase;

        // firebase is initialized
        if ( firebase )
        {
            var user = firebase.auth().currentUser;
            if ( user )
            {
                return user[key];
            }
        }

        return "";
    }-*/;

    interface ScaffoldHeaderUiBinder
            extends UiBinder<MaterialHeader, ScaffoldHeader>
    {
    }
}
