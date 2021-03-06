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

package biz.turnonline.ecosystem.widget.shared.util;

import biz.turnonline.ecosystem.widget.shared.rest.Contact;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Order;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class Formatter
{
    public static String formatContactName( Contact contact )
    {
        return contact.getBusinessName() != null && !"".equals( contact.getBusinessName() )
                ? contact.getBusinessName()
                : contact.getFirstName() + " " + contact.getLastName();
    }

    public static String formatProductName( Product product )
    {
        return product.getItemName();
    }

    public static String formatOrderName( Order order )
    {
        return order.getId().toString();
    }

    public static String formatPostcode( String postcode )
    {
        return postcode.length() == 5 ? postcode.substring( 0, 3 ) + " " + postcode.substring( 3 ) : postcode;
    }
}
