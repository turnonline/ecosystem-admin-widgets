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

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.AppEventBus;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import biz.turnonline.ecosystem.widget.shared.rest.payment.TransactionCategory;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.bubble.MaterialBubble;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnTransactionCategories
        extends WidgetColumn<Transaction, MaterialColumn>
{
    private final AppEventBus eventBus;

    protected AppMessages messages = AppMessages.INSTANCE;

    public ColumnTransactionCategories( EventBus eventBus )
    {
        this.eventBus = ( AppEventBus ) eventBus;
    }

    @Override
    public MaterialColumn getValue( Transaction object )
    {
        MaterialColumn content = new MaterialColumn();

        MaterialBubble bubble = new MaterialBubble();
        bubble.setBackgroundColor( Color.WHITE );
        bubble.setPadding( 0 );
        bubble.setVisible( false );
        bubble.getElement().getStyle().setMarginTop( 14, Style.Unit.PX );
        bubble.setShadow( 0 );
        bubble.setOpacity( 0.6 );
        content.add( bubble );

        MaterialContainer categoriesContainer = new MaterialContainer();
        content.add( categoriesContainer );

        List<TransactionCategory> categories = object.getCategories();
        if ( categories != null )
        {
            categories.forEach( category -> {
                MaterialContainer container = new MaterialContainer();
                container.setMarginRight( 5 );
                container.getElement().getStyle().setDisplay( Style.Display.INLINE_BLOCK );
                categoriesContainer.add( container );

                CategoryBadge badge = new CategoryBadge( category );
                container.add( badge );
            } );
        }
        else
        {
            InlineHTML child = new InlineHTML( "&nbsp;" );
            child.getElement().getStyle().setPadding( 10, Style.Unit.PX );
            child.getElement().getStyle().setDisplay( Style.Display.BLOCK );
            categoriesContainer.add( child );
        }

        content.addMouseOverHandler( event -> showCategories( event, object, categoriesContainer, bubble ) );
        content.addMouseOutHandler( event -> hideCategories( categoriesContainer, bubble ) );

        return content;
    }

    private void hideCategories( MaterialContainer categories, MaterialBubble bubble )
    {
        bubble.setVisible( false );
        categories.setVisible( true );
    }

    private void showCategories( MouseOverEvent event, Transaction value, MaterialContainer categories, MaterialBubble bubble )
    {
        if ( !event.getNativeEvent().getCtrlKey() )
        {
            return;
        }

        eventBus.paymentProcessor().getCategoriesForTransaction( value.getTransactionId(), response -> {
            bubble.clear();

            response.getItems().forEach( category -> {
                MaterialContainer container = new MaterialContainer();
                bubble.add( container );

                CategoryBadge badge = new CategoryBadge( category );
                container.add( badge );
            } );

            if ( !response.getItems().isEmpty() )
            {
                bubble.setVisible( true );
                categories.setVisible( false );
            }
        } );
    }
}
