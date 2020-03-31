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
