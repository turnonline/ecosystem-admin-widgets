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

package biz.turnonline.ecosystem.widget.shared.rest.search;

import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.FirebaseAuthDispatcher;
import org.ctoolkit.gwt.client.facade.Items;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * The Product billing resource REST facade service interface.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@Options( dispatcher = FirebaseAuthDispatcher.class, serviceRootKey = Configuration.SEARCH_API_ROOT )
public interface SearchFacade
        extends RestService
{
    // products

    @GET
    @Path( "products" )
    void getProducts( @QueryParam( "query" ) String query,
                      @QueryParam( "offset" ) Integer offset,
                      @QueryParam( "limit" ) Integer limit,
                      FacadeCallback<Items<SearchProduct>> callback );

    // contacts

    @GET
    @Path( "contacts" )
    void getContacts( @QueryParam( "query" ) String query,
                      @QueryParam( "offset" ) Integer offset,
                      @QueryParam( "limit" ) Integer limit,
                      FacadeCallback<Items<SearchContact>> callback );

}
