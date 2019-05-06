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

package biz.turnonline.ecosystem.widget.shared;

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

    @Key( value = "label.add")
    String labelAdd();

    @Key( value = "label.delete")
    String labelDelete();

    @Key( value = "label.businessName")
    String labelBusinessName();

    @Key( value = "label.id")
    String labelId();

    @Key( value = "label.customer")
    String labelCustomer();

    @Key( value = "label.items")
    String labelItems();

    @Key( value = "label.transactions")
    String labelTransactions();

    @Key( value = "label.payment")
    String labelPayment();

    @Key( value = "label.status")
    String labelStatus();

    @Key( value = "label.companyId")
    String labelCompanyId();

    @Key( value = "label.taxId")
    String labelTaxId();

    @Key( value = "label.vatId")
    String labelVatId();

    @Key( value = "label.vatPayer")
    String labelVatPayer();

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

    @Key( value = "label.editProduct")
    String labelEditProduct();

    @Key( value = "label.orders")
    String labelOrders();

    @Key( value = "label.editOrder")
    String labelEditOrder();

    @Key( value = "label.invoices")
    String labelInvoices();

    @Key( value = "label.editInvoice")
    String labelEditInvoice();

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

    @Key( value = "label.detail")
    String labelDetail();

    @Key( value = "label.content")
    String labelContent();

    @Key( value = "label.publishing")
    String labelPublishing();

    @Key( value = "label.pricing")
    String labelPricing();

    @Key( value = "label.event")
    String labelEvent();

    @Key( value = "label.invoiceAddress")
    String labelInvoiceAddress();

    @Key( value = "label.postalAddress")
    String labelPostalAddress();

    @Key( value = "label.price")
    String labelPrice();

    @Key( value = "label.priceExclusiveVat")
    String labelPriceExclusiveVat();

    @Key( value = "label.currency")
    String labelCurrency();

    @Key( value = "label.description")
    String labelDescription();

    @Key( value = "label.itemName")
    String labelItemName();

    @Key( value = "label.amount")
    String labelAmount();

    @Key( value = "label.unit")
    String labelUnit();

    @Key( value = "label.snippet")
    String labelSnippet();

    @Key( value = "label.metaFields")
    String labelMetaFields();

    @Key( value = "label.availableFields")
    String labelAvailableFields();

    @Key( value = "label.mandatoryFields")
    String labelMandatoryFields();

    @Key( value = "label.pictures")
    String labelPictures();

    @Key( value = "label.settings")
    String labelSettings();

    @Key( value = "label.comingSoon")
    String labelComingSoon();

    @Key( value = "label.published")
    String labelPublished();

    @Key( value = "label.social")
    String labelSocial();

    @Key( value = "label.facebookLike")
    String labelFacebookLike();

    @Key( value = "label.googlePlus")
    String labelGooglePlus();

    @Key( value = "label.linkedInShare")
    String labelLinkedInShare();

    @Key( value = "label.domain")
    String labelDomain();

    @Key( value = "label.domainName")
    String labelDomainName();

    @Key( value = "label.domainUri")
    String labelDomainUri();

    @Key( value = "label.discountCode")
    String labelDiscountCode();

    @Key( value = "label.allChildrenCheckedIn")
    String labelAllChildrenCheckedIn();

    @Key( value = "label.percentage")
    String labelPercentage();

    @Key( value = "label.fixed")
    String labelFixed();

    @Key( value = "label.value")
    String labelValue();

    @Key( value = "label.discountType")
    String labelDiscountType();

    @Key( value = "label.discountRule")
    String labelDiscountRule();

    @Key( value = "label.codes")
    String labelCodes();

    @Key( value = "label.priceDefinition")
    String labelPriceDefinition();

    @Key( value = "label.vatDefinition")
    String labelVatDefinition();

    @Key( value = "label.discounts")
    String labelDiscounts();

    @Key( value = "label.vat")
    String labelVat();

    @Key( value = "label.vatEU")
    String labelVatEU();

    @Key( value = "label.vatNonEU")
    String labelVatNonEU();

    @Key( value = "label.domesticDelivery")
    String labelDomesticDelivery();

    @Key( value = "label.deadline")
    String labelDeadline();

    @Key( value = "label.seats")
    String labelSeats();

    @Key( value = "label.begin")
    String labelBegin();

    @Key( value = "label.end")
    String labelEnd();

    @Key( value = "label.on")
    String labelOn();

    @Key( value = "label.from")
    String labelFrom();

    @Key( value = "label.to")
    String labelTo();

    @Key( value = "label.show")
    String labelShow();

    @Key( value = "label.location")
    String labelLocation();

    @Key( value = "label.annually")
    String labelAnnually();

    @Key( value = "label.semiAnnually")
    String labelSemiAnnually();

    @Key( value = "label.quarterly")
    String labelQuarterly();

    @Key( value = "label.monthly")
    String labelMonthly();

    @Key( value = "label.weekly")
    String labelWeekly();

    @Key( value = "label.manually")
    String labelManually();

    @Key( value = "label.proforma")
    String labelProforma();

    @Key( value = "label.taxDocument")
    String labelTaxDocument();

    @Key( value = "label.beginAt")
    String labelBeginAt();

    @Key( value = "label.numberOfDays")
    String labelNumberOfDays();

    @Key( value = "label.periodicity")
    String labelPeriodicity();

    @Key( value = "label.invoiceType")
    String labelInvoiceType();

    @Key( value = "label.invoiceNumber")
    String labelInvoiceNumber();

    @Key( value = "label.variableSymbol")
    String labelVariableSymbol();

    @Key( value = "label.dates")
    String labelDates();

    @Key( value = "label.dateOfIssue")
    String labelDateOfIssue();

    @Key( value = "label.dateOfTaxable")
    String labelDateOfTaxable();

    @Key( value = "label.dueDate")
    String labelDueDate();

    @Key( value = "label.paymentMethod")
    String labelPaymentMethod();

    @Key( value = "label.bankTransfer")
    String labelBankTransfer();

    @Key( value = "label.cash")
    String labelCash();

    @Key( value = "label.creditCard")
    String labelCreditCard();

    @Key( value = "label.debitCard")
    String labelDebitCard();

    @Key( value = "label.texts")
    String labelTexts();

    @Key( value = "label.finalText")
    String labelFinalText();

    @Key( value = "label.introductoryText")
    String labelIntroductoryText();

    @Key( value = "label.select")
    String labelSelect();

    @Key( value = "label.selectCustomer")
    String labelSelectCustomer();

    @Key( value = "label.selectProduct")
    String labelSelectProduct();

    @Key( value = "label.selectedProduct")
    String labelSelectedProduct(String product);

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
    
    @Key( value = "tooltip.new.product")
    String tooltipNewProduct();

    @Key( value = "tooltip.edit.product")
    String tooltipEditProduct();

    @Key( value = "tooltip.delete.product")
    String tooltipDeleteProduct();

    @Key( value = "tooltip.save.product")
    String tooltipSaveProduct();

    @Key( value = "tooltip.new.order")
    String tooltipNewOrder();

    @Key( value = "tooltip.edit.order")
    String tooltipEditOrder();

    @Key( value = "tooltip.delete.order")
    String tooltipDeleteOrder();

    @Key( value = "tooltip.save.order")
    String tooltipSaveOrder();

    @Key( value = "tooltip.new.invoice")
    String tooltipNewInvoice();

    @Key( value = "tooltip.edit.invoice")
    String tooltipEditInvoice();

    @Key( value = "tooltip.delete.invoice")
    String tooltipDeleteInvoice();

    @Key( value = "tooltip.save.invoice")
    String tooltipSaveInvoice();

    @Key( value = "tooltip.back")
    String tooltipBack();

    @Key( value = "tooltip.addressAutocomplete")
    String tooltipAddressAutocomplete();

    @Key( value = "tooltip.availableFields")
    String tooltipAvailableFields();

    @Key( value = "tooltip.pictures")
    String tooltipPictures();

    @Key( value = "tooltip.productAutocomplete")
    String tooltipProductAutocomplete();

    @Key( value = "tooltip.contactAutocomplete")
    String tooltipContactAutocomplete();

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

    // other

    @Key( value = "copyright")
    String copyright();
}