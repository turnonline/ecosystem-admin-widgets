package org.ctoolkit.turnonline.widget.contact.event;

import com.google.gwt.event.shared.GwtEvent;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.ContactCard;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SaveContactEvent
        extends GwtEvent<SaveContactEventHandler>
{
    public static Type<SaveContactEventHandler> TYPE = new Type<SaveContactEventHandler>();

    private final ContactCard contactCard;

    public SaveContactEvent( ContactCard contactCard )
    {
        this.contactCard = contactCard;
    }

    public Type<SaveContactEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( SaveContactEventHandler handler )
    {
        handler.onSaveContact( this );
    }

    public ContactCard getContactCard()
    {
        return contactCard;
    }
}
