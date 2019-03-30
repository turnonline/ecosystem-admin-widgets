var Configuration = {
    DOMICILE: "SK",
    LOGIN_ID: "pohorelec@turnonline.biz",
    ACCOUNT_STEWARD_API_ROOT: "https://account.turnon.cloud/api/steward/v1",
    PRODUCT_BILLING_API_ROOT: "https://billing.turnon.cloud/api/billing/v1",
    MAPS_API_KEY: "AIzaSyBcFynhFn5xRAXBDshvHMJqn3BNF2ypEOs"
};

var token = "";
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