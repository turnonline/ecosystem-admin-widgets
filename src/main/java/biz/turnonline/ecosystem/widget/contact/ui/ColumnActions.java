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

package biz.turnonline.ecosystem.widget.contact.ui;

import biz.turnonline.ecosystem.widget.contact.event.EditContactEvent;
import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.account.ContactCard;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ColumnActions
        extends WidgetColumn<ContactCard, MaterialButton>
{
    private final EventBus eventBus;

    protected AppMessages messages = AppMessages.INSTANCE;

    public ColumnActions( EventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    @Override
    public MaterialButton getValue( ContactCard value )
    {
        MaterialButton btnEdit = new MaterialButton();
        btnEdit.addClickHandler( event -> {
            event.stopPropagation();
            eventBus.fireEvent( new EditContactEvent( value ) );
        } );

        btnEdit.setType( ButtonType.FLOATING );
        btnEdit.setBackgroundColor( Color.BLUE );

        btnEdit.setIconType( IconType.EDIT );
        btnEdit.setIconColor( Color.WHITE );
        btnEdit.setWaves( WavesType.DEFAULT );
        btnEdit.setSize( ButtonSize.MEDIUM );

        btnEdit.setTooltip( messages.tooltipEditContact() );

        return btnEdit;
    }
}
