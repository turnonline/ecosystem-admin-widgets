package biz.turnonline.ecosystem.widget.shared.rest;


/**
 * Firebase wrapper
 *
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Firebase
{
    public static native void getIdToken( GetIdTokenCallback callback ) /*-{
        var firebase = $wnd.firebase;

        // firebase is initialized
        if ( firebase )
        {
            var user = firebase.auth().currentUser;
            if ( user )
            {
                user.getIdToken().then( function ( currentToken ) {
                    if ( currentToken )
                    {
                        callback.@biz.turnonline.ecosystem.widget.shared.rest.Firebase.GetIdTokenCallback::then(Ljava/lang/String;)(currentToken);
                    }
                } );
            }
        }
    }-*/;

    @FunctionalInterface
    public interface GetIdTokenCallback
    {
        void then( String token );
    }
}
