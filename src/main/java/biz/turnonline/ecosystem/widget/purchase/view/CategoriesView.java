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

package biz.turnonline.ecosystem.widget.purchase.view;

import biz.turnonline.ecosystem.widget.purchase.event.BackTransactionEvent;
import biz.turnonline.ecosystem.widget.purchase.event.EditCategoryEvent;
import biz.turnonline.ecosystem.widget.purchase.presenter.CategoriesPresenter;
import biz.turnonline.ecosystem.widget.purchase.ui.CategoriesDataSource;
import biz.turnonline.ecosystem.widget.purchase.ui.ColumnCategoriesActions;
import biz.turnonline.ecosystem.widget.purchase.ui.ColumnCategoryFilters;
import biz.turnonline.ecosystem.widget.purchase.ui.ColumnCategoryName;
import biz.turnonline.ecosystem.widget.purchase.ui.ColumnCategoryPropagate;
import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Category;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.ui.SmartTable;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class CategoriesView
        extends View<List<Category>>
        implements CategoriesPresenter.IView
{
    private static final CategoriesViewUiBinder binder = GWT.create( CategoriesViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    SmartTable<Category> table;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialAnchorButton newCategory;

    @Inject
    public CategoriesView( @Named( "CategoriesBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;
        setActive( Route.TRANSACTIONS );

        add( binder.createAndBindUi( this ) );
        initTable();

        // refresh action setup
        breadcrumb.setRefreshTooltip( messages.tooltipCategoriesListRefresh() );
        breadcrumb.setNavSectionVisible( true );
        breadcrumb.addRefreshClickHandler( event -> refresh() );

        breadcrumb.setClearFilterVisible( false );
    }

    @Override
    public void refresh()
    {
        table.refresh();
    }

    private void initTable()
    {
        ColumnCategoryName name = new ColumnCategoryName();
        name.width( "35%" );

        ColumnCategoryFilters filters = new ColumnCategoryFilters();
        filters.width("50%");

        ColumnCategoryPropagate propagate = new ColumnCategoryPropagate();
        propagate.width( "10%" );

        ColumnCategoriesActions actions = new ColumnCategoriesActions( bus() );
        actions.width("5%");

        table.addColumn( messages.labelName(), name );
        table.addColumn( messages.labelFilters(), filters );
        table.addColumn( messages.labelPropagate(), propagate );
        table.addColumn( actions );

        table.configure( new CategoriesDataSource( ( AppEventBus ) bus() ) );
        table.getPager().setVisible( false );
    }

    @UiHandler( "btnBack" )
    public void handleBack( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new BackTransactionEvent() );
    }

    @UiHandler( "newCategory" )
    public void handleNew( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new EditCategoryEvent() );
    }

    interface CategoriesViewUiBinder
            extends UiBinder<HTMLPanel, CategoriesView>
    {
    }
}