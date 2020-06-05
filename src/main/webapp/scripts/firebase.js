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
function initFirebase()
{
    var apiKey = "AIzaSyC5GaQdU3T2rnnhxMdPRht5EUDttzim55Q";
    var projectId = "turnon-t1";
    var senderId = "620698461320";

    var config = {
        apiKey: apiKey,
        authDomain: projectId + '.firebaseapp.com',
        databaseURL: 'https://' + projectId + '.firebaseio.com',
        projectId: projectId,
        storageBucket: projectId + '.appspot.com',
        messagingSenderId: senderId
    };
    firebase.initializeApp( config );
}

function initWidget( src )
{
    // <meta name="gwt:property" content="locale=${locale}">
    let meta = document.createElement( "meta" );
    meta.setAttribute( "name", "gwt:property" );
    meta.setAttribute( "content", "locale=" + getCookie( "locale" ) );

    document.head.appendChild(meta);

    firebase.auth().onAuthStateChanged( function ( user ) {
        if ( user )
        {
            let script = document.createElement( 'script' );
            script.type = 'text/javascript';
            script.language = 'javascript';
            script.setAttribute( 'language', 'javascript' );
            script.src = src;

            document.body.appendChild( script );
        }
        else
        {
            window.location.href = "/signin.html";
        }
    } );
}

function getCookie( name )
{
    var match = document.cookie.match( new RegExp( '(^| )' + name + '=([^;]+)' ) );
    if ( match ) return match[2];
}

function logout()
{
    firebase.auth().signOut().then( function () {
        window.location.href = "/signin.html";
    } );
}

initFirebase();
