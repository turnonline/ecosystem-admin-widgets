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
import java.util.Date;

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
                   @QueryParam( "dateOfIssueFrom" ) Date dateOfIssueFrom,
                   @QueryParam( "dateOfIssueTo" ) Date dateOfIssueTo,
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
}
