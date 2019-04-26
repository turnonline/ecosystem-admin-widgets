package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.event.RestCallEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialHeader;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.incubator.client.timer.TimerProgress;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ScaffoldHeader
        extends Composite
{
    private static ScaffoldHeaderUiBinder binder = GWT.create( ScaffoldHeaderUiBinder.class );

    interface ScaffoldHeaderUiBinder
            extends UiBinder<MaterialHeader, ScaffoldHeader>
    {
    }

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
        initWidget( binder.createAndBindUi( this ) );

        email.setText( getFirebaseCurrentUserData( "email" ) );
        avatar.setUrl( getFirebaseCurrentUserData( "photoUrl" ) );
        avatar.getElement().setAttribute( "width", "40" );

        btnSettings.setHref( Route.SETTINGS.url() );
        btnLogout.setHref( Route.LOGOUT.url() );

        progress.setVisibility( Style.Visibility.HIDDEN );

        eventBus.addHandler( RestCallEvent.TYPE, event ->
                progress.setVisibility( event.getDirection() == RestCallEvent.Direction.OUT ?
                        Style.Visibility.VISIBLE :
                        Style.Visibility.HIDDEN ) );
    }

    public MaterialNavBrand getNavBrand()
    {
        return title;
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
}
