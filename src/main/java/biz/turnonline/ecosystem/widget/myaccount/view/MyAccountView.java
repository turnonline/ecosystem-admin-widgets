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

package biz.turnonline.ecosystem.widget.myaccount.view;

import biz.turnonline.ecosystem.widget.myaccount.event.SaveAccountEvent;
import biz.turnonline.ecosystem.widget.myaccount.presenter.MyAccountPresenter;
import biz.turnonline.ecosystem.widget.shared.rest.account.Account;
import biz.turnonline.ecosystem.widget.shared.rest.account.AccountBusiness;
import biz.turnonline.ecosystem.widget.shared.ui.LegalFormComboBox;
import biz.turnonline.ecosystem.widget.shared.ui.Route;
import biz.turnonline.ecosystem.widget.shared.ui.ScaffoldBreadcrumb;
import biz.turnonline.ecosystem.widget.shared.view.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;

import javax.inject.Inject;
import javax.inject.Named;

import static gwt.material.design.client.constants.IconType.ACCOUNT_BOX;
import static gwt.material.design.client.constants.IconType.BUSINESS;

/**
 * My account and settings form. Settings visibility is based on the account scope (role).
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class MyAccountView
        extends View<Account>
        implements MyAccountPresenter.IView
{
    private static MyAccountViewUiBinder binder = GWT.create( MyAccountViewUiBinder.class );

    @UiField( provided = true )
    ScaffoldBreadcrumb breadcrumb;

    @UiField
    MaterialLabel email;

    @UiField
    MaterialSwitch company;

    @UiField
    MaterialTitle typeTitle;

    @UiField
    MaterialIcon typeIcon;

    @UiField
    MaterialTextBox businessName;

    @UiField
    LegalFormComboBox legalForm;

    @UiField
    MaterialTextBox companyId;

    @UiField
    MaterialTextBox taxId;

    @UiField
    MaterialTextBox vatId;

    @UiField
    MaterialSwitch vatPayer;

    @Inject
    public MyAccountView( EventBus eventBus, @Named( "MyAccountBreadcrumb" ) ScaffoldBreadcrumb breadcrumb )
    {
        super( eventBus );

        this.breadcrumb = breadcrumb;
        setActive( Route.MY_ACCOUNT );

        add( binder.createAndBindUi( this ) );
    }

    @Override
    protected void afterSetModel()
    {
        Account account = getRawModel();
        AccountBusiness business = account.getBusiness();

        email.setValue( account.getEmail() );
        company.setValue( account.getCompany() );

        if ( business == null )
        {
            businessName.setValue( null );
            legalForm.setSingleValueByCode( null );
            companyId.setValue( null );
            taxId.setValue( null );
            vatId.setValue( null );
        }
        else
        {
            businessName.setValue( business.getBusinessName() );
            legalForm.setSingleValueByCode( business.getLegalForm() );
            companyId.setValue( business.getCompanyId() );
            taxId.setValue( business.getTaxId() );
            vatId.setValue( business.getVatId() );
        }

        handleVatPayer();
        vatPayer.addValueChangeHandler( event -> handleVatPayer() );

        handleAccountTypeTitle();
    }

    private void handleVatPayer()
    {
        vatId.setEnabled( vatPayer.getValue() );
    }

    private void handleAccountTypeTitle()
    {
        Boolean isCompany = company.getValue();
        String prefix = messages.labelAccountType() + ": ";

        if ( isCompany )
        {
            typeTitle.setDescription( prefix + messages.labelCompanyAccount() );
            typeIcon.setIconType( BUSINESS );
        }
        else
        {
            typeTitle.setDescription( prefix + messages.labelPersonalAccount() );
            typeIcon.setIconType( ACCOUNT_BOX );
        }
    }

    @UiHandler( "company" )
    void onCompanyChange( ValueChangeEvent<Boolean> e )
    {
        handleAccountTypeTitle();
    }

    @UiHandler( "btnSave" )
    public void btnSaveClick( ClickEvent event )
    {
        bus().fireEvent( new SaveAccountEvent( getModel() ) );
    }

    interface MyAccountViewUiBinder
            extends UiBinder<HTMLPanel, MyAccountView>
    {
    }
}