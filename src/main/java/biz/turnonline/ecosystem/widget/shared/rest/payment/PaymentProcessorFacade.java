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

package biz.turnonline.ecosystem.widget.shared.rest.payment;

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
 * The Payment Processor REST facade service interface.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
@SuppressWarnings( "VoidMethodAnnotatedWithGET" )
@Options( dispatcher = FirebaseAuthDispatcher.class, serviceRootKey = Configuration.PAYMENT_PROCESSOR_API_ROOT )
public interface PaymentProcessorFacade
        extends RestService
{
    //////////////////////
    ////// bank account //
    //////////////////////

    @GET
    @Path( "bank-accounts" )
    void getBankAccounts( @QueryParam( "offset" ) Integer offset,
                          @QueryParam( "limit" ) Integer limit,
                          SuccessCallback<Items<BankAccount>> callback );

    @GET
    @Path( "bank-accounts/{bank_account_id}" )
    void findBankAccountById( @PathParam( "bank_account_id" ) Long bankAccountId,
                              FacadeCallback<BankAccount> callback );

    @POST
    @Path( "bank-accounts" )
    void createBankAccount( BankAccount bankAccount,
                            FacadeCallback<BankAccount> callback );

    @PUT
    @Path( "bank-accounts/{bank_account_id}" )
    void updateBankAccount( @PathParam( "bank_account_id" ) Long bankAccountId,
                            BankAccount bankAccount,
                            FacadeCallback<BankAccount> callback );

    @PUT
    @Path( "bank-accounts/{bank_account_id}/primary" )
    void markBankAccountAsPrimary( @PathParam( "bank_account_id" ) Long bankAccountId,
                                   FacadeCallback<BankAccount> callback );

    @PUT
    @Path( "bank-accounts/{bank_code}/certificates/actual" )
    void integrateWithBank( @PathParam( "bank_code" ) String bankCode,
                            Certificate certificate,
                            FacadeCallback<Certificate> callback );

    @GET
    @Path( "bank-accounts/primary" )
    void findBankAccountById( @QueryParam( "country" ) String country,
                              FacadeCallback<BankAccount> callback );

    @DELETE
    @Path( "bank-accounts/{bank_account_id}" )
    void deleteBankAccount( @PathParam( "bank_account_id" ) Long bankAccountId,
                            FacadeCallback<Void> callback );

    //////////////////////
    ////// transactions /////
    //////////////////////

    @GET
    @Path( "transactions" )
    void getTransactions( @QueryParam( "offset" ) Integer offset,
                          @QueryParam( "limit" ) Integer limit,
                          @QueryParam( "operation" ) String operation,
                          @QueryParam( "type" ) String type,
                          @QueryParam( "from" ) String from,
                          @QueryParam( "to" ) String to,
                          SuccessCallback<Items<Transaction>> callback );

    @GET
    @Path( "transactions/{transaction_id}" )
    void findTransactionById( @PathParam( "transaction_id" ) Long transactionId,
                          SuccessCallback<Transaction> callback );

    //////////////////////
    ////// codebooks /////
    //////////////////////

    @GET
    @Path( "codebook/bank-code" )
    void getBankCodes( @HeaderParam( "Accept-Language" ) String acceptLanguage,
                       @QueryParam( "country" ) String country,
                       SuccessCallback<Items<BankCode>> callback );

    ///////////////////////
    ////// categories /////
    ///////////////////////

    @GET
    @Path( "categories" )
    void getCategories( SuccessCallback<Items<Category>> callback );

    @GET
    @Path( "categories/{category_id}" )
    void findCategoryById( @PathParam( "category_id" ) Long categoryId,
                           FacadeCallback<Category> callback );

    @POST
    @Path( "categories" )
    void createCategory( Category category,
                         FacadeCallback<Category> callback );

    @PUT
    @Path( "categories/{category_id}" )
    void updateCategory( @PathParam( "category_id" ) Long categoryId,
                         Category category,
                         FacadeCallback<Category> callback );

    @DELETE
    @Path( "categories/{category_id}" )
    void deleteCategory( @PathParam( "category_id" ) Long categoryId,
                         FacadeCallback<Void> callback );

    @GET
    @Path( "categories/transactions/{transaction_id}" )
    void getCategoriesForTransaction( @PathParam( "transaction_id" ) Long transactionId,
                                      SuccessCallback<Items<Category>> callback );
}
