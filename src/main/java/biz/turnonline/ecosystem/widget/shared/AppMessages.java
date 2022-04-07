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

package biz.turnonline.ecosystem.widget.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public interface AppMessages
        extends Messages
{
    AppMessages INSTANCE = GWT.create( AppMessages.class );

    // labels
    @Key( "label.appName" )
    String labelAppName();

    @Key( value = "label.logout" )
    String labelLogout();

    @Key( value = "label.accountEmail" )
    String labelAccountEmail();

    @Key( value = "label.contactEmail" )
    String labelContactEmail();

    @Key( value = "label.accountType" )
    String labelAccountType();

    @Key( value = "label.publicContact" )
    String labelPublicContact();

    @Key( value = "label.new" )
    String labelNew();

    @Key( value = "label.edit" )
    String labelEdit();

    @Key( value = "label.add" )
    String labelAdd();

    @Key( value = "label.import" )
    String labelImport();

    @Key( value = "label.delete" )
    String labelDelete();

    @Key( "label.companyBasicInfo" )
    String labelCompanyBasicInfo();

    @Key( value = "label.businessName" )
    String labelBusinessName();

    @Key( value = "label.legalForm" )
    String labelLegalForm();

    @Key( value = "label.id" )
    String labelId();

    @Key( value = "label.customer" )
    String labelCustomer();

    @Key( "label.supplier" )
    String labelSupplier();

    @Key( value = "label.items" )
    String labelItems();

    @Key( "label.order" )
    String labelOrder();

    @Key( "label.orderItems" )
    String labelOrderItems();

    @Key( value = "label.transactions" )
    String labelTransactions();

    @Key( value = "label.transactionDetail" )
    String labelTransactionDetail();

    @Key( value = "label.categories" )
    String labelCategories();

    @Key( value = "label.category" )
    String labelCategory();

    @Key( value = "label.payment" )
    String labelPayment();

    @Key( value = "label.counterparty" )
    String labelCounterparty();

    @Key( value = "label.status" )
    String labelStatus();

    @Key( value = "label.companyId" )
    String labelCompanyId();

    @Key( value = "label.taxId" )
    String labelTaxId();

    @Key( value = "label.vatId" )
    String labelVatId();

    @Key( value = "label.vatPayer" )
    String labelVatPayer();

    @Key( value = "label.name" )
    String labelName();

    @Key( value = "label.color" )
    String labelColor();

    @Key( value = "label.firstName" )
    String labelFirstName();

    @Key( value = "label.middleName" )
    String labelMiddleName();

    @Key( value = "label.lastName" )
    String labelLastName();

    @Key( value = "label.prefix" )
    String labelPrefix();

    @Key( value = "label.suffix" )
    String labelSuffix();

    @Key( value = "label.phone" )
    String labelPhone();

    @Key( value = "label.email" )
    String labelEmail();

    @Key( value = "label.ccEmail" )
    String labelCcEmail();

    @Key( value = "label.company" )
    String labelCompany();

    @Key( value = "label.headquarters" )
    String labelHeadquarters();

    @Key( value = "label.address" )
    String labelAddress();

    @Key( value = "label.website" )
    String labelCompanyWebsite();

    @Key( value = "label.home" )
    String labelHome();

    @Key( value = "label.contacts" )
    String labelContacts();

    @Key( value = "label.myAccount" )
    String labelMyAccount();

    @Key( value = "label.companyAccount" )
    String labelCompanyAccount();

    @Key( value = "label.personalAccount" )
    String labelPersonalAccount();

    @Key( value = "label.editContact" )
    String labelEditContact();

    @Key( value = "label.products" )
    String labelProducts();

    @Key( value = "label.editProduct" )
    String labelEditProduct();

    @Key( value = "label.orders" )
    String labelOrders();

    @Key( value = "label.editOrder" )
    String labelEditOrder();

    @Key( value = "label.editBill" )
    String labelEditBill();

    @Key( value = "label.bills" )
    String labelBills();

    @Key( "label.send" )
    String labelSend();

    @Key( "label.invoice" )
    String labelInvoice();

    @Key( value = "label.invoices" )
    String labelInvoices();

    @Key( "label.expenses" )
    String labelExpenses();

    @Key( "label.lastInvoice" )
    String labelLastInvoice();

    @Key( value = "label.editInvoice" )
    String labelEditInvoice();

    @Key( "label.purchases" )
    String labelPurchases();

    @Key( value = "label.confirmation" )
    String labelConfirmation();

    @Key( "label.calculate" )
    String labelCalculate();

    @Key( value = "label.street" )
    String labelStreet();

    @Key( value = "label.city" )
    String labelCity();

    @Key( value = "label.postCode" )
    String labelPostCode();

    @Key( value = "label.country" )
    String labelCountry();

    @Key( value = "label.samePersonalAddress" )
    String labelSamePersonalAddress();

    @Key( value = "label.sameCompanyAddress" )
    String labelSameCompanyAddress();

    @Key( "label.specificBillingAddress" )
    String labelSpecificBillingAddress();

    @Key( value = "label.person" )
    String labelPerson();

    @Key( value = "label.contactPerson" )
    String labelContactPerson();

    @Key( value = "label.invoicing" )
    String labelInvoicing();

    @Key( value = "label.logo" )
    String labelLogo();

    @Key( "label.stamp" )
    String labelStamp();

    @Key( value = "label.detail" )
    String labelDetail();

    @Key( value = "label.content" )
    String labelContent();

    @Key( value = "label.publishing" )
    String labelPublishing();

    @Key( value = "label.pricing" )
    String labelPricing();

    @Key( "label.pricingItems" )
    String labelPricingItems();

    @Key( value = "label.event" )
    String labelEvent();

    @Key( "label.billing.info" )
    String labelBillingInfo();

    @Key( "label.billing.defaults" )
    String labelBillingDefaults();

    @Key( "label.billing.schedule" )
    String labelBillingSchedule();

    @Key( value = "label.billing.beginOn" )
    String labelBillingBeginOn();

    @Key( "label.billing.nextDate" )
    String labelBillingNextDate();

    @Key( "label.billing.lastDate" )
    String labelBillingLastDate();

    @Key( "label.billingContact" )
    String labelBillingContact();

    @Key( value = "label.billingAddress" )
    String labelBillingAddress();

    @Key( "label.bankAccounts" )
    String labelBankAccounts();

    @Key( value = "label.postalAddress" )
    String labelPostalAddress();

    @Key( value = "label.personalAddress" )
    String labelPersonalAddress();

    @Key( value = "label.price" )
    String labelPrice();

    @Key( value = "label.priceExcludingVat" )
    String labelPriceExcludingVat();

    @Key( "label.priceIncludingVat" )
    String labelPriceIncludingVat();

    @Key( "label.totalPrice" )
    String labelTotalPrice();

    @Key( "label.totalPriceExclVat" )
    String labelTotalPriceExclVat();

    @Key( "label.totalVat" )
    String labelTotalVat();

    @Key( "label.totalVatBase" )
    String labelTotalVatBase();

    @Key( "label.amountToPay" )
    String labelAmountToPay();

    @Key( value = "label.currency" )
    String labelCurrency();

    @Key( value = "label.bank" )
    String labelBank();

    @Key( value = "label.merchant" )
    String labelMerchant();

    @Key( value = "label.description" )
    String labelDescription();

    @Key( value = "label.itemName" )
    String labelItemName();

    @Key( value = "label.amount" )
    String labelAmount();

    @Key( value = "label.balance" )
    String labelBalance();

    @Key( value = "label.unit" )
    String labelUnit();

    @Key( value = "label.snippet" )
    String labelSnippet();

    @Key( value = "label.metaFields" )
    String labelMetaFields();

    @Key( value = "label.availableFields" )
    String labelAvailableFields();

    @Key( value = "label.mandatoryFields" )
    String labelMandatoryFields();

    @Key( value = "label.pictures" )
    String labelPictures();

    @Key( value = "label.settings" )
    String labelSettings();

    @Key( value = "label.comingSoon" )
    String labelComingSoon();

    @Key( value = "label.published" )
    String labelPublished();

    @Key( value = "label.social" )
    String labelSocial();

    @Key( value = "label.facebookLike" )
    String labelFacebookLike();

    @Key( value = "label.googlePlus" )
    String labelGooglePlus();

    @Key( value = "label.linkedInShare" )
    String labelLinkedInShare();

    @Key( "label.changes" )
    String labelChanges();

    @Key( "label.vatRecapitulation" )
    String labelVatRecapitulation();

    @Key( "label.created" )
    String labelCreated();

    @Key( "label.modified" )
    String labelModified();

    @Key( "label.decline" )
    String labelDecline();

    @Key( value = "label.domain" )
    String labelDomain();

    @Key( "label.subdomain" )
    String labelSubdomain();

    @Key( value = "label.domain.name" )
    String labelDomainName();

    @Key( value = "label.domain.uri" )
    String labelDomainUri();

    @Key( "label.domain.custom" )
    String labelDomainCustom();

    @Key( "label.domain.type" )
    String labelDomainType();

    @Key( "label.domain.type.subdomain" )
    String labelDomainTypeSubdomain();

    @Key( "label.domain.type.naked" )
    String labelDomainTypeNaked();

    @Key( "label.domain.type.product" )
    String labelDomainTypeProduct();

    @Key( "label.domain.verified" )
    String labelDomainVerified();

    @Key( "label.domain.selection.domains" )
    String labelDomainSelectionDomains();

    @Key( "label.domain.selection.products" )
    String labelDomainSelectionProducts();

    @Key( "label.domain.selection.all" )
    String labelDomainSelectionAll();

    @Key( "label.domain.placeholder.naked" )
    String labelDomainPlaceholderNaked();

    @Key( "label.domain.placeholder.subdomain" )
    String labelDomainPlaceholderSubdomain();

    @Key( "label.product.pricing.template" )
    String labelProductPricingTemplate();

    @Key( "label.product.loading" )
    String labelProductLoading();

    @Key( value = "label.discountCode" )
    String labelDiscountCode();

    @Key( value = "label.allChildrenCheckedIn" )
    String labelAllChildrenCheckedIn();

    @Key( "label.checkedIn" )
    String labelCheckedIn();

    @Key( value = "label.percentage" )
    String labelPercentage();

    @Key( value = "label.fixed" )
    String labelFixed();

    @Key( value = "label.value" )
    String labelValue();

    @Key( value = "label.operation" )
    String labelOperation();

    @Key( value = "label.propertyValue" )
    String labelPropertyValue();

    @Key( value = "label.discountType" )
    String labelDiscountType();

    @Key( value = "label.discountRule" )
    String labelDiscountRule();

    @Key( value = "label.codes" )
    String labelCodes();

    @Key( value = "label.active" )
    String labelActive();

    @Key( value = "label.priceDefinition" )
    String labelPriceDefinition();

    @Key( value = "label.vatDefinition" )
    String labelVatDefinition();

    @Key( value = "label.discounts" )
    String labelDiscounts();

    @Key( value = "label.vat" )
    String labelVat();

    @Key( value = "label.vatEU" )
    String labelVatEU();

    @Key( value = "label.vatNonEU" )
    String labelVatNonEU();

    @Key( "label.vatAmount" )
    String labelVatAmount();

    @Key( "label.sum" )
    String labelSum();

    @Key( value = "label.domesticDelivery" )
    String labelDomesticDelivery();

    @Key( value = "label.deadline" )
    String labelDeadline();

    @Key( value = "label.seats" )
    String labelSeats();

    @Key( value = "label.begin" )
    String labelBegin();

    @Key( value = "label.end" )
    String labelEnd();

    @Key( value = "label.on" )
    String labelOn();

    @Key( value = "label.from" )
    String labelFrom();

    @Key( value = "label.to" )
    String labelTo();

    @Key( value = "label.show" )
    String labelShow();

    @Key( value = "label.location" )
    String labelLocation();

    @Key( value = "label.annually" )
    String labelAnnually();

    @Key( value = "label.semiAnnually" )
    String labelSemiAnnually();

    @Key( value = "label.quarterly" )
    String labelQuarterly();

    @Key( value = "label.monthly" )
    String labelMonthly();

    @Key( value = "label.weekly" )
    String labelWeekly();

    @Key( value = "label.manually" )
    String labelManually();

    @Key( value = "label.proforma" )
    String labelProforma();

    @Key( value = "label.taxDocument" )
    String labelTaxDocument();

    @Key( value = "label.numberOfDays" )
    String labelNumberOfDays();

    @Key( value = "label.periodicity" )
    String labelPeriodicity();

    @Key( value = "label.invoiceType" )
    String labelInvoiceType();

    @Key( value = "label.invoiceNumber" )
    String labelInvoiceNumber();

    @Key( value = "label.variableSymbol" )
    String labelVariableSymbol();

    @Key( value = "label.dates" )
    String labelDates();

    @Key( value = "label.dateOfIssue" )
    String labelDateOfIssue();

    @Key( value = "label.dateOfTaxable" )
    String labelDateOfTaxable();

    @Key( value = "label.dueDate" )
    String labelDueDate();

    @Key( value = "label.paymentMethod" )
    String labelPaymentMethod();

    @Key( "label.paymentId" )
    String labelPaymentId();

    @Key( "label.paymentReference" )
    String labelPaymentReference();

    @Key( "label.noTransactions" )
    String labelNoTransactions();

    @Key( "label.noneInvoice" )
    String labelNoneInvoice();

    @Key( value = "label.bankTransfer" )
    String labelBankTransfer();

    @Key( value = "label.cash" )
    String labelCash();

    @Key( value = "label.cardPayment" )
    String labelCardPayment();

    @Key( "label.beneficiary.iban" )
    String labelBeneficiaryIban();

    @Key( "label.beneficiary.bic" )
    String labelBeneficiaryBic();

    @Key( "label.beneficiary.name" )
    String labelBeneficiaryName();

    @Key( value = "label.texts" )
    String labelInvoiceTexts();

    @Key( value = "label.finalText" )
    String labelFinalText();

    @Key( value = "label.introductoryText" )
    String labelIntroductoryText();

    @Key( value = "label.select" )
    String labelSelect();

    @Key( value = "label.selectCustomer" )
    String labelSelectCustomer();

    @Key( value = "label.selectProduct" )
    String labelSelectProduct();

    @Key( value = "label.selectedProduct" )
    String labelSelectedProduct( String product );

    @Key( value = "label.ok" )
    String labelOk();

    @Key( value = "label.cancel" )
    String labelCancel();

    @Key( "label.close" )
    String labelClose();

    @Key( value = "label.save" )
    String labelSave();

    @Key( value = "label.back" )
    String labelBack();

    @Key( "label.language" )
    String labelLanguage();

    @Key( "label.language.preference" )
    String labelLanguagePreference();

    @Key( "label.language.en" )
    String labelLanguageEn();

    @Key( "label.language.sk" )
    String labelLanguageSk();

    @Key( "label.language.cs" )
    String labelLanguageCs();

    @Key( "label.order.status" )
    String labelOrderStatus();

    @Key( "label.order.loading" )
    String labelOrderLoading();

    @Key( "label.invoice.currentStatus" )
    String labelInvoiceCurrentStatus();

    @Key( "label.invoice.status.new" )
    String labelInvoiceStatusNew();

    @Key( "label.invoice.status.sent" )
    String labelInvoiceStatusSent();

    @Key( "label.invoice.status.paid" )
    String labelInvoiceStatusPaid();

    @Key( "label.invoice.status.canceled" )
    String labelInvoiceStatusCanceled();

    @Key( "label.invoice.loading" )
    String labelInvoiceLoading();

    @Key( "label.bill.loading" )
    String labelBillLoading();

    @Key( "label.billNumber" )
    String labelBillNumber();

    @Key( "label.receipt" )
    String labelReceipt();

    @Key( "label.billType" )
    String labelBillType();

    @Key( "label.billScan" )
    String labelBillScan();

    @Key( "label.currentMonth" )
    String labelCurrentMonth( String firstDay, String lastDay );

    @Key( "label.lastMonths" )
    String labelLastMonths( String lastDay );

    @Key( "label.uploadBatch" )
    String labelUploadBatch();

    @Key( "label.uploadBatchDescription" )
    String labelUploadBatchDescription();

    @Key( "label.importBankAccount" )
    String labelImportBankAccount();

    @Key( "label.clientId" )
    String labelClientId();

    @Key( "label.range.currentMonth" )
    String labelRangeCurrentMonth();

    @Key( "label.range.lastMonth" )
    String labelRangeLastMonth();

    @Key( "label.range.lastThreeMonths" )
    String labelRangeLastThreeMonths();

    @Key( "label.range.lastSixMonths" )
    String labelRangeLastSixMonths();

    @Key( "label.range.all" )
    String labelRangeAll();

    @Key( "label.unbounded" )
    String labelUnbounded();

    @Key( "label.completedAt" )
    String labelCompletedAt();

    @Key( "label.filters" )
    String labelFilters();

    @Key( "label.propagate" )
    String labelPropagate();

    @Key( value = "label.editCategory" )
    String labelEditCategory();

    @Key( value = "label.propertyName.name" )
    String labelPropertyNameName();

    @Key( value = "label.propertyName.amount" )
    String labelPropertyNameAmount();

    @Key( value = "label.propertyName.currency" )
    String labelPropertyNameCurrency();

    @Key( value = "label.propertyName.credit" )
    String labelPropertyNameCredit();

    @Key( value = "label.propertyName.counterpartyIban" )
    String labelPropertyNameCounterpartyIban();

    @Key( value = "label.propertyName.reference" )
    String labelPropertyNameReference();

    @Key( value = "label.transactionStatus.created" )
    String labelTransactionStatusCreated();

    @Key( value = "label.transactionStatus.pending" )
    String labelTransactionStatusPending();

    @Key( value = "label.transactionStatus.completed" )
    String labelTransactionStatusCompleted();

    @Key( value = "label.transactionStatus.declined" )
    String labelTransactionStatusDeclined();

    @Key( value = "label.transactionStatus.failed" )
    String labelTransactionStatusFailed();

    @Key( value = "label.transactionStatus.reverted" )
    String labelTransactionStatusReverted();

    @Key( value = "label.transactionId" )
    String labelTransactionId();

    @Key( value = "label.transactionReference" )
    String labelTransactionReference();

    @Key( value = "label.bankCode" )
    String labelBankCode();

    @Key( value = "label.iban" )
    String labelIban();

    @Key( value = "label.paymentMethod.transfer" )
    String labelPaymentMethodTransfer();

    @Key( value = "label.paymentMethod.cash" )
    String labelPaymentMethodCash();

    @Key( value = "label.paymentMethod.cardPayment" )
    String labelPaymentMethodCardPayment();

    @Key( value = "label.paymentMethod.refund" )
    String labelPaymentMethodRefund();

    @Key( "label.whyThisStore" )
    String labelWhyThisStore();

    @Key( "label.slovak" )
    String labelSlovak();

    @Key( "label.czech" )
    String labelCzech();

    @Key( "label.english" )
    String labelEnglish();

    @Key( "label.approveAll" )
    String labelApproveAll();

    // tooltips

    @Key( value = "tooltip.contactListRefresh" )
    String tooltipContactListRefresh();

    @Key( "tooltip.domain.tip.naked" )
    String tooltipDomainTipNaked();

    @Key( "tooltip.domain.tip.subdomain" )
    String tooltipDomainTipSubdomain();

    @Key( value = "tooltip.new.contact" )
    String tooltipNewContact();

    @Key( value = "tooltip.edit.contact" )
    String tooltipEditContact();

    @Key( value = "tooltip.delete.contact" )
    String tooltipDeleteContact();

    @Key( value = "tooltip.contact.delete" )
    String tooltipContactDelete();

    @Key( value = "tooltip.save.account" )
    String tooltipSaveAccount();

    @Key( value = "tooltip.save.contact" )
    String tooltipSaveContact();

    @Key( value = "tooltip.new.product" )
    String tooltipNewProduct();

    @Key( value = "tooltip.edit.product" )
    String tooltipEditProduct();

    @Key( value = "tooltip.delete.product" )
    String tooltipDeleteProduct();

    @Key( value = "tooltip.save.product" )
    String tooltipSaveProduct();

    @Key( value = "tooltip.new.order" )
    String tooltipNewOrder();

    @Key( value = "tooltip.edit.order" )
    String tooltipEditOrder();

    @Key( value = "tooltip.delete.order" )
    String tooltipDeleteOrder();

    @Key( value = "tooltip.save.order" )
    String tooltipSaveOrder();

    @Key( value = "tooltip.edit.invoice" )
    String tooltipEditInvoice();

    @Key( value = "tooltip.save.invoice" )
    String tooltipSaveInvoice();

    @Key( value = "tooltip.back.list" )
    String tooltipBackList();

    @Key( value = "tooltip.addressAutocomplete" )
    String tooltipAddressAutocomplete();

    @Key( value = "tooltip.availableFields" )
    String tooltipAvailableFields();

    @Key( value = "tooltip.pictures" )
    String tooltipPictures();

    @Key( value = "tooltip.productAutocomplete" )
    String tooltipProductAutocomplete();

    @Key( "tooltip.product.list.refresh" )
    String tooltipProductListRefresh();

    @Key( "tooltip.product.delete" )
    String tooltipProductDelete();

    @Key( value = "tooltip.contactAutocomplete" )
    String tooltipContactAutocomplete();

    @Key( value = "tooltip.supplierAutocomplete" )
    String tooltipSupplierAutocomplete();

    @Key( "tooltip.order.issueInvoice" )
    String tooltipOrderIssueInvoice();

    @Key( "tooltip.order.lastInvoice" )
    String tooltipOrderLastInvoice();

    @Key( "tooltip.order.invoices" )
    String tooltipOrderPastInvoices();

    @Key( "tooltip.order.delete" )
    String tooltipOrderDelete();

    @Key( "tooltip.order.setCompleted" )
    String tooltipOrderSetCompleted();

    @Key( "tooltip.order.number" )
    String tooltipOrderNumber();

    @Key( "tooltip.order.list.refresh" )
    String tooltipOrderListRefresh();

    @Key( "tooltip.purchase.order.number" )
    String tooltipPurchaseOrderNumber();

    @Key( "tooltip.purchase.order.list.refresh" )
    String tooltipPurchaseOrderListRefresh();

    @Key( "tooltip.purchase.order.view" )
    String tooltipPurchaseOrderView();

    @Key( "tooltip.purchase.order.decline" )
    String tooltipPurchaseOrderDecline();

    @Key( "tooltip.purchase.order.delete" )
    String tooltipPurchaseOrderDelete();

    @Key( "tooltip.invoice.send" )
    String tooltipInvoiceSend();

    @Key( "tooltip.invoice.send.email" )
    String tooltipInvoiceSendEmail();

    @Key( "tooltip.invoice.cancel" )
    String tooltipInvoiceCancel();

    @Key( "tooltip.invoice.delete" )
    String tooltipInvoiceDelete();

    @Key( "tooltip.invoice.download" )
    String tooltipInvoiceDownload();

    @Key( "tooltip.invoice.new" )
    String tooltipInvoiceNew();

    @Key( "tooltip.invoice.list.refresh" )
    String tooltipInvoiceListRefresh();

    @Key( "tooltip.invoice.list.clearFilter" )
    String tooltipInvoiceListClearFilter();

    @Key( "tooltip.purchase.invoice.view" )
    String tooltipPurchaseInvoiceView();

    @Key( "tooltip.purchase.receipt.view" )
    String tooltipPurchaseReceiptView();

    @Key( "tooltip.purchase.ecosystem.inside" )
    String tooltipPurchaseEcosystemInside();

    @Key( "tooltip.purchase.ecosystem.outside" )
    String tooltipPurchaseEcosystemOutside();

    @Key( "tooltip.expense.list.refresh" )
    String tooltipExpenseListRefresh();

    @Key( "tooltip.customer.ecosystem.inside" )
    String tooltipCustomerEcosystemInside();

    @Key( value = "tooltip.bill.new" )
    String tooltipNewBill();

    @Key( value = "tooltip.bill.save" )
    String tooltipSaveBill();

    @Key( value = "tooltip.bill.delete" )
    String tooltipBillDelete();

    @Key( value = "tooltip.bill.approve" )
    String tooltipBillApprove();

    @Key( value = "tooltip.bill.approveAll" )
    String tooltipApproveAllBills();

    @Key( value = "tooltip.bill.list.refresh" )
    String tooltipBillListRefresh();

    @Key( value = "tooltip.bill.edit" )
    String tooltipEditBill();

    @Key( value = "tooltip.revolut.clientId" )
    String tooltipRevolutClientIdTooltip();

    @Key( value = "tooltip.bankAccount.save" )
    String tooltipSaveBankAccount();

    @Key( value = "tooltip.bankAccount.delete" )
    String tooltipDeleteBankAccount();

    @Key( value = "tooltip.bankAccount.markAsPrimary" )
    String tooltipMarkBankAccountAsPrimary();

    @Key( value = "tooltip.bankAccount.markedAsPrimary" )
    String tooltipBankAccountIsMarkedAsPrimary();

    @Key( value = "tooltip.bankAccount.imported" )
    String tooltipBankAccountImported();

    @Key( value = "tooltip.bill.approved" )
    String tooltipBillApproved();

    @Key( value = "tooltip.bill.waitingForApproval" )
    String tooltipBillWaitingForApproval();

    @Key( value = "tooltip.transaction.list.refresh" )
    String tooltipTransactionListRefresh();

    @Key( value = "tooltip.transaction.detail" )
    String tooltipTransactionDetail();

    @Key( value = "tooltip.categories.list.refresh" )
    String tooltipCategoriesListRefresh();

    @Key( value = "tooltip.bill.paired" )
    String tooltipBillPaired();

    @Key( value = "tooltip.bill.notPaired" )
    String tooltipBillNotPaired();

    @Key( value = "tooltip.transaction.categories" )
    String tooltipCategories();

    @Key( value = "tooltip.category.edit" )
    String tooltipEditCategory();

    @Key( value = "tooltip.category.resolve" )
    String tooltipCategoryResolve();

    @Key( value = "tooltip.category.new" )
    String tooltipNewCategory();

    @Key( value = "tooltip.category.save" )
    String tooltipSaveCategory();

    @Key( value = "tooltip.category.delete" )
    String tooltipCategoryDelete();

    @Key( value = "tooltip.upload.bill" )
    String tooltipUploadBill();

    @Key( value = "tooltip.invoice.paymentPending" )
    String tooltipInvoicePaymentPending(String key);

    // messages

    @Key( "msg.invoiceDeleted" )
    String msgInvoiceDeleted();

    @Key( "msg.invoiceDeletedWith" )
    String msgInvoiceDeletedWith( String detail );

    @Key( value = "msg.recordDeleted" )
    String msgRecordDeleted( String detail );

    @Key( value = "msg.errorRemoteServiceCall" )
    String msgErrorRemoteServiceCall();

    @Key( value = "msg.errorRecordDoesNotExists" )
    String msgErrorRecordDoesNotExists();

    @Key( "msg.errorBadRequest" )
    String msgErrorBadRequest( String p );

    @Key( value = "msg.msgConfirmOneRecordDelete" )
    String msgConfirmOneRecordDelete( String name );

    @Key( value = "msg.msgConfirmMultipleRecordsDelete" )
    String msgConfirmMultipleRecordsDelete( int records );

    @Key( value = "msg.recordCreated" )
    String msgRecordCreated();

    @Key( value = "msg.recordUpdated" )
    String msgRecordUpdated();

    @Key( value = "msg.pictureDeleted" )
    String msgPictureDeleted();

    @Key( "msg.invoice.issued" )
    String msgInvoiceIssued();

    @Key( "msg.order.created" )
    String msgOrderCreated();

    @Key( "msg.order.updated" )
    String msgOrderUpdated();

    @Key( "msg.order.status.active" )
    String msgOrderStatusActive();

    @Key( "msg.order.status.suspended" )
    String msgOrderStatusSuspended();

    @Key( "msg.order.status.completed" )
    String msgOrderStatusCompleted();

    @Key( "msg.purchase.order.declined" )
    String msgPurchaseOrderDeclined();

    @Key( "msg.invoice.status.sent" )
    String msgInvoiceStatusSent();

    @Key( "msg.batch.created" )
    String msgBatchCreated( String name );

    @Key( "msg.bankAccount.markedAsPrimary" )
    String msgBankAccountMarkedAsPrimary( String name );

    @Key( "msg.bankAccountImported" )
    String msgBankAccountImported( String name );

    @Key( "msg.billApproved" )
    String msgBillApproved( String name );

    @Key( "msg.why" )
    String why();

    @Key( "msg.whyCreated" )
    String msgWhyCreated();

    // other

    @Key( value = "copyright" )
    String copyright();

    @Key( "description.order.status.trialing" )
    String descriptionOrderStatusTrialing();

    @Key( "description.order.status.active" )
    String descriptionOrderStatusActive();

    @Key( "description.order.status.activate" )
    String descriptionOrderStatusActivate();

    @Key( "description.order.status.suspended" )
    String descriptionOrderStatusSuspended();

    @Key( "description.order.status.suspend" )
    String descriptionOrderStatusSuspend();

    @Key( "description.order.status.issue" )
    String descriptionOrderStatusIssue();

    @Key( "description.order.status.finished" )
    String descriptionOrderStatusFinished();

    @Key( "description.order.status.completed" )
    String descriptionOrderStatusCompleted();

    @Key( "description.invoice.status.new" )
    String descriptionInvoiceStatusNew();

    @Key( "description.invoice.status.sendTo" )
    String descriptionInvoiceStatusSendTo();

    @Key( "description.invoice.status.sent" )
    String descriptionInvoiceStatusSent();

    @Key( "description.invoice.status.paid" )
    String descriptionInvoiceStatusPaid();

    @Key( "description.invoice.status.canceled" )
    String descriptionInvoiceStatusCanceled();

    @Key( "description.invoice.send" )
    String descriptionInvoiceSend();

    // question

    @Key( value = "question.deleteRecord" )
    String questionDeleteRecord();

    @Key( value = "question.approveBill" )
    String questionApproveBill();

    @Key( value = "question.approveSelectedBills")
    String questionApproveSelectedBills(int records);

    @Key( "question.purchase.order.decline" )
    String questionPurchaseOrderDecline( String company );

    @Key( "question.purchase.order.delete" )
    String questionPurchaseOrderDelete( String p0 );
}
