package org.ctoolkit.turnonline.widget.shared.rest;

import com.google.gwt.core.client.GWT;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;
import org.ctoolkit.turnonline.widget.shared.AppMessages;
import org.fusesource.restygwt.client.FailedResponseException;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public abstract class FacadeCallback<T>
        implements MethodCallback<T>
{
    private AppMessages messages = GWT.create( AppMessages.class );

    @Override
    public void onFailure( Method method, Throwable exception )
    {
        printError( exception );
        MaterialToast.fireToast( messages.msgErrorRemoteServiceCall(), "red" );
        MaterialLoader.loading( false );
    }

    @Override
    public void onSuccess( Method method, T response )
    {
        MaterialLoader.loading( false );
    }

    private void printError( Throwable exception )
    {
        if ( exception instanceof FailedResponseException )
        {
            FailedResponseException fre = ( FailedResponseException ) exception;
            GWT.log( "Exception occur during calling remote service: " + fre.getResponse().getText() );

            // TODO: redirect to login if fre.getResponse().getStatusCode() == 401
        }
        else
        {
            GWT.log( "Exception occur during calling remote service", exception );
        }
    }
}
