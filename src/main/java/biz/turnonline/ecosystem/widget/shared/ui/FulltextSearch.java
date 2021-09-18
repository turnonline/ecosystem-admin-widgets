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
import biz.turnonline.ecosystem.widget.shared.Resources;
import biz.turnonline.ecosystem.widget.shared.rest.search.GlobalItemType;
import biz.turnonline.ecosystem.widget.shared.rest.search.SearchGlobal;
import biz.turnonline.ecosystem.widget.shared.util.Router;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.autocomplete.MaterialAutoComplete;
import gwt.material.design.addins.client.autocomplete.base.MaterialSuggestionOracle;
import gwt.material.design.addins.client.autocomplete.constants.AutocompleteType;
import gwt.material.design.addins.client.base.constants.AddinsCssName;
import gwt.material.design.client.MaterialDesignBase;
import gwt.material.design.client.base.mixin.CssNameMixin;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.incubator.client.search.InlineSearchClientBundle;
import gwt.material.design.incubator.client.search.constants.Theme;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static biz.turnonline.ecosystem.widget.shared.util.Router.Target.SELF;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class FulltextSearch
        extends MaterialAutoComplete
{
    private static AppMessages messages = AppMessages.INSTANCE;

    private static Map<GlobalItemType, String> localizationMap = new HashMap<>();

    private static Map<GlobalItemType, IconType> iconMap = new HashMap<>();

    private static Map<GlobalItemType, Redirection> listRedirectionMap = new HashMap<>();

    private static Map<GlobalItemType, Redirection> detailRedirectionMap = new HashMap<>();

    private static Map<GlobalItemType, Integer> searchOrderMap = new HashMap<>();

    private static Map<Route, GlobalItemType> routeMap = new HashMap<>();

    private static Route route;

    static
    {
        localizationMap.put( GlobalItemType.CONTACT, messages.labelContacts() );
        localizationMap.put( GlobalItemType.PRODUCT, messages.labelProducts() );
        localizationMap.put( GlobalItemType.INVOICE, messages.labelInvoices() );
        localizationMap.put( GlobalItemType.ORDER, messages.labelOrders() );

        iconMap.put( GlobalItemType.CONTACT, IconType.CONTACT_PHONE );
        iconMap.put( GlobalItemType.PRODUCT, IconType.TABLET_MAC );
        iconMap.put( GlobalItemType.INVOICE, IconType.ASSIGNMENT );
        iconMap.put( GlobalItemType.ORDER, IconType.ASSIGNMENT_TURNED_IN );

        listRedirectionMap.put( GlobalItemType.CONTACT, global -> Router.routeToList( Route.CONTACTS, SELF ) );
        listRedirectionMap.put( GlobalItemType.PRODUCT, global -> Router.routeToList( Route.PRODUCTS, SELF ) );
        listRedirectionMap.put( GlobalItemType.INVOICE, global -> Router.routeToList( Route.INVOICES, SELF ) );
        listRedirectionMap.put( GlobalItemType.ORDER, global -> Router.routeToList( Route.ORDERS, SELF ) );

        detailRedirectionMap.put( GlobalItemType.CONTACT, global -> Router.routeToDetail( Route.CONTACTS, new String[]{global.getId()}, SELF ) );
        detailRedirectionMap.put( GlobalItemType.PRODUCT, global -> Router.routeToDetail( Route.PRODUCTS, new String[]{global.getId(), "tabDetail"}, SELF ) );
        detailRedirectionMap.put( GlobalItemType.INVOICE, global -> Router.routeToDetail( Route.INVOICES, new String[]{global.getId(), "tabDetail"}, SELF ) );
        detailRedirectionMap.put( GlobalItemType.ORDER, global -> Router.routeToDetail( Route.ORDERS, new String[]{global.getId(), "tabDetail"}, SELF ) );

        searchOrderMap.put( GlobalItemType.CONTACT, 3 );
        searchOrderMap.put( GlobalItemType.PRODUCT, 4 );
        searchOrderMap.put( GlobalItemType.INVOICE, 1 );
        searchOrderMap.put( GlobalItemType.ORDER, 2 );

        routeMap.put( Route.CONTACTS, GlobalItemType.CONTACT );
        routeMap.put( Route.PRODUCTS, GlobalItemType.PRODUCT );
        routeMap.put( Route.INVOICES, GlobalItemType.INVOICE );
        routeMap.put( Route.ORDERS, GlobalItemType.ORDER );
    }

    static
    {
        MaterialDesignBase.injectCss( InlineSearchClientBundle.INSTANCE.inlineSearchCss() );
    }

    private CssNameMixin<FulltextSearch, Theme> cssNameMixin;

    public FulltextSearch( EventBus eventBus )
    {
        super( new Oracle( eventBus ) );
        setType( AutocompleteType.TEXT );
        setLimit( 5 );

        addStyleName( AddinsCssName.INLINE_SEARCH );
        addStyleName( AddinsCssName.FIXED_INLINE_SEARCH );
        getCssNameMixin().setCssName( Theme.LIGHT );

        Element icon = Document.get().createElement( "i" );
        icon.addClassName( "material-icons" );
        icon.setInnerText( IconType.SEARCH.getCssName() );
        getItemBox().getElement().getNextSiblingElement().appendChild( icon );

        Widget list = getItemBox().getParent().getParent().getParent();
        list.getElement().setAttribute( "class", "" );
        list.getElement().getStyle().setPaddingTop( 10, Style.Unit.PX );

        getItemBox().getElement().setAttribute( "autocomplete", "off" );
        getItemBox().getElement().setAttribute( "type", "search" );

        addSelectionHandler( event -> {
            SuggestOracle.Suggestion selected = event.getSelectedItem();
            if ( selected instanceof Suggest )
            {
                Suggest suggest = ( Suggest ) selected;
                GlobalItemType type = suggest.getGlobal().getType();

                Redirection redirection = detailRedirectionMap.get( type );
                if ( redirection != null )
                {
                    redirection.redirect( suggest.getGlobal() );
                }
            }
            else if ( selected instanceof Separator )
            {
                Separator separator = ( Separator ) selected;
                GlobalItemType type = separator.getGlobal().getType();

                Redirection redirection = listRedirectionMap.get( type );
                if ( redirection != null )
                {
                    redirection.redirect( separator.getGlobal() );
                }
            }
        } );
    }

    protected CssNameMixin<FulltextSearch, Theme> getCssNameMixin()
    {
        if ( cssNameMixin == null )
        {
            cssNameMixin = new CssNameMixin<>( this );
        }
        return cssNameMixin;
    }

    public void setRoute( Route activeRoute )
    {
        route = activeRoute;
    }

    @FunctionalInterface
    private interface Redirection
    {
        void redirect( SearchGlobal global );
    }

    private static class Oracle
            extends MaterialSuggestionOracle
    {
        private final EventBus eventBus;

        private Oracle( EventBus eventBus )
        {
            this.eventBus = eventBus;
        }

        @Override
        public boolean isDisplayStringHTML()
        {
            return true;
        }

        @Override
        public void requestSuggestions( Request request, Callback callback )
        {
            ( ( AppEventBus ) eventBus ).search().getGlobal( request.getQuery(), 0, request.getLimit(),
                    response -> {

//                        response.setItems( Mocks.mockSearchGlobal() );

                        // prepare ordering
                        List<SearchGlobalWrapper> wrapped = new ArrayList<>();
                        response.getItems().forEach( searchGlobal -> {
                            GlobalItemType current = routeMap.get( route );
                            Integer searchOrder = searchOrderMap.get( searchGlobal.getType() );
                            if ( searchOrder == null )
                            {
                                searchOrder = 100; // move to bottom
                            }

                            if ( current != null && current == searchGlobal.getType() )
                            {
                                searchOrder = 0; // move to top
                            }
                            wrapped.add( new SearchGlobalWrapper( searchOrder, searchGlobal ) );
                        } );

                        // sort by order
                        List<SearchGlobal> sortedByType = wrapped.stream()
                                .sorted( Comparator.comparing( SearchGlobalWrapper::getOrder ) )
                                .map( SearchGlobalWrapper::getGlobal )
                                .collect( Collectors.toList() );

                        GlobalItemType currentType = null;
                        List<MaterialSuggestionOracle.Suggestion> suggests = new ArrayList<>();
                        for ( SearchGlobal global : sortedByType )
                        {
                            if ( global.getType() != currentType )
                            {
                                suggests.add( new Separator( global ) );
                            }
                            suggests.add( new Suggest( global ) );

                            currentType = global.getType();
                        }

                        Response autocompleteResponse = new Response();
                        autocompleteResponse.setSuggestions( suggests );
                        callback.onSuggestionsReady( request, autocompleteResponse );
                    } );
        }
    }

    public static class Separator
            implements MaterialSuggestionOracle.Suggestion
    {
        private final SearchGlobal global;

        private Separator( SearchGlobal global )
        {
            this.global = global;
        }

        @Override
        public String getDisplayString()
        {
            String textColor = "blue-text";
            if ( routeMap.get( route ) != null && routeMap.get( route ) == global.getType() )
            {
                textColor = "orange-text";
            }

            StringBuilder html = new StringBuilder();
            html.append( "<div style='cursor: pointer;border-bottom: 1px solid;margin: 0px -12px -13px -20px;padding: 0 0 10px 10px;' class='" ).append( textColor ).append( "'>" );
            html.append( "<i class='" ).append( textColor ).append( " left material-icons' style='margin: -3px 5px 0 0;'>" ).append( icon().getCssName() ).append( "</i>" );
            html.append( "<b class='" ).append( textColor ).append( "'>" ).append( text() ).append( "</b>" );
            html.append( "</div>" );

            return html.toString();
        }

        @Override
        public String getReplacementString()
        {
            return null;
        }

        public SearchGlobal getGlobal()
        {
            return global;
        }

        private IconType icon()
        {
            IconType icon = iconMap.get( global.getType() );
            if ( icon == null )
            {
                icon = IconType.LIST;
            }

            return icon;
        }

        private String text()
        {
            String text = localizationMap.get( global.getType() );
            if ( text == null )
            {
                text = global.getType().name();
            }

            return text;
        }
    }

    public static class Suggest
            implements MaterialSuggestionOracle.Suggestion
    {
        private final SearchGlobal global;

        private Suggest( SearchGlobal global )
        {
            this.global = global;
        }

        @Override
        public String getDisplayString()
        {
            String url = global.getImageUrl();
            if ( url == null || "".equals( url ) )
            {
                url = Resources.INSTANCE.noImage().getSafeUri().asString();
            }

            StringBuilder html = new StringBuilder();
            html.append( "<div style='margin-left: 10px;cursor: pointer;' class='valign-wrapper'>" );
            html.append( "<img class='valign' src='" ).append( url ).append( "' style='border-radius: 0;width:inherit;height:inherit;max-width:32px;max-height:32px;'/>" );
            html.append( "<span class='valign' style='display: inline-block;'>" ).append( global.getName() ).append( "</span>" );
            html.append( "</div>" );

            return html.toString();
        }

        @Override
        public String getReplacementString()
        {
            return global.getName();
        }

        public SearchGlobal getGlobal()
        {
            return global;
        }
    }

    private static class SearchGlobalWrapper
            extends SearchGlobal
    {
        private int order;

        private SearchGlobal global;

        public SearchGlobalWrapper( int order, SearchGlobal global )
        {
            this.global = global;
            this.order = order;
        }

        public int getOrder()
        {
            return order;
        }

        public SearchGlobal getGlobal()
        {
            return global;
        }
    }
}
