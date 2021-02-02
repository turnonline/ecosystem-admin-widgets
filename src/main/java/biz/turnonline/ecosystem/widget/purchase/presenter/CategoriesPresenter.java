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

package biz.turnonline.ecosystem.widget.purchase.presenter;

import biz.turnonline.ecosystem.widget.purchase.event.BackTransactionEvent;
import biz.turnonline.ecosystem.widget.purchase.event.CategoriesEvent;
import biz.turnonline.ecosystem.widget.purchase.event.EditCategoryEvent;
import biz.turnonline.ecosystem.widget.purchase.place.Categories;
import biz.turnonline.ecosystem.widget.purchase.place.EditCategory;
import biz.turnonline.ecosystem.widget.purchase.place.Transactions;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Category;
import com.google.gwt.place.shared.PlaceController;

import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class CategoriesPresenter
        extends Presenter<CategoriesPresenter.IView>
{
    @Inject
    public CategoriesPresenter( IView view, PlaceController placeController )
    {
        super( view, placeController );
        setPlace( Categories.class );
    }

    @Override
    public void bind()
    {
        bus().addHandler( BackTransactionEvent.TYPE, event -> controller().goTo( new Transactions() ) );
        bus().addHandler( CategoriesEvent.TYPE, event -> controller().goTo( new Categories() ) );

        bus().addHandler( EditCategoryEvent.TYPE, event -> {
            controller().goTo( new EditCategory( event.getCategoryId() ) );
        } );
    }

    @Override
    public void onBackingObject()
    {
        onAfterBackingObject();
    }

    public interface IView
            extends org.ctoolkit.gwt.client.view.IView<List<Category>>
    {
        void refresh();
    }
}