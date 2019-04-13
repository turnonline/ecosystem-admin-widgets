package biz.turnonline.ecosystem.widget.shared.rest;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import com.google.gwt.core.client.GWT;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;
import org.fusesource.restygwt.client.FailedResponseException;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public interface SuccessCallback<T>
        extends MethodCallback<T>
{
    void onSuccess( T response );

    @Override
    default void onFailure( Method method, Throwable exception )
    {
        printError( exception );

        MaterialToast.fireToast( AppMessages.INSTANCE.msgErrorRemoteServiceCall(), "red" );
        MaterialLoader.loading( false );

        redirectToLoginIfUnauthorized( exception );
    }

    @Override
    default void onSuccess( Method method, T response )
    {
        MaterialLoader.loading( false );
        onSuccess( response );
    }

    default void printError( Throwable exception )
    {
        if ( exception instanceof FailedResponseException )
        {
            FailedResponseException fre = ( FailedResponseException ) exception;
            GWT.log( "Exception occur during calling remote service: " + fre.getResponse().getText() );
        }
        else
        {
            GWT.log( "Exception occur during calling remote service", exception );
        }
    }

    default void redirectToLoginIfUnauthorized( Throwable exception )
    {
        if ( exception instanceof FailedResponseException )
        {
            FailedResponseException fre = ( FailedResponseException ) exception;

            if ( fre.getStatusCode() == 401 )
            {
//                Window.Location.replace( "/login" );
            }
        }
    }
}
