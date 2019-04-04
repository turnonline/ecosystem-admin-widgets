package biz.turnonline.ecosystem.widget.contact.event;

import biz.turnonline.ecosystem.widget.shared.rest.accountsteward.ContactCard;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class EditContactEvent
        extends GwtEvent<EditContactEventHandler>
{
    public static Type<EditContactEventHandler> TYPE = new Type<EditContactEventHandler>();

    private ContactCard contactCard;

    public EditContactEvent() {
    }

    public EditContactEvent( ContactCard contactCard )
    {
        this.contactCard = contactCard;
    }

    public Type<EditContactEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( EditContactEventHandler handler )
    {
        handler.onEditContact( this );
    }

    public ContactCard getContactCard()
    {
        return contactCard;
    }
}
