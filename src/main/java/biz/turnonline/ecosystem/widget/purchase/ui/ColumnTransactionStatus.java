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

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import biz.turnonline.ecosystem.widget.shared.rest.payment.TransactionStatus;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnTransactionStatus
        extends WidgetColumn<Transaction, MaterialChip>
{
    private static final AppMessages messages = AppMessages.INSTANCE;

    private static final Map<String, Color> colorMap = new HashMap<>();
    private static final Map<String, IconType> iconMap = new HashMap<>();
    private static final Map<String, String> localizationMap = new HashMap<>();

    static
    {
        colorMap.put( TransactionStatus.CREATED.name(), Color.BLUE );
        colorMap.put( TransactionStatus.PENDING.name(), Color.GREY );
        colorMap.put( TransactionStatus.COMPLETED.name(), Color.GREEN );
        colorMap.put( TransactionStatus.DECLINED.name(), Color.YELLOW );
        colorMap.put( TransactionStatus.FAILED.name(), Color.RED );
        colorMap.put( TransactionStatus.REVERTED.name(), Color.ORANGE );

        iconMap.put( TransactionStatus.CREATED.name(), IconType.INSERT_DRIVE_FILE );
        iconMap.put( TransactionStatus.PENDING.name(), IconType.PAUSE );
        iconMap.put( TransactionStatus.COMPLETED.name(), IconType.CHECK );
        iconMap.put( TransactionStatus.DECLINED.name(), IconType.WARNING );
        iconMap.put( TransactionStatus.FAILED.name(), IconType.ERROR_OUTLINE );
        iconMap.put( TransactionStatus.REVERTED.name(), IconType.UNDO );

        localizationMap.put( TransactionStatus.CREATED.name(), messages.labelTransactionStatusCreated() );
        localizationMap.put( TransactionStatus.PENDING.name(), messages.labelTransactionStatusPending() );
        localizationMap.put( TransactionStatus.COMPLETED.name(), messages.labelTransactionStatusCompleted() );
        localizationMap.put( TransactionStatus.DECLINED.name(), messages.labelTransactionStatusDeclined() );
        localizationMap.put( TransactionStatus.FAILED.name(), messages.labelTransactionStatusFailed() );
        localizationMap.put( TransactionStatus.REVERTED.name(), messages.labelTransactionStatusReverted() );
    }

    @Override
    public MaterialChip getValue( Transaction object )
    {
        String status = object.getStatus();
        String text = localizationMap.get( status );

        MaterialChip badge = new MaterialChip( "" );

        if ( text != null )
        {
            badge.setText( text );
            badge.setTextColor( Color.WHITE );
            badge.setBackgroundColor( colorMap.get( status ) );
            badge.setIconType( iconMap.get( status ) );
            badge.setIconPosition( IconPosition.LEFT );
        }
        else
        {
            badge.setVisible( false );
        }

        return badge;
    }
}
