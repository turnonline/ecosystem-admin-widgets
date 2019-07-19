/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.shared.event;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Pricing;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Pricing calculation event with already recalculate {@link Pricing} payload.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class RecalculatedPricingEvent
        extends GwtEvent<RecalculatedPricingEventHandler>
{
    public static Type<RecalculatedPricingEventHandler> TYPE = new Type<>();

    private final Pricing pricing;

    public RecalculatedPricingEvent( Pricing pricing )
    {
        this.pricing = pricing;
    }

    public Type<RecalculatedPricingEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    /**
     * Returns already recalculate pricing items.
     */
    public Pricing getPricing()
    {
        return pricing;
    }

    protected void dispatch( RecalculatedPricingEventHandler handler )
    {
        handler.onCalculatePricing( this );
    }
}
