var Configuration = {
    DOMICILE: "SK",
    LOGIN_ID: "pohorelec@turnonline.biz",
    ACCOUNT_STEWARD_API_ROOT: "https://account.turnon.cloud/api/steward/v1",
    MAPS_API_KEY: "AIzaSyBcFynhFn5xRAXBDshvHMJqn3BNF2ypEOs"
};

var token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImZiMDEyZTk5Y2EwYWNhODI2ZTkwODZiMzIyM2JiOTYwZGFhOTFmODEiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiSm96ZWYgUG9ob3JlbGVjIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS8tYjEwd1g1VnNHRm8vQUFBQUFBQUFBQUkvQUFBQUFBQUFBQUEvQUNldm9RTnB4NGJhcWNzc0lMVGJobHVCZDViSDdhUDdaZy9tby9waG90by5qcGciLCJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vdHVybi1vbmxpbmUtZXUiLCJhdWQiOiJ0dXJuLW9ubGluZS1ldSIsImF1dGhfdGltZSI6MTU1MzgwOTQxOCwidXNlcl9pZCI6IlMyOHVvc1NWblpUWlhnZGNZVDdMcFRGdjNSdzEiLCJzdWIiOiJTMjh1b3NTVm5aVFpYZ2RjWVQ3THBURnYzUncxIiwiaWF0IjoxNTUzODA5NDE4LCJleHAiOjE1NTM4MTMwMTgsImVtYWlsIjoicG9ob3JlbGVjQHR1cm5vbmxpbmUuYml6IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZ29vZ2xlLmNvbSI6WyIxMDExNDI3ODA3NzA5MDM2NjMxMDkiXSwiZW1haWwiOlsicG9ob3JlbGVjQHR1cm5vbmxpbmUuYml6Il19LCJzaWduX2luX3Byb3ZpZGVyIjoiZ29vZ2xlLmNvbSJ9fQ.knCUEUHOAaQG18VKl9NYX6vOLEEhh4SiD9UmIr0ex9yAbMHhLosh3m6VVGQC3BmxJXHW8EjVKJyr9kFWogQydBRYBOMpXkM99lj3TBIBn8OH3H9TC6wsZzRuF1UJle8Yww3xxWXn5lOxRmPXDn0m1SvQ2Ai5mmEoGvFmhHf0QXtYlwjDNSVhSouLGPfh7CHSLcyvBW1C54O-lvdEP419M0vWMgTegI7_zR5tbYog3nqRlPq5mL48un5u_Z7QQxR4MfxjAJo-cQoLNHkopcanxEerXRRR2fqLh9DsZzu8bO6Zp08Tg8OQaa0Bn08EQ38wCWVeRkd3PX98CRS52j5yhg";
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