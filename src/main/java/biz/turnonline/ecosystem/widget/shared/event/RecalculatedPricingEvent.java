/*
 *  Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
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
