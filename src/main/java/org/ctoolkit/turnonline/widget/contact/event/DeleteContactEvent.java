package org.ctoolkit.turnonline.widget.contact.event;

import com.google.gwt.event.shared.GwtEvent;
import org.ctoolkit.turnonline.widget.shared.rest.accountsteward.ContactCard;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class DeleteContactEvent
        extends GwtEvent<DeleteContactEventHandler>
{
    public static Type<DeleteContactEventHandler> TYPE = new Type<DeleteContactEventHandler>();

    private final List<ContactCard> contactCards;

    public DeleteContactEvent( List<ContactCard> contactCards )
    {
        this.contactCards = contactCards;
    }

    public Type<DeleteContactEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( DeleteContactEventHandler handler )
    {
        handler.onDeleteContact( this );
    }

    public List<ContactCard> getContactCards()
    {
        return contactCards;
    }
}
