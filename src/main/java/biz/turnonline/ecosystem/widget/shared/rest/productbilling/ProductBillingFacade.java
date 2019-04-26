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
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
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
                      FacadeCallback<Items<Product>> callback );

    @GET
    @Path( "products/{product_id}" )
    void findProductById( @PathParam( "product_id" ) Long productId,
                          FacadeCallback<Product> callback );

    @POST
    @Path( "products" )
    void createProduct( @HeaderParam( "X-Calc-PricingItems" ) boolean calcPricingItems,
                        Product product,
                        FacadeCallback<Product> callback );

    @PUT
    @Path( "products/{product_id}" )
    void updateProduct( @PathParam( "product_id" ) Long productId,
                        @HeaderParam( "X-Calc-PricingItems" ) boolean calcPricingItems,
                        Product product,
                        FacadeCallback<Product> callback );

    @DELETE
    @Path( "products/{product_id}" )
    void deleteProduct( @PathParam( "product_id" ) Long productId,
                        FacadeCallback<Void> callback );

    // orders

    @GET
    @Path( "orders" )
    void getOrders( @QueryParam( "offset" ) Integer offset,
                    @QueryParam( "limit" ) Integer limit,
                    @QueryParam( "lightList" ) boolean lightList,
                    FacadeCallback<Items<Order>> callback );

    @GET
    @Path( "orders/{order_id}" )
    void findOrderById( @PathParam( "order_id" ) Long orderId,
                        FacadeCallback<Order> callback );

    @POST
    @Path( "orders" )
    void createOrder( Order order,
                      FacadeCallback<Order> callback );

    @PUT
    @Path( "orders/{order_id}" )
    void updateOrder( @PathParam( "order_id" ) Long orderId,
                      Order order,
                      FacadeCallback<Order> callback );

    @DELETE
    @Path( "orders/{order_id}" )
    void deleteOrder( @PathParam( "order_id" ) Long orderId,
                      FacadeCallback<Void> callback );

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
