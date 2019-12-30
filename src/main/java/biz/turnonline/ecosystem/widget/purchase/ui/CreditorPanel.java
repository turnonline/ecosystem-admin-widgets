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

package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Creditor;
import biz.turnonline.ecosystem.widget.shared.rest.billing.CreditorContactDetails;
import biz.turnonline.ecosystem.widget.shared.ui.CountryComboBox;
import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.addins.client.inputmask.MaterialInputMask;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nullable;

/**
 * Creditor details read only form panel.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class CreditorPanel
        extends Composite
{
    private static CustomerPanelUiBinder binder = GWT.create( CustomerPanelUiBinder.class );

    @UiField
    MaterialTextBox businessName;

    @UiField
    MaterialTextBox companyId;

    @UiField
    MaterialTextBox taxId;

    @UiField
    MaterialTextBox vatId;

    @UiField
    MaterialTextBox contactName;

    @UiField
    MaterialTextBox phone;

    @UiField
    MaterialTextBox email;

    @UiField
    MaterialTextBox street;

    @UiField
    MaterialTextBox city;

    @UiField
    MaterialInputMask<String> postCode;

    @UiField
    CountryComboBox country;

    public CreditorPanel()
    {
        initWidget( binder.createAndBindUi( this ) );

        businessName.setReadOnly( true );
        companyId.setReadOnly( true );
        taxId.setReadOnly( true );
        vatId.setReadOnly( true );
        contactName.setReadOnly( true );
        phone.setReadOnly( true );
        email.setReadOnly( true );
        street.setReadOnly( true );
        city.setReadOnly( true );
        postCode.setReadOnly( true );
        country.setReadOnly( true );
    }

    public void fill( @Nullable Creditor creditor )
    {
        if ( creditor == null )
        {
            creditor = new Creditor();
        }

        // company details
        businessName.setValue( creditor.getBusinessName() );
        companyId.setValue( creditor.getCompanyId() );
        vatId.setValue( creditor.getVatId() );
        taxId.setValue( creditor.getTaxId() );

        // public contact
        CreditorContactDetails contact = creditor.getContact();
        contactName.setVisible( true );

        if ( contact != null )
        {
            String publicName = contact.getName();
            contactName.setValue( publicName );
            phone.setValue( contact.getPhone() );
            email.setValue( contact.getEmail() );

            if ( Strings.isNullOrEmpty( publicName ) )
            {
                contactName.setVisible( false );
            }
        }

        // billing address
        street.setValue( creditor.getStreet() );
        city.setValue( creditor.getCity() );
        postCode.setValue( creditor.getPostcode() );
        postCode.reload();
        country.setSingleValueByCode( creditor.getCountry() );
    }

    interface CustomerPanelUiBinder
            extends UiBinder<HTMLPanel, CreditorPanel>
    {
    }
}
