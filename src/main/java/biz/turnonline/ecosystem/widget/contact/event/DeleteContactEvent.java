package biz.turnonline.ecosystem.widget.contact.event;

import biz.turnonline.ecosystem.widget.shared.rest.account.ContactCard;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DeleteContactEvent
        extends GwtEvent<DeleteContactEventHandler>
{
    public static Type<DeleteContactEventHandler> TYPE = new Type<DeleteContactEventHandler>();

    private final ContactCard contactCard;

    public DeleteContactEvent( ContactCard contactCard )
    {
        this.contactCard = contactCard;
    }

    public Type<DeleteContactEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteContactEventHandler handler )
    {
        handler.onDeleteContact( this );
    }

    public ContactCard getContactCard()
    {
        return contactCard;
    }
}
