package biz.turnonline.ecosystem.widget.shared.rest;

import org.ctoolkit.gwt.client.facade.Items;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CodeBookCallback<T extends Items<T>>
        implements SuccessCallback<T>
{
    private final SuccessCallback<T> delegate;

    public CodeBookCallback( SuccessCallback<T> delegate )
    {
        this.delegate = delegate;
    }

    @Override
    public void onSuccess( T response )
    {
        delegate.onSuccess( response );
    }
}
