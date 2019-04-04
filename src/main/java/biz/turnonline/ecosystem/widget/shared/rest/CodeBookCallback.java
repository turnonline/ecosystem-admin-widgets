package biz.turnonline.ecosystem.widget.shared.rest;

import org.ctoolkit.gwt.client.facade.Items;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CodeBookCallback<T extends Items<T>>
        implements MethodCallback<T>
{
    private final MethodCallback<T> delegate;

    public CodeBookCallback( MethodCallback<T> delegate )
    {
        this.delegate = delegate;
    }

    @Override
    public void onSuccess( Method method, T response )
    {
        delegate.onSuccess( method, response );
    }

    @Override
    public void onFailure( Method method, Throwable exception )
    {
        delegate.onFailure( method, exception );
    }
}
