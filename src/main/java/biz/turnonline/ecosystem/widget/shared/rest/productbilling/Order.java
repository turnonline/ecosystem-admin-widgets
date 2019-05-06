/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2018-10-08 17:45:39 UTC)
 * on 2019-03-14 at 19:18:29 UTC
 * Modify at your own risk.
 */

package biz.turnonline.ecosystem.widget.shared.rest.productbilling;

import java.util.Date;

/**
 * Model definition for Order.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the TurnOnline.biz Product Billing. For a detailed
 * explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings( "javadoc" )
public final class Order
        implements HasCustomer, HasPricingItems
{
    /**
     * The value may be {@code null}.
     */

    private Date beginAt;

    /**
     * The value may be {@code null}.
     */

    private Customer customer;

    /**
     * The value may be {@code null}.
     */

    private Long id;

    /**
     * The value may be {@code null}.
     */

    private String invoiceType;

    /**
     * The value may be {@code null}.
     */

    private java.util.List<PricingItem> items;

    /**
     * The value may be {@code null}.
     */

    private Date lastBillingDate;

    /**
     * The value may be {@code null}.
     */

    private Date modificationDate;

    /**
     * The value may be {@code null}.
     */

    private Date nextBillingDate;

    /**
     * The value may be {@code null}.
     */

    private Integer numberOfDays;

    /**
     * The value may be {@code null}.
     */

    private String periodicity;

    /**
     * The value may be {@code null}.
     */

    private String status;

    /**
     * The value may be {@code null}.
     */

    private Double totalPrice;

    /**
     * The value may be {@code null}.
     */

    private Double totalPriceExclVat;

    /**
     * The value may be {@code null}.
     */

    private Double totalVatBase;

    /**
     * @return value or {@code null} for none
     */
    public Date getBeginAt()
    {
        return beginAt;
    }

    /**
     * @param beginAt beginAt or {@code null} for none
     */
    public Order setBeginAt( Date beginAt )
    {
        this.beginAt = beginAt;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Customer getCustomer()
    {
        return customer;
    }

    /**
     * @param customer customer or {@code null} for none
     */
    public Order setCustomer( Customer customer )
    {
        this.customer = customer;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id id or {@code null} for none
     */
    public Order setId( Long id )
    {
        this.id = id;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getInvoiceType()
    {
        return invoiceType;
    }

    /**
     * @param invoiceType invoiceType or {@code null} for none
     */
    public Order setInvoiceType( String invoiceType )
    {
        this.invoiceType = invoiceType;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public java.util.List<PricingItem> getItems()
    {
        return items;
    }

    /**
     * @param items items or {@code null} for none
     */
    public void setItems( java.util.List<PricingItem> items )
    {
        this.items = items;
    }

    /**
     * @return value or {@code null} for none
     */
    public Date getLastBillingDate()
    {
        return lastBillingDate;
    }

    /**
     * @param lastBillingDate lastBillingDate or {@code null} for none
     */
    public Order setLastBillingDate( Date lastBillingDate )
    {
        this.lastBillingDate = lastBillingDate;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Date getModificationDate()
    {
        return modificationDate;
    }

    /**
     * @param modificationDate modificationDate or {@code null} for none
     */
    public Order setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Date getNextBillingDate()
    {
        return nextBillingDate;
    }

    /**
     * @param nextBillingDate nextBillingDate or {@code null} for none
     */
    public Order setNextBillingDate( Date nextBillingDate )
    {
        this.nextBillingDate = nextBillingDate;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Integer getNumberOfDays()
    {
        return numberOfDays;
    }

    /**
     * @param numberOfDays numberOfDays or {@code null} for none
     */
    public Order setNumberOfDays( Integer numberOfDays )
    {
        this.numberOfDays = numberOfDays;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getPeriodicity()
    {
        return periodicity;
    }

    /**
     * @param periodicity periodicity or {@code null} for none
     */
    public Order setPeriodicity( String periodicity )
    {
        this.periodicity = periodicity;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * @param status status or {@code null} for none
     */
    public Order setStatus( String status )
    {
        this.status = status;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Double getTotalPrice()
    {
        return totalPrice;
    }

    /**
     * @param totalPrice totalPrice or {@code null} for none
     */
    public Order setTotalPrice( Double totalPrice )
    {
        this.totalPrice = totalPrice;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Double getTotalPriceExclVat()
    {
        return totalPriceExclVat;
    }

    /**
     * @param totalPriceExclVat totalPriceExclVat or {@code null} for none
     */
    public Order setTotalPriceExclVat( Double totalPriceExclVat )
    {
        this.totalPriceExclVat = totalPriceExclVat;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Double getTotalVatBase()
    {
        return totalVatBase;
    }

    /**
     * @param totalVatBase totalVatBase or {@code null} for none
     */
    public Order setTotalVatBase( Double totalVatBase )
    {
        this.totalVatBase = totalVatBase;
        return this;
    }

}
