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

package biz.turnonline.ecosystem.widget.shared.rest.productbilling;

import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.FirebaseAuthDispatcher;
import org.ctoolkit.gwt.client.facade.Items;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

    @GET
    @Path( "products/{product_id}" )
    void findById( @PathParam( "product_id" ) Long contactId,
                   MethodCallback<Product> callback );

    @POST
    @Path( "products" )
    void create( @HeaderParam( "X-Calc-PricingItems" ) boolean calcPricingItems,
                 Product product,
                 MethodCallback<Product> callback );

    @PUT
    @Path( "products/{product_id}" )
    void update( @PathParam( "product_id" ) Long contactId,
                 @HeaderParam( "X-Calc-PricingItems" ) boolean calcPricingItems,
                 Product product,
                 MethodCallback<Product> callback );

    @DELETE
    @Path( "products/{product_id}" )
    void delete( @PathParam( "product_id" ) Long productId,
                 MethodCallback<Void> callback );

    // codebooks

    @GET
    @Path( "codebook/billing-units" )
    void getBillingUnits( @HeaderParam( "Accept-Language" ) String acceptLanguage,
                          MethodCallback<Items<BillingUnit>> callback );

    @GET
    @Path( "codebook/vat-rates" )
    void getVatRates( @QueryParam( "domicile" ) String domicile,
                      @HeaderParam( "Accept-Language" ) String acceptLanguage,
                      MethodCallback<Items<VatRate>> callback );
}
