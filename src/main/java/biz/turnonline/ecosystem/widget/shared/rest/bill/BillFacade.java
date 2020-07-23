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

package biz.turnonline.ecosystem.widget.shared.rest.bill;

import biz.turnonline.ecosystem.widget.shared.Configuration;
import biz.turnonline.ecosystem.widget.shared.rest.FacadeCallback;
import biz.turnonline.ecosystem.widget.shared.rest.FirebaseAuthDispatcher;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import org.ctoolkit.gwt.client.facade.Items;
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
 * The Bill resource REST facade service interface.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
@SuppressWarnings( "VoidMethodAnnotatedWithGET" )
@Options( dispatcher = FirebaseAuthDispatcher.class, serviceRootKey = Configuration.BILLING_PROCESSOR_API_ROOT )
public interface BillFacade
        extends RestService
{

    //////////////////////
    ////// bills //////
    //////////////////////

    @GET
    @Path( "bills" )
    void getBills( @QueryParam( "offset" ) Integer offset,
                   @QueryParam( "limit" ) Integer limit,
                   @QueryParam( "from" ) String dateOfIssueFrom,
                   @QueryParam( "to" ) String dateOfIssueTo,
                   @QueryParam( "lightList" ) boolean lightList,
                   SuccessCallback<Items<Bill>> callback );

    @GET
    @Path( "bills/{bill_id}" )
    void findBillById( @PathParam( "bill_id" ) Long billId,
                       FacadeCallback<Bill> callback );

    @POST
    @Path( "bills" )
    void createBill( Bill bill,
                     FacadeCallback<Bill> callback );

    @PUT
    @Path( "bills/{bill_id}" )
    void updateBill( @PathParam( "bill_id" ) Long billId,
                     Bill bill,
                     FacadeCallback<Bill> callback );

    @DELETE
    @Path( "bills/{bill_id}" )
    void deleteBill( @PathParam( "bill_id" ) Long billId,
                     FacadeCallback<Void> callback );

    @POST
    @Path( "bills/{bill_id}/approve" )
    void approveBill( @PathParam( "bill_id" ) Long billId,
                     FacadeCallback<Void> callback );

}
