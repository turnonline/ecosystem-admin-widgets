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

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.billing.Transaction;
import biz.turnonline.ecosystem.widget.shared.util.Router;
import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import static biz.turnonline.ecosystem.widget.shared.util.Router.Target.NEW_WINDOW;

/**
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnTransactionActions
        extends WidgetColumn<Transaction, MaterialButton>
{
    protected AppMessages messages = AppMessages.INSTANCE;

    @Override
    public MaterialButton getValue( Transaction value )
    {
        MaterialButton btnTransactionDetail = new MaterialButton();
        btnTransactionDetail.addClickHandler( event -> {
            event.stopPropagation();

            Router.routeToDetail( Route.TRANSACTIONS,
                    new String[]{value.getTransactionId().toString()},
                    NEW_WINDOW );
        } );

        btnTransactionDetail.setType( ButtonType.FLOATING );
        btnTransactionDetail.setBackgroundColor( Color.BLUE );

        btnTransactionDetail.setIconType( IconType.VISIBILITY );
        btnTransactionDetail.setIconColor( Color.WHITE );
        btnTransactionDetail.setWaves( WavesType.DEFAULT );
        btnTransactionDetail.setSize( ButtonSize.MEDIUM );

        btnTransactionDetail.setTooltip( messages.tooltipTransactionDetail() );

        return btnTransactionDetail;
    }
}
