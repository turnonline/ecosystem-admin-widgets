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

package biz.turnonline.ecosystem.widget.purchase.presenter;

import biz.turnonline.ecosystem.widget.purchase.event.BackCategoryEvent;
import biz.turnonline.ecosystem.widget.purchase.event.DeleteCategoryEvent;
import biz.turnonline.ecosystem.widget.purchase.event.SaveCategoryEvent;
import biz.turnonline.ecosystem.widget.purchase.place.Categories;
import biz.turnonline.ecosystem.widget.purchase.place.EditCategory;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.SuccessCallback;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Category;
import biz.turnonline.ecosystem.widget.shared.rest.payment.CategoryFilter;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class EditCategoryPresenter
        extends Presenter<EditCategoryPresenter.IView>
{
    @Inject
    public EditCategoryPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( EditCategory.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( BackCategoryEvent.TYPE, event -> controller().goTo( new Categories() ) );
        bus().addHandler( SaveCategoryEvent.TYPE, this::save );
        bus().addHandler( DeleteCategoryEvent.TYPE, this::delete );
    }

    @Override
    public void onBackingObject()
    {
        view().setModel( newCategory() );

        EditCategory where = ( EditCategory ) controller().getWhere();
        if ( where.getId() != null )
        {
            bus().paymentProcessor().findCategoryById( where.getId(),
                    ( SuccessCallback<Category> ) response -> view().setModel( response ) );
        }

        onAfterBackingObject();
    }

    private Category newCategory()
    {
        CategoryFilter filter = new CategoryFilter();
        List<CategoryFilter> filters = new ArrayList<>();
        filters.add( filter );

        Category category = new Category();
        category.setPropagate( true );
        category.setColor( view().randomizeColor() );
        category.setFilters( filters );

        return category;
    }

    private void save( SaveCategoryEvent event )
    {
        Category category = event.getCategory();

        if ( category.getId() == null )
        {
            bus().paymentProcessor().createCategory( category, ( SuccessCallback<Category> ) response -> {
                success( messages.msgRecordCreated() );
                view().setModel( response );
            } );
        }
        else
        {
            bus().paymentProcessor().updateCategory( category.getId(), category,
                    ( SuccessCallback<Category> ) response -> success( messages.msgRecordUpdated() ) );
        }
    }

    private void delete( DeleteCategoryEvent event )
    {
        Category category = event.getCategory();

        bus().paymentProcessor().deleteCategory( category.getId(),
                ( SuccessCallback<Void> ) response -> {
                    success( messages.msgRecordDeleted( category.getName() ) );
                    controller().goTo( new Categories() );
                } );
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<Category>
    {
        String randomizeColor();
    }
}