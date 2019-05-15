package biz.turnonline.ecosystem.widget.shared.rest;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.event.RestCallEvent;
import biz.turnonline.ecosystem.widget.shared.event.RestCallEvent.Direction;
import biz.turnonline.ecosystem.widget.shared.util.StaticEventBus;
import com.google.gwt.core.client.GWT;
import gwt.material.design.client.ui.MaterialToast;
import org.fusesource.restygwt.client.FailedResponseException;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface FacadeCallback<T>
        extends MethodCallback<T>
{
    void onSuccess( T response );

    @Override
    default void onFailure( Method method, Throwable exception )
    {
        handleError( exception );
        StaticEventBus.INSTANCE.fireEvent( new RestCallEvent( Direction.IN ) );

        redirectToLoginIfUnauthorized( exception );
    }

    @Override
    default void onSuccess( Method method, T response )
    {
        StaticEventBus.INSTANCE.fireEvent( new RestCallEvent( Direction.IN ) );
        onSuccess( response );
    }

    default void handleError( Throwable exception )
    {
        String errorMessage = AppMessages.INSTANCE.msgErrorRemoteServiceCall();

        if ( exception instanceof FailedResponseException )
        {
            FailedResponseException fre = ( FailedResponseException ) exception;
            if ( fre.getStatusCode() == 404)
            {
                errorMessage = AppMessages.INSTANCE.msgErrorRecordDoesNotExists();
            }

            GWT.log( "Exception occur during calling remote service: " + fre.getResponse().getText() );
        }
        else
        {
            GWT.log( "Exception occur during calling remote service", exception );
        }

        MaterialToast.fireToast( errorMessage, "red" );
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
