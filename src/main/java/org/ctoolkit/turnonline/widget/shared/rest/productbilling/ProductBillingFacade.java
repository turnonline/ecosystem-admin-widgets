/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
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

package org.ctoolkit.turnonline.widget.shared.rest.productbilling;

import org.ctoolkit.gwt.client.facade.Items;
import org.ctoolkit.turnonline.widget.shared.Configuration;
import org.ctoolkit.turnonline.widget.shared.rest.FirebaseAuthDispatcher;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

/**
 * The Product billing resource REST facade service interface.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@Options( dispatcher = FirebaseAuthDispatcher.class, serviceRootKey = Configuration.PRODUCT_BILLING_API_ROOT )
public interface ProductBillingFacade
        extends RestService
{
    // products

    @GET
    @Path( "products" )
    void getProducts( @QueryParam( "offset" ) Integer offset,
                      @QueryParam( "limit" ) Integer limit,
                      @QueryParam( "lightList" ) boolean lightList,
                      @HeaderParam( "X-Calc-PricingItems" ) boolean calcPricingItems,
                      MethodCallback<Items<Product>> callback );

    @DELETE
    @Path( "products/{product_id}" )
    void delete( @PathParam( "product_id" ) Long productId,
                 MethodCallback<Void> callback );
}
