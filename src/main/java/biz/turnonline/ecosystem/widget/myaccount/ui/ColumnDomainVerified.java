/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
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

package biz.turnonline.ecosystem.widget.myaccount.ui;

import biz.turnonline.ecosystem.widget.shared.rest.account.Domain;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

/**
 * Column value renderer as an icon.
 * Either a red cross or green check based on the value of {@link Domain#getVerified()}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ColumnDomainVerified
        extends WidgetColumn<Domain, MaterialIcon>
{
    @Override
    public MaterialIcon getValue( Domain domain )
    {
        boolean verified = domain.getVerified() == null ? false : domain.getVerified();

        MaterialIcon icon = new MaterialIcon();
        icon.setIconType( verified ? IconType.CHECK : IconType.CLOSE );
        icon.setTextColor( verified ? Color.GREEN : Color.RED_DARKEN_1 );

        return icon;
    }
}
