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

var Configuration = {
    DOMICILE: window.localStorage.getItem("turnonline::account::domicile"),
    CURRENCY: window.localStorage.getItem("turnonline::account::currency"),
    VAT: "STANDARD",
    LOGIN_ID: window.localStorage.getItem("turnonline::account::id"),
    LOGO: window.localStorage.getItem("turnonline::account::logo"),

    ACCOUNT_STEWARD_STORAGE: "https://account.turnonline.cloud/storage/steward/v1",
    PRODUCT_BILLING_STORAGE: "https://billing.turnonline.cloud/storage/billing/v1",
    ACCOUNT_STEWARD_API_ROOT: "https://account.turnonline.cloud/api/steward/v1",
    PRODUCT_BILLING_API_ROOT: "https://billing.turnonline.cloud/api/billing/v1",
    BILLING_PROCESSOR_API_ROOT: "https://bill.turnonline.cloud/api/bill/v1",
    PAYMENT_PROCESSOR_API_ROOT: "https://payment.comvai.com/api/payment/v1",
    BILLING_PROCESSOR_STORAGE: "https://bill.turnonline.cloud/storage/bill/v1",
    SEARCH_API_ROOT: "https://search.turnonline.cloud/api/search/v1",
    MAPS_API_KEY: ""
};