package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.ui.SuggestOracle;
import gwt.material.design.addins.client.autocomplete.MaterialAutoComplete;
import gwt.material.design.addins.client.autocomplete.base.MaterialSuggestionOracle;
import gwt.material.design.client.ui.MaterialChip;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class TransactionFullTextSearch
        extends MaterialAutoComplete
        implements HasTransactionSuggestRemovedHandlers
{
    public TransactionFullTextSearch()
    {
        super( new Oracle( new ArrayList<>() ) );

        setChipProvider( new ChipProvider() );

        getItemBox().getElement().getStyle().setMarginBottom( 0, Style.Unit.PX );

        setLimit( 10 );
    }

    public void setTransactions( List<Transaction> transactions )
    {
        ( ( Oracle ) getSuggestions() ).setTransactions( transactions );
    }

    @Override
    public HandlerRegistration addTransactionSuggestRemoveHandler( TransactionSuggestRemovedHandler handler )
    {
        return this.addHandler( handler, TransactionSuggestRemovedEvent.getType() );
    }

    private class ChipProvider
            extends MaterialAutoComplete.DefaultMaterialChipProvider
    {
        @Override
        public MaterialChip getChip( SuggestOracle.Suggestion suggestion )
        {
            MaterialChip chip = super.getChip( suggestion );
            chip.getIcon().addClickHandler( event -> {
                Suggest s = (Suggest) suggestion;

                TransactionSuggestRemovedEvent.fire( TransactionFullTextSearch.this, s.getTransaction() );
            } );
            return chip;
        }
    }

    private static class Oracle
            extends MaterialSuggestionOracle
    {
        private List<Transaction> transactions;

        public Oracle( List<Transaction> transactions )
        {
            this.transactions = transactions;
        }

        public void setTransactions( List<Transaction> transactions )
        {
            this.transactions = transactions;
        }

        @Override
        public boolean isDisplayStringHTML()
        {
            return true;
        }

        @Override
        public void requestSuggestions( Request request, Callback callback )
        {
            List<MaterialSuggestionOracle.Suggestion> suggests = doQuery( request.getQuery() )
                    .stream()
                    .map( Suggest::new )
                    .collect( Collectors.toList() );

            Response autocompleteResponse = new Response();
            autocompleteResponse.setSuggestions( suggests );
            callback.onSuggestionsReady( request, autocompleteResponse );
        }

        private List<Transaction> doQuery( String query )
        {
            return transactions.stream()
                    .filter( transaction ->
                            queryTransactionId( transaction, query ) ||
                                    queryMerchant( transaction, query ) ||
                                    queryCategory( transaction, query ) )
                    .collect( Collectors.toList() );
        }

        private boolean queryTransactionId( Transaction transaction, String query )
        {
            return transaction.getTransactionId().toString().toLowerCase( Locale.ROOT ).contains( query.toLowerCase( Locale.ROOT ) );
        }

        private boolean queryMerchant( Transaction transaction, String query )
        {
            if ( transaction.getMerchant() == null )
            {
                return false;
            }

            return transaction.getMerchant().getName().toLowerCase( Locale.ROOT ).contains( query.toLowerCase( Locale.ROOT ) );
        }

        private boolean queryCategory( Transaction transaction, String query )
        {
            if ( transaction.getCategories() == null )
            {
                return false;
            }

            return transaction.getCategories().stream()
                    .anyMatch( category -> category.getName().toLowerCase( Locale.ROOT ).contains( query.toLowerCase( Locale.ROOT ) ) );
        }
    }

    public static class Suggest
            implements MaterialSuggestionOracle.Suggestion
    {
        private Transaction transaction;

        public Suggest( Transaction transaction )
        {
            this.transaction = transaction;
        }

        @Override
        public String getDisplayString()
        {
            StringBuilder html = new StringBuilder();

            DateTimeFormat format = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM );

            Boolean credit = transaction.isCredit();
            String amount = ( credit ? "+" : "-" ) + NumberFormat.getCurrencyFormat( transaction.getCurrency() ).format( transaction.getAmount() );
            String completedAt = transaction.getCompletedAt() != null ? format.format( transaction.getCompletedAt() ) : "";
            String counterParty = transaction.getMerchant() != null ? transaction.getMerchant().getName() : "";

            html.append( counterParty );
            html.append( " [" );
            html.append( amount );
            html.append( "] ---> " );
            html.append( completedAt );

            return html.toString();
        }

        @Override
        public String getReplacementString()
        {
            return transaction.getTransactionId().toString();
        }

        public Transaction getTransaction()
        {
            return transaction;
        }
    }
}
