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

package biz.turnonline.ecosystem.widget.shared.rest.account;

import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.FirebaseAuthDispatcher;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import org.ctoolkit.gwt.client.facade.Items;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

/**
 * The Account steward resource REST facade service interface.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
@Options( dispatcher = FirebaseAuthDispatcher.class, serviceRootKey = Configuration.ACCOUNT_STEWARD_API_ROOT )
public interface AccountStewardFacade
        extends RestService
{
    // accounts

    @GET
    @Path( "accounts/{login_id}" )
    void getAccount( @PathParam( "login_id" ) String loginId, FacadeCallback<Account> callback );

    @GET
    @Path( "accounts/{login_id}/invoicing" )
    void getInvoicingConfig( @PathParam( "login_id" ) String loginId, FacadeCallback<InvoicingConfig> callback );

    @PUT
    @Path( "accounts/{login_id}/invoicing" )
    void update( @PathParam( "login_id" ) String loginId,
                 InvoicingConfig invoicing,
                 FacadeCallback<InvoicingConfig> callback );

    @PUT
    @Path( "accounts/{login_id}" )
    void update( @PathParam( "login_id" ) String loginId,
                 Account account,
                 FacadeCallback<Account> callback );
    // domains

    @GET
    @Path( "accounts/{login_id}/domains" )
    void getDomains( @PathParam( "login_id" ) String loginId,
                     @QueryParam( "limit" ) Integer limit,
                     SuccessCallback<Items<Domain>> callback );

    @GET
    @Path( "accounts/{login_id}/domains" )
    void getFilteredDomains( @PathParam( "login_id" ) String loginId,
                             @QueryParam( "limit" ) Integer limit,
                             @QueryParam( "type" ) String type,
                             SuccessCallback<Items<Domain>> callback );

    @POST
    @Path( "accounts/{login_id}/domains" )
    void create( @PathParam( "login_id" ) String loginId,
                 Domain domain,
                 FacadeCallback<Domain> callback );

    @DELETE
    @Path( "accounts/{login_id}/domains/{name}" )
    void delete( @PathParam( "login_id" ) String loginId,
                 @PathParam( "name" ) String name,
                 FacadeCallback<Void> callback );

    // contacts

    @GET
    @Path( "accounts/{login_id}/contacts" )
    void getContacts( @PathParam( "login_id" ) String loginId,
                      @QueryParam( "offset" ) Integer offset,
                      @QueryParam( "limit" ) Integer limit,
                      @QueryParam( "company" ) Boolean company,
                      SuccessCallback<Items<ContactCard>> callback );

    @GET
    @Path( "accounts/{login_id}/contacts/{contact_id}" )
    void findById( @PathParam( "login_id" ) String loginId,
                   @PathParam( "contact_id" ) Long contactId,
                   FacadeCallback<ContactCard> callback );

    @POST
    @Path( "accounts/{login_id}/contacts" )
    void create( @PathParam( "login_id" ) String loginId,
                 ContactCard contact,
                 FacadeCallback<ContactCard> callback );

    @PUT
    @Path( "accounts/{login_id}/contacts/{contact_id}" )
    void update( @PathParam( "login_id" ) String loginId,
                 @PathParam( "contact_id" ) Long contactId,
                 ContactCard contact,
                 FacadeCallback<ContactCard> callback );

    @DELETE
    @Path( "accounts/{login_id}/contacts/{contact_id}" )
    void delete( @PathParam( "login_id" ) String loginId,
                 @PathParam( "contact_id" ) Long contactId,
                 FacadeCallback<Void> callback );

    // codebooks

    @GET
    @Path( "codebook/countries" )
    void getCountries( @QueryParam( "version" ) String version,
                       SuccessCallback<Items<Country>> callback );

    @GET
    @Path( "codebook/countries/{code}" )
    void getCountry( @PathParam( "code" ) String code,
                     MethodCallback<Country> callback );

    @GET
    @Path( "codebook/legalforms" )
    void getLegalForms( @QueryParam( "version" ) String version,
                        SuccessCallback<Items<LegalForm>> callback );

    @GET
    @Path( "codebook/legalforms/{code}" )
    void getLegalForm( @PathParam( "code" ) String code,
                       MethodCallback<LegalForm> callback );
}
