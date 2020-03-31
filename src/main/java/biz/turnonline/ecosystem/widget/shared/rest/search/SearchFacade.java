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

package biz.turnonline.ecosystem.widget.shared.rest.search;

import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.FirebaseAuthDispatcher;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import org.ctoolkit.gwt.client.facade.Items;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * The Product billing resource REST facade service interface.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
@Options( dispatcher = FirebaseAuthDispatcher.class, serviceRootKey = Configuration.SEARCH_API_ROOT )
public interface SearchFacade
        extends RestService
{
    // global

    @GET
    @Path( "global" )
    void getGlobal( @QueryParam( "query" ) String query,
                    @QueryParam( "offset" ) Integer offset,
                    @QueryParam( "limit" ) Integer limit,
                    SuccessCallback<Items<SearchGlobal>> callback );

    // products

    @GET
    @Path( "products" )
    void getProducts( @QueryParam( "query" ) String query,
                      @QueryParam( "offset" ) Integer offset,
                      @QueryParam( "limit" ) Integer limit,
                      SuccessCallback<Items<SearchProduct>> callback );

    // contacts

    @GET
    @Path( "contacts" )
    void getContacts( @QueryParam( "query" ) String query,
                      @QueryParam( "offset" ) Integer offset,
                      @QueryParam( "limit" ) Integer limit,
                      SuccessCallback<Items<SearchContact>> callback );

}
