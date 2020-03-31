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

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.search.SearchContact;
import biz.turnonline.ecosystem.widget.shared.util.Formatter;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.autocomplete.MaterialAutoComplete;
import gwt.material.design.addins.client.autocomplete.base.MaterialSuggestionOracle;
import gwt.material.design.addins.client.autocomplete.constants.AutocompleteType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ContactAutoComplete
        extends MaterialAutoComplete
{
    private static AppMessages messages = AppMessages.INSTANCE;

    public ContactAutoComplete( EventBus eventBus )
    {
        super( new ContactOracle( eventBus ) );
        setType( AutocompleteType.TEXT );
        setLimit( 5 );

        FlowPanel parent = ( FlowPanel ) getItemBox().getParent().getParent().getParent().getParent();
        InputSearchIcon icon = new InputSearchIcon();
        icon.getElement().getStyle().setMarginTop( -42, Style.Unit.PX );
        parent.add( icon );

        getItemBox().getElement().setAttribute( "autocomplete", "off" );
        getLabel().getElement().getStyle().setMarginLeft( -10, Style.Unit.PX );

        setTooltip( messages.tooltipContactAutocomplete() );
    }

    private static class ContactOracle
            extends MaterialSuggestionOracle
    {
        private final EventBus eventBus;

        private ContactOracle( EventBus eventBus )
        {
            this.eventBus = eventBus;
        }

        @Override
        public void requestSuggestions( Request request, Callback callback )
        {
            ( ( AppEventBus ) eventBus ).search().getContacts( request.getQuery(), 0, request.getLimit(),
                    response -> {
                        Response resp = new Response();

                        List<ContactSuggest> suggests = new ArrayList<>();
                        response.getItems().forEach( contact -> suggests.add( new ContactSuggest( contact ) ) );
                        resp.setSuggestions( suggests );
                        callback.onSuggestionsReady( request, resp );
                    } );
        }
    }

    public static class ContactSuggest
            implements MaterialSuggestionOracle.Suggestion
    {
        private final SearchContact contact;

        private ContactSuggest( SearchContact contact )
        {
            this.contact = contact;
        }

        @Override
        public String getDisplayString()
        {
            return getReplacementString();
        }

        @Override
        public String getReplacementString()
        {
            return Formatter.formatContactName( contact );
        }

        public SearchContact getContact()
        {
            return contact;
        }
    }
}
