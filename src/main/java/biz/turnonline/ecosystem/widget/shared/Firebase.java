package biz.turnonline.ecosystem.widget.shared;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class Firebase
{
    public static native boolean isUserLoggedIn() /*-{
        return $wnd.firebase.auth().currentUser != null;
    }-*/;
}
