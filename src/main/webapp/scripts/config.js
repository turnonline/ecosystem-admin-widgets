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
                photoURL: "data:image/png;base64,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"
            }
        }
    }
};