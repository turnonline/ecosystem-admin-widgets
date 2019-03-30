/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
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

package org.ctoolkit.turnonline.widget.product.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialButton;
import org.ctoolkit.turnonline.widget.product.AppEventBus;
import org.ctoolkit.turnonline.widget.product.event.DeleteProductEvent;
import org.ctoolkit.turnonline.widget.product.event.EditProductEvent;
import org.ctoolkit.turnonline.widget.product.presenter.ProductsPresenter;
import org.ctoolkit.turnonline.widget.product.ui.ColumnActions;
import org.ctoolkit.turnonline.widget.product.ui.ColumnName;
import org.ctoolkit.turnonline.widget.product.ui.ColumnPicture;
import org.ctoolkit.turnonline.widget.product.ui.ColumnPrice;
import org.ctoolkit.turnonline.widget.product.ui.ColumnPublished;
import org.ctoolkit.turnonline.widget.product.ui.ProductsDataSource;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;
import org.ctoolkit.turnonline.widget.shared.ui.ConfirmationWindow;
import org.ctoolkit.turnonline.widget.shared.ui.ConfirmationWindow.Question;
import org.ctoolkit.turnonline.widget.shared.ui.Route;
import org.ctoolkit.turnonline.widget.shared.ui.ScaffoldBreadcrumb;
import org.ctoolkit.turnonline.widget.shared.ui.SmartTable;
import org.ctoolkit.turnonline.widget.shared.util.Formatter;
import org.ctoolkit.turnonline.widget.shared.view.View;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ProductsView
        extends View
        implements ProductsPresenter.IView
{
    private static ProductsViewUiBinder binder = GWT.create( ProductsViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    MaterialButton btnNew;

    @UiField
    MaterialButton btnDelete;

    @UiField
    SmartTable<Product> table;

    @UiField
    ConfirmationWindow confirmationWindow;

    @Override
    public void refresh()
    {
        table.refresh();
    }

    interface ProductsViewUiBinder
            extends UiBinder<HTMLPanel, ProductsView>
    {
    }

    @Inject
    public ProductsView( EventBus eventBus, @Named( "ProductsBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super( eventBus );

        this.breadcrumb = breadcrumb;
        scaffoldNavBar.setActive( Route.PRODUCTS );

        add( binder.createAndBindUi( this ) );
        initTable();

        confirmationWindow.getBtnOk().addClickHandler( event -> {
            List<Product> selectedRowModels = table.getSelectedRowModels( false );
            bus().fireEvent( new DeleteProductEvent( selectedRowModels ) );
        } );
    }

    private void initTable()
    {
        ColumnPicture picture = new ColumnPicture();
        picture.setWidth( "5%" );

        ColumnPublished published = new ColumnPublished();
        published.setWidth( "5%" );

        ColumnName name = new ColumnName();
        name.setWidth( "50%" );

        ColumnPrice price = new ColumnPrice();
        price.setWidth( "30%" );

        ColumnActions actions = new ColumnActions( bus() );
        actions.setWidth( "5%" );

        table.addColumn( picture );
        table.addColumn( published );
        table.addColumn( name, messages.labelName() );
        table.addColumn( price, messages.labelPriceExclusiveVat() );
        table.addColumn( actions );

        table.configure( new ProductsDataSource( ( AppEventBus ) bus() ) );
    }

    @UiHandler( "btnNew" )
    public void handleNew( ClickEvent event )
    {
        bus().fireEvent( new EditProductEvent() );
    }

    @UiHandler( "btnDelete" )
    public void handleDelete( ClickEvent event )
    {
        List<Product> selected = table.getSelectedRowModels( false );
        if ( !selected.isEmpty() )
        {
            confirmationWindow.open( new Question()
            {
                @Override
                public int selectedRecords()
                {
                    return selected.size();
                }

                @Override
                public String name()
                {
                    return Formatter.formatProductName( selected.get( 0 ) );
                }
            } );
        }
    }
}