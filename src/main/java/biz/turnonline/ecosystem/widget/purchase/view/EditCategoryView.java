/*
 * Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.turnonline.ecosystem.widget.purchase.view;

import biz.turnonline.ecosystem.widget.purchase.event.BackCategoryEvent;
import biz.turnonline.ecosystem.widget.purchase.event.DeleteCategoryEvent;
import biz.turnonline.ecosystem.widget.purchase.event.SaveCategoryEvent;
import biz.turnonline.ecosystem.widget.purchase.presenter.EditCategoryPresenter;
import biz.turnonline.ecosystem.widget.purchase.ui.CategoryFilters;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Category;
import biz.turnonline.ecosystem.widget.shared.rest.payment.CategoryFilter;
import biz.turnonline.ecosystem.widget.shared.ui.ConfirmationWindow;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Random;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditCategoryView
        extends View<Category>
        implements EditCategoryPresenter.IView
{
    private static final EditCategoryViewUiBinder binder = GWT.create( EditCategoryViewUiBinder.class );

    private static final Random random = new Random();

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    ConfirmationWindow confirmationDelete;

    @UiField
    MaterialButton btnSave;

    @UiField
    MaterialButton btnBack;

    @UiField
    MaterialAnchorButton deleteCategory;

    @UiField
    MaterialTextBox name;

    @UiField
    MaterialTextBox color;

    @UiField
    MaterialSwitch propagate;

    @UiField
    CategoryFilters filters;

    @UiField
    MaterialLink addFilter;

    @Inject
    public EditCategoryView( @Named( "EditCategoryBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super();

        this.breadcrumb = breadcrumb;

        setActive( Route.TRANSACTIONS );

        add( binder.createAndBindUi( this ) );

        confirmationDelete.getBtnOk().addClickHandler( event -> bus().fireEvent( new DeleteCategoryEvent( getRawModel() ) ) );

        color.addKeyUpHandler( event -> colorChange( color.getValue() ) );
        color.addValueChangeHandler( event -> colorChange( color.getValue() ) );

        name.getIcon().addClickHandler( event -> {
            String colorHex = randomizeColor();

            colorChange( colorHex );
            color.setValue( colorHex );
        } );
    }

    @Override
    protected void beforeGetModel( Category category )
    {
        category.setName( name.getValue() );
        category.setColor( color.getValue() );
        category.setPropagate( propagate.getValue() );

        category.setFilters( filters.getValue() );
    }

    @Override
    protected void afterSetModel( Category category )
    {
        name.setValue( category.getName() );
        color.setValue( category.getColor() );
        propagate.setValue( category.isPropagate() );

        filters.setValue( category.getFilters() );

        colorChange( color.getValue() );
        deleteCategory.setEnabled( category.getId() != null );
    }

    @UiHandler( "btnBack" )
    public void handleBack( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new BackCategoryEvent() );
    }

    @UiHandler( "btnSave" )
    public void handleSave( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        bus().fireEvent( new SaveCategoryEvent( getModel() ) );
    }

    @UiHandler( "deleteCategory" )
    public void deleteCategory( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        confirmationDelete.open( AppMessages.INSTANCE.questionDeleteRecord() );
    }

    @UiHandler( "addFilter" )
    public void add( @SuppressWarnings( "unused" ) ClickEvent event )
    {
        CategoryFilter filter = new CategoryFilter();
        filters.addRow( filter );
    }

    private void colorChange( String colorHex )
    {
        name.getIcon().getElement().setAttribute( "style", "cursor: pointer; color: " + colorHex + " !important" );
    }

    @Override
    public String randomizeColor()
    {
        return "#" + nextCode();
    }

    private String nextCode()
    {
        String code = "" + random.nextInt( 0xFFFFF );
        if ( code.length() != 6 )
        {
            return nextCode();
        }

        return code;
    }

    interface EditCategoryViewUiBinder
            extends UiBinder<HTMLPanel, EditCategoryView>
    {
    }
}