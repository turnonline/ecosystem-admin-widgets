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
