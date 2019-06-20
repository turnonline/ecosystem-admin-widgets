package biz.turnonline.ecosystem.widget.shared.rest;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import org.fusesource.restygwt.client.FailedResponseException;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * {@link MethodCallback} turned in to {@link FacadeCallback} that supports lambda syntax.
 * <p>
 * Use this interface if you need a notification about a failure, see {@link #done(T, Failure)}.
 * Calls failing with unauthenticated exception (401 status code) will redirect to {@code /login}
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
@FunctionalInterface
public interface FacadeCallback<T>
        extends MethodCallback<T>
{
    /**
     * Called when asynchronous call completes either successfully or with an error.
     */
    void done( T response, Failure failure );

    @Override
    default void onFailure( Method method, Throwable exception )
    {
        redirectToLoginIfUnauthorized( exception );

        done( null, new Failure()
        {
            @Override
            public boolean isSuccess()
            {
                return !isFailure();
            }

            @Override
            public boolean isFailure()
            {
                return true;
            }

            @Override
            public Throwable exception()
            {
                return exception;
            }

            @Override
            public Response response()
            {
                return method.getResponse();
            }

            @Override
            public int statusCode()
            {
                return method.getResponse().getStatusCode();
            }

            @Override
            public boolean isNotFound()
            {
                return statusCode() == 404;
            }

            @Override
            public boolean isBadRequest()
            {
                return statusCode() == 400;
            }
        } );
    }

    @Override
    default void onSuccess( Method method, T response )
    {
        done( response, new Failure()
        {
            @Override
            public boolean isSuccess()
            {
                return !isFailure();
            }

            @Override
            public boolean isFailure()
            {
                return false;
            }

            @Override
            public Throwable exception()
            {
                return null;
            }

            @Override
            public Response response()
            {
                return method.getResponse();
            }

            @Override
            public int statusCode()
            {
                return method.getResponse().getStatusCode();
            }

            @Override
            public boolean isNotFound()
            {
                return false;
            }

            @Override
            public boolean isBadRequest()
            {
                return false;
            }
        } );
    }

    default void redirectToLoginIfUnauthorized( Throwable exception )
    {
        if ( exception instanceof FailedResponseException )
        {
            FailedResponseException fre = ( FailedResponseException ) exception;

            if ( fre.getStatusCode() == 401 )
            {
                Window.Location.replace( "/login" );
            }
        }
    }

    /**
     * Failure metadata.
     */
    interface Failure
    {
        /**
         * The boolean indication whether remote call has failed.
         *
         * @return true if call has failed
         */
        boolean isFailure();

        /**
         * The boolean indication whether remote call has completed successfully.
         *
         * @return true if call has been successful
         */
        boolean isSuccess();

        /**
         * The failure exception
         */
        Throwable exception();

        /**
         * The call response.
         */
        Response response();

        /**
         * Remote call HTTP status code.
         */
        int statusCode();

        /**
         * {@code true} if HTTP status code of the response is 404
         */
        boolean isNotFound();

        /**
         * {@code true} if HTTP status code of the response is 400
         */
        boolean isBadRequest();
    }
}
