package biz.turnonline.ecosystem.widget.shared.rest;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.event.RestCallEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import org.ctoolkit.gwt.client.facade.FirebaseAuthAwareRequestSender;
import org.fusesource.restygwt.client.Dispatcher;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.callback.DefaultFilterawareRequestCallback;

import javax.annotation.Nonnull;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * The authentication dispatcher that populates every REST request with credential.
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class FirebaseAuthDispatcher
        implements Dispatcher
{
    public static final FirebaseAuthDispatcher INSTANCE = new FirebaseAuthDispatcher();

    private final FirebaseAuthAwareRequestSender firebaseAware = new FirebaseAuthAwareRequestSender();

    private AppEventBus eventBus;

    public void initEventBus( @Nonnull AppEventBus eventBus )
    {
        this.eventBus = checkNotNull( eventBus, "AppEventBus is null" );
    }

    @Override
    public Request send( Method method, RequestBuilder builder )
    {
        if ( eventBus == null )
        {
            throw new IllegalArgumentException( "EventBus is null, did you forget to call initEventBus(AppEventBus) ?" );
        }

        eventBus.fireEvent( new RestCallEvent( RestCallEvent.Direction.OUT ) );

        DefaultFilterawareRequestCallback filtered = new DefaultFilterawareRequestCallback( method );
        filtered.addFilter( ( m, response, callback ) -> {
            eventBus.fireEvent( new RestCallEvent( RestCallEvent.Direction.IN ) );
            return callback;
        } );

        builder.setCallback( filtered );
        firebaseAware.send( builder );
        return null;
    }
}
