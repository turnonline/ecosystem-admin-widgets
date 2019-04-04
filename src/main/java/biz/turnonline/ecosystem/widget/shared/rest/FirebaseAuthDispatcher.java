package biz.turnonline.ecosystem.widget.shared.rest;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import gwt.material.design.client.ui.MaterialLoader;
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
        MaterialLoader.loading( true );
        return super.sendRequest( builder );
    }
}
