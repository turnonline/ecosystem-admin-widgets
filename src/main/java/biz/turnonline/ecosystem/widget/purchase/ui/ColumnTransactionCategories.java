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
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.addins.client.bubble.MaterialBubble;
import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.Position;
import gwt.material.design.client.ui.MaterialButton;
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
        this.eventBus = (AppEventBus)eventBus;
    }

    @Override
    public MaterialColumn getValue( Transaction object )
    {
        MaterialColumn content = new MaterialColumn();

        MaterialBubble bubble = new MaterialBubble();
        bubble.setFloat( Style.Float.LEFT );
        bubble.setPosition( Position.LEFT );
        bubble.setBackgroundColor( Color.WHITE );
        bubble.setVisible( false );
        bubble.getElement().getStyle().setMarginTop( 0, Style.Unit.PX );
        bubble.getElement().getStyle().setPosition( Style.Position.ABSOLUTE );
        bubble.addMouseOutHandler( event -> bubble.setVisible( false ) );
        content.add( bubble );

        content.add( getBtnResolve( object, bubble ) );

        List<TransactionCategory> categories = object.getCategories();
        if ( categories != null )
        {
            categories.forEach( category -> {
                MaterialContainer container = new MaterialContainer();
                container.setMarginBottom( 5 );
                container.getElement().getStyle().setDisplay( Style.Display.INLINE_BLOCK );
                content.add( container );

                CategoryBadge badge = new CategoryBadge(category);
                container.add( badge );
            } );
        }

        return content;
    }

    private MaterialButton getBtnResolve( Transaction value, MaterialBubble bubble )
    {
        MaterialButton btnCheckCategories = new MaterialButton();
        btnCheckCategories.addMouseOverHandler( event -> {
            event.stopPropagation();
            eventBus.paymentProcessor().getCategoriesForTransaction( value.getTransactionId(), response -> {
                bubble.clear();

                response.getItems().forEach( category -> {
                    MaterialContainer container = new MaterialContainer();
                    bubble.add( container );

                    CategoryBadge badge = new CategoryBadge( category );
                    container.add( badge );
                } );

                if (!response.getItems().isEmpty())
                {
                    bubble.setVisible( true );
                }
            } );
        } );

        btnCheckCategories.setType( ButtonType.FLAT );

        btnCheckCategories.setIconType( IconType.YOUTUBE_SEARCHED_FOR );
        btnCheckCategories.setIconColor( Color.ORANGE );
        btnCheckCategories.setSize( ButtonSize.MEDIUM );
        btnCheckCategories.setPadding( 0 );

        btnCheckCategories.setTooltip( messages.tooltipCategoryResolve() );

        return btnCheckCategories;
    }
}
