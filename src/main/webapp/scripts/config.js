/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
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

var Configuration = {
    DOMICILE: "SK",
    CURRENCY: "EUR",
    VAT: "STANDARD",
    LOGIN_ID: "pohorelec@turnonline.biz",
    LOGO: "",
    ACCOUNT_STEWARD_STORAGE: "https://account.turnonline.cloud/storage/steward/v1",
    PRODUCT_BILLING_STORAGE: "https://billing.turnonline.cloud/storage/billing/v1",
    ACCOUNT_STEWARD_API_ROOT: "https://account.turnonline.cloud/api/steward/v1",
    PRODUCT_BILLING_API_ROOT: "https://billing.turnonline.cloud/api/billing/v1",
    BILL_API_ROOT: "https://bill.turnonline.cloud/api/bill/v1",
    BILL_STORAGE: "https://bill.turnonline.cloud/storage/bill/v1",
    SEARCH_API_ROOT: "https://search.turnonline.cloud/api/search/v1",
    MAPS_API_KEY: "AIzaSyBcFynhFn5xRAXBDshvHMJqn3BNF2ypEOs"
};

var token = window.localStorage.getItem("turnonline-token");
firebase = {
    auth: function () {
        return {
            currentUser: {
                getIdToken: function () {
                    return {
                        then: function ( callback ) {
                            callback.call( this, token );
                        }
                    }
                },
                email: "john.foo@turnonline.biz",
                photoURL: "https://i1.sndcdn.com/avatars-000337104679-fbgb18-t500x500.jpg"
            }
        }
    }
};