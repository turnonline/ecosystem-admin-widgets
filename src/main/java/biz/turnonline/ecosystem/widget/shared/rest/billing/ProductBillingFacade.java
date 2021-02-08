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

package biz.turnonline.ecosystem.widget.shared.rest.billing;

import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.FirebaseAuthDispatcher;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import org.ctoolkit.gwt.client.facade.Items;
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
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
@SuppressWarnings( "VoidMethodAnnotatedWithGET" )
@Options( dispatcher = FirebaseAuthDispatcher.class, serviceRootKey = Configuration.PRODUCT_BILLING_API_ROOT )
public interface ProductBillingFacade
        extends RestService
{
    //////////////////////
    ////// products //////
    //////////////////////

    @GET
    @Path( "products" )
    void getProducts( @QueryParam( "offset" ) Integer offset,
                      @QueryParam( "limit" ) Integer limit,
                      @QueryParam( "lightList" ) boolean lightList,
                      @HeaderParam( "vnd.turnon.cloud.calc-pricing-items" ) boolean calcPricingItems,
                      SuccessCallback<Items<Product>> callback );

    @GET
    @Path( "products/{product_id}" )
    void findProductById( @PathParam( "product_id" ) Long productId,
                          @HeaderParam( "vnd.turnon.cloud.calc-pricing-items" ) boolean calcPricingItems,
                          FacadeCallback<Product> callback );

    @POST
    @Path( "products" )
    void createProduct( @HeaderParam( "vnd.turnon.cloud.calc-pricing-items" ) boolean calcPricingItems,
                        Product product,
                        FacadeCallback<Product> callback );

    @PUT
    @Path( "products/{product_id}" )
    void updateProduct( @PathParam( "product_id" ) Long productId,
                        @HeaderParam( "vnd.turnon.cloud.calc-pricing-items" ) boolean calcPricingItems,
                        Product product,
                        FacadeCallback<Product> callback );

    @DELETE
    @Path( "products/{product_id}" )
    void deleteProduct( @PathParam( "product_id" ) Long productId,
                        FacadeCallback<Void> callback );

    @DELETE
    @Path( "products/{product_id}/publishing/pictures/{order}" )
    void deleteProductPicture( @PathParam( "product_id" ) Long productId,
                               @PathParam( "order" ) Integer order,
                               FacadeCallback<Void> callback );

    //////////////////////
    ////// orders ////////
    //////////////////////

    @GET
    @Path( "orders" )
    void getOrders( @QueryParam( "offset" ) Integer offset,
                    @QueryParam( "limit" ) Integer limit,
                    @QueryParam( "lightList" ) boolean lightList,
                    SuccessCallback<Items<Order>> callback );

    @GET
    @Path( "orders/{order_id}" )
    void findOrderById( @PathParam( "order_id" ) Long orderId,
                        @QueryParam( "invoices" ) Integer numberOf,
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

    @GET
    @Path( "orders/{order_id}/status" )
    void getOrderStatus( @PathParam( "order_id" ) Long orderId,
                         SuccessCallback<OrderStatus> callback );

    @PUT
    @Path( "orders/{order_id}/status" )
    void changeOrderStatus( @PathParam( "order_id" ) Long orderId,
                            OrderStatus status,
                            FacadeCallback<Void> callback );

    //////////////////////
    ////// invoices //////
    //////////////////////

    @GET
    @Path( "invoices" )
    void getInvoices( @QueryParam( "offset" ) Integer offset,
                      @QueryParam( "limit" ) Integer limit,
                      @QueryParam( "lightList" ) boolean lightList,
                      SuccessCallback<Items<Invoice>> callback );

    @GET
    @Path( "orders/{order_id}/invoices" )
    void getOrderInvoices( @PathParam( "order_id" ) Long orderId,
                           @QueryParam( "offset" ) Integer offset,
                           @QueryParam( "limit" ) Integer limit,
                           @QueryParam( "lightList" ) boolean lightList,
                           SuccessCallback<Items<Invoice>> callback );

    @POST
    @Path( "orders/{order_id}/invoices" )
    void createOrderInvoice( @PathParam( "order_id" ) Long orderId,
                             Invoice invoice,
                             FacadeCallback<Invoice> callback );

    @GET
    @Path( "orders/{order_id}/invoices/{invoice_id}" )
    void findInvoiceById( @PathParam( "order_id" ) Long orderId,
                          @PathParam( "invoice_id" ) Long invoiceId,
                          FacadeCallback<Invoice> callback );

    @POST
    @Path( "invoices" )
    void createInvoice( Invoice invoice,
                        FacadeCallback<Invoice> callback );

    @PUT
    @Path( "orders/{order_id}/invoices/{invoice_id}" )
    void updateInvoice( @PathParam( "order_id" ) Long orderId,
                        @PathParam( "invoice_id" ) Long invoiceId,
                        Invoice invoice,
                        FacadeCallback<Invoice> callback );

    @PUT
    @Path( "orders/{order_id}/invoices/{invoice_id}" )
    void sendInvoice( @PathParam( "order_id" ) Long orderId,
                      @PathParam( "invoice_id" ) Long invoiceId,
                      @HeaderParam( "vnd.turnon.cloud.send-invoice" ) Boolean sendInvoice,
                      Invoice invoice,
                      FacadeCallback<Invoice> callback );

    @PUT
    @Path( "orders/{order_id}/invoices/{invoice_id}" )
    void emailInvoice( @PathParam( "order_id" ) Long orderId,
                       @PathParam( "invoice_id" ) Long invoiceId,
                       @HeaderParam( "vnd.turnon.cloud.send-invoice" ) Boolean sendInvoice,
                       @HeaderParam( "vnd.turnon.cloud.contact-email" ) String email,
                       Invoice invoice,
                       FacadeCallback<Invoice> callback );

    @DELETE
    @Path( "orders/{order_id}/invoices/{invoice_id}" )
    void deleteInvoice( @PathParam( "order_id" ) Long orderId,
                        @PathParam( "invoice_id" ) Long invoiceId,
                        FacadeCallback<Void> callback );

    @POST
    @Path( "prices" )
    void calculate( Pricing pricing, SuccessCallback<Pricing> callback );

    //////////////////////
    ////// codebooks /////
    //////////////////////

    @GET
    @Path( "codebook/billing-units" )
    void getBillingUnits( @HeaderParam( "Accept-Language" ) String acceptLanguage,
                          SuccessCallback<Items<BillingUnit>> callback );

    @GET
    @Path( "codebook/vat-rates" )
    void getVatRates( @QueryParam( "domicile" ) String domicile,
                      @HeaderParam( "Accept-Language" ) String acceptLanguage,
                      SuccessCallback<Items<VatRate>> callback );

    @GET
    @Path( "codebook/categories" )
    void getCategories( @HeaderParam( "Accept-Language" ) String acceptLanguage,
                          SuccessCallback<Items<Category>> callback );

    //////////////////////
    ///// purchases //////
    //////////////////////

    @GET
    @Path( "purchases/orders" )
    void searchPurchaseOrders( @QueryParam( "offset" ) Integer offset,
                               @QueryParam( "limit" ) Integer limit,
                               @QueryParam( "lightList" ) boolean lightList,
                               SuccessCallback<Items<PurchaseOrder>> callback );

    @GET
    @Path( "purchases/orders/{order_id}" )
    void getPurchaseOrder( @PathParam( "order_id" ) Long orderId,
                           @QueryParam( "invoices" ) Integer numberOf,
                           SuccessCallback<PurchaseOrder> callback );

    @DELETE
    @Path( "purchases/orders/{order_id}" )
    void declinePurchaseOrder( @PathParam( "order_id" ) Long orderId,
                               FacadeCallback<Void> callback );

    @GET
    @Path( "purchases/expenses" )
    void searchExpenses( @QueryParam( "offset" ) Integer offset,
                         @QueryParam( "limit" ) Integer limit,
                         @QueryParam( "lightList" ) boolean lightList,
                         SuccessCallback<Items<Expense>> callback );

    @GET
    @Path( "purchases/expenses" )
    void searchExpensesByOrder( @QueryParam( "offset" ) Integer offset,
                                @QueryParam( "limit" ) Integer limit,
                                @QueryParam( "lightList" ) boolean lightList,
                                @QueryParam( "orderId" ) Long orderId,
                                SuccessCallback<Items<Expense>> callback );

    @GET
    @Path( "purchases/orders/{order_id}/invoices" )
    void listOrderIncomingInvoices( @PathParam( "order_id" ) Long orderId,
                                    @QueryParam( "offset" ) Integer offset,
                                    @QueryParam( "limit" ) Integer limit,
                                    @QueryParam( "lightList" ) boolean lightList,
                                    SuccessCallback<Items<IncomingInvoice>> callback );

    @GET
    @Path( "purchases/orders/{order_id}/invoices/{invoice_id}" )
    void getIncomingOrderInvoice( @PathParam( "order_id" ) Long orderId,
                                  @PathParam( "invoice_id" ) Long invoiceId,
                                  SuccessCallback<IncomingInvoice> callback );

    @DELETE
    @Path( "purchases/orders/{order_id}/invoices/{invoice_id}" )
    void deleteIncomingInvoice( @PathParam( "order_id" ) Long orderId,
                                @PathParam( "invoice_id" ) Long invoiceId,
                                FacadeCallback<Void> callback );

    /**
     * Searches for transaction that match the filtering criteria.
     *
     * @param orderId   Identification of the order to search for transactions.
     *                  If invoice Id is not provided, a transactions of all associated invoices will be in the results.
     * @param invoiceId Identification of the invoice to search settled transactions.
     *                  Order Id is being required for successful match.
     * @param callback  the result callback
     */
    @GET
    @Path( "transactions" )
    void getTransactions( @QueryParam( "orderId" ) Long orderId,
                          @QueryParam( "invoiceId" ) Long invoiceId,
                          SuccessCallback<Items<Transaction>> callback );
}
