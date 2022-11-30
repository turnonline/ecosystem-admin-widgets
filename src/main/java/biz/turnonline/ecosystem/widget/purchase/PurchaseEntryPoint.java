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

package biz.turnonline.ecosystem.widget.purchase;

import biz.turnonline.ecosystem.widget.purchase.place.PurchaseOrders;
import biz.turnonline.ecosystem.widget.shared.DaggerComponent;
import biz.turnonline.ecosystem.widget.shared.DaggerEntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import gwt.material.design.amcore.client.Am4Core;
import gwt.material.design.amcore.client.theme.AnimatedTheme;

/**
 * The purchases widget application entry point.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class PurchaseEntryPoint
        extends DaggerEntryPoint
{
    public static Place DEFAULT_PLACE = new PurchaseOrders();

    @Override
    protected DaggerComponent component()
    {
        return DaggerPurchaseComponent.create();
    }

    @Override
    public void onModuleLoad()
    {
        super.onModuleLoad();

        Am4Core.useTheme( GWT.create( AnimatedTheme.class ) );
    }
}
