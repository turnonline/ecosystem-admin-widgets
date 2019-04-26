package biz.turnonline.ecosystem.widget.shared.rest;

import biz.turnonline.ecosystem.widget.shared.event.RestCallEvent;
import biz.turnonline.ecosystem.widget.shared.event.RestCallEvent.Direction;
import biz.turnonline.ecosystem.widget.shared.util.StaticEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import org.ctoolkit.gwt.client.facade.FirebaseAuthRequestBuilder;
import org.fusesource.restygwt.client.Dispatcher;
import org.fusesource.restygwt.client.Method;

/**
 * The authentication dispatcher that populates every REST request with credential.
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class FirebaseAuthDispatcher
        extends FirebaseAuthRequestBuilder
        implements Dispatcher
{
    public static final FirebaseAuthDispatcher INSTANCE = new FirebaseAuthDispatcher();

    @Override
    public Request send( Method method, RequestBuilder builder ) throws RequestException
    {
        StaticEventBus.INSTANCE.fireEvent( new RestCallEvent( Direction.OUT ) );
        return super.sendRequest( builder );
    }
}
