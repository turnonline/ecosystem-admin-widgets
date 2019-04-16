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

package biz.turnonline.ecosystem.widget.shared.rest.accountsteward;

import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.FirebaseAuthDispatcher;
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
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@Options( dispatcher = FirebaseAuthDispatcher.class, serviceRootKey = Configuration.ACCOUNT_STEWARD_API_ROOT )
public interface AccountStewardFacade
        extends RestService
{
    // contacts

    @GET
    @Path( "accounts/{login_id}/contacts" )
    void getContacts( @PathParam( "login_id" ) String loginId,
                      @QueryParam( "offset" ) Integer offset,
                      @QueryParam( "limit" ) Integer limit,
                      @QueryParam( "company" ) Boolean company,
                      FacadeCallback<Items<ContactCard>> callback );

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
                       MethodCallback<Items<Country>> callback );

    @GET
    @Path( "codebook/countries/{code}" )
    void getCountry( @PathParam( "code" ) String code,
                     MethodCallback<Country> callback );

    @GET
    @Path( "codebook/legalforms" )
    void getLegalForms( @QueryParam( "version" ) String version,
                        MethodCallback<Items<LegalForm>> callback );

    @GET
    @Path( "codebook/legalforms/{code}" )
    void getLegalForm( @PathParam( "code" ) String code,
                       MethodCallback<LegalForm> callback );
}
