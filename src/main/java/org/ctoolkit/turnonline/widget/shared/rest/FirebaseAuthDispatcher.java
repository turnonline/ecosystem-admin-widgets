package org.ctoolkit.turnonline.widget.shared.rest;

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
        return super.sendRequest( builder );
    }
}
