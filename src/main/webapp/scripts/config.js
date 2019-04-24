var Configuration = {
    DOMICILE: "SK",
    CURRENCY: "EUR",
    VAT: "STANDARD",
    LOGIN_ID: "pohorelec@turnonline.biz",
    ACCOUNT_STEWARD_API_ROOT: "https://account.turnon.cloud/api/steward/v1",
    PRODUCT_BILLING_API_ROOT: "https://billing.turnon.cloud/api/billing/v1",
    MAPS_API_KEY: "AIzaSyBcFynhFn5xRAXBDshvHMJqn3BNF2ypEOs"
};

var token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImZmMWRmNWExNWI1Y2Y1ODJiNjFhMjEzODVjMGNmYWVkZmRiNmE3NDgiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiSm96ZWYgUG9ob3JlbGVjIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS8tYjEwd1g1VnNHRm8vQUFBQUFBQUFBQUkvQUFBQUFBQUFBQUEvQUNldm9RTnB4NGJhcWNzc0lMVGJobHVCZDViSDdhUDdaZy9tby9waG90by5qcGciLCJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vdHVybi1vbmxpbmUtZXUiLCJhdWQiOiJ0dXJuLW9ubGluZS1ldSIsImF1dGhfdGltZSI6MTU1NDMyMzQxNSwidXNlcl9pZCI6IlMyOHVvc1NWblpUWlhnZGNZVDdMcFRGdjNSdzEiLCJzdWIiOiJTMjh1b3NTVm5aVFpYZ2RjWVQ3THBURnYzUncxIiwiaWF0IjoxNTU0MzIzNDE1LCJleHAiOjE1NTQzMjcwMTUsImVtYWlsIjoicG9ob3JlbGVjQHR1cm5vbmxpbmUuYml6IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZ29vZ2xlLmNvbSI6WyIxMDExNDI3ODA3NzA5MDM2NjMxMDkiXSwiZW1haWwiOlsicG9ob3JlbGVjQHR1cm5vbmxpbmUuYml6Il19LCJzaWduX2luX3Byb3ZpZGVyIjoiZ29vZ2xlLmNvbSJ9fQ.g5ZlwmB3WEYAIZmozFndHXHfG6PWPAdNX2mMGEfPpPWi1Yfsm_RthXzXVlzjgoi7Be3h3_mKv6lWwKxn9O-y2Df14U_A5d0rCO6GbJ3hDnFiL9576NeCPvLU-qI2lZIOESDl5wq6q3mlIGdDf35t5zxQVc0JhtGX2wJ1Tny-80e-jT1Ro3UFZnMdqFbKyKMlB_1N2yenGn8hO0Rm9blHX8xn8bu5WcbzmfwZfllEEpCNDeDNDIB83hhtLAVBbOHBhuBN6kbHO5_1avnFBKdecCYJYaOTY2GloTqsAjuzyOT2QXivBJRNuQ3z1Dx2ztQcv170it0S1l4GQ1ro-AqIYA";
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
                photoUrl: "https://www.google.com/s2/photos/public/AIbEiAIAAABDCPeaz9KoqZfZbSILdmNhcmRfcGhvdG8qKDRkMTJhZDMzMzlhOGIzZThmOWY4NmU1N2RiZTgwNjdhOGZmNTY4YjQwAT0MVCzv9oi9rn31QqHvEYzDsYFd?sz=48"
            }
        }
    }
};