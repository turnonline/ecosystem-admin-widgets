/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
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

package org.ctoolkit.turnonline.widget.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public interface AppMessages
        extends Messages
{
    public static final AppMessages INSTANCE = GWT.create( AppMessages.class );

    // labels
    @Key( value = "label.new")
    String labelNew();

    @Key( value = "label.edit")
    String labelEdit();

    @Key( value = "label.delete")
    String labelDelete();

    @Key( value = "label.businessName")
    String labelBusinessName();

    @Key( value = "label.companyId")
    String labelCompanyId();

    @Key( value = "label.taxId")
    String labelTaxId();

    @Key( value = "label.vatId")
    String labelVatId();

    @Key( value = "label.vatPayer")
    String labelVatPayer();

    @Key( value = "label.numberOfDays")
    String numberOfDays();

    @Key( value = "label.name")
    String labelName();

    @Key( value = "label.firstName")
    String labelFirstName();

    @Key( value = "label.lastName")
    String labelLastName();

    @Key( value = "label.prefix" )
    String labelPrefix();

    @Key( value = "label.suffix" )
    String labelSuffix();

    @Key( value = "label.phone")
    String labelPhone();

    @Key( value = "label.email")
    String labelEmail();

    @Key( value = "label.ccEmail")
    String labelCcEmail();

    @Key( value = "label.company")
    String labelCompany();

    @Key( value = "label.address")
    String labelAddress();

    @Key( value = "label.home")
    String labelHome();

    @Key( value = "label.contacts")
    String labelContacts();

    @Key( value = "label.editContact")
    String labelEditContact();

    @Key( value = "label.products")
    String labelProducts();

    @Key( value = "label.orders")
    String labelOrders();

    @Key( value = "label.invoices")
    String labelInvoices();

    @Key( value = "label.confirmation")
    String labelConfirmation();

    @Key( value = "label.street")
    String labelStreet();

    @Key( value = "label.city")
    String labelCity();

    @Key( value = "label.postCode")
    String labelPostCode();

    @Key( value = "label.country")
    String labelCountry();

    @Key( value = "label.postalAddressSame")
    String labelPostalAddressSame();

    @Key( value = "label.person")
    String labelPerson();

    @Key( value = "label.invoicing")
    String labelInvoicing();

    @Key( value = "label.invoiceAddress")
    String labelInvoiceAddress();

    @Key( value = "label.postalAddress")
    String labelPostalAddress();

    @Key( value = "label.ok")
    String labelOk();

    @Key( value = "label.cancel")
    String labelCancel();

    @Key( value = "label.save")
    String labelSave();

    @Key( value = "label.back")
    String labelBack();

    // tooltips

    @Key( value = "tooltip.new.contact")
    String tooltipNewContact();

    @Key( value = "tooltip.edit.contact")
    String tooltipEditContact();

    @Key( value = "tooltip.delete.contact")
    String tooltipDeleteContact();

    @Key( value = "tooltip.save.contact")
    String tooltipSaveContact();

    @Key( value = "tooltip.back")
    String tooltipBack();

    @Key( value = "tooltip.addressAutocomplete")
    String tooltipAddressAutocomplete();

    // messages

    @Key( value = "msg.recordDeleted")
    String msgRecordDeleted(String detail);

    @Key( value = "msg.errorRemoteServiceCall")
    String msgErrorRemoteServiceCall();

    @Key( value = "msg.msgConfirmOneRecordDelete")
    String msgConfirmOneRecordDelete(String name);

    @Key( value = "msg.msgConfirmMultipleRecordsDelete")
    String msgConfirmMultipleRecordsDelete(int records);

    @Key( value = "msg.recordCreated")
    String msgRecordCreated();

    @Key( value = "msg.recordUpdated")
    String msgRecordUpdated();
}
