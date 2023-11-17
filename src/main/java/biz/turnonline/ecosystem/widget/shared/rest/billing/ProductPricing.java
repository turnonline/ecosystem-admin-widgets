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

package biz.turnonline.ecosystem.widget.shared.rest.billing;

import org.ctoolkit.gwt.client.facade.RelevantNullChecker;

import java.util.List;
import java.util.Map;

public final class ProductPricing
        implements RelevantNullChecker
{
    private String currency;

    private List<ProductDiscount> discounts;

    private Boolean domesticDelivery;

    private PricingItem items;

    private Double priceExclVat;

    private Map<String, Object> subsidiary;

    private List<PricingStructureTemplate> template;

    private String vat;

    private String vatEU;

    private String vatNonEU;

    /**
     * The currency alphabetic code based on the ISO 4217. If no value has been provided the seller's domicile default will be set.
     **/
    public String getCurrency()
    {
        return currency;
    }

    public ProductPricing setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    /**
     * The optional list of discount definition. The one which will match will be applied. Currently only single discount can be applied to discount item price.
     **/
    public List<ProductDiscount> getDiscounts()
    {
        return discounts;
    }

    public ProductPricing setDiscounts( List<ProductDiscount> discounts )
    {
        this.discounts = discounts;
        return this;
    }

    /**
     * The boolean indication whether the product is marked as 'domestic delivery'. It might have an impact how is the VAT handled in the final price.
     **/
    public Boolean getDomesticDelivery()
    {
        return domesticDelivery;
    }

    public ProductPricing setDomesticDelivery( Boolean domesticDelivery )
    {
        this.domesticDelivery = domesticDelivery;
        return this;
    }

    /**
     * An extra property with recalculated pricing item tree based on the current product's values with default structure. It will be recalculated on demand, see header 'Vnd-ConnecSys-Calc-Pricing-Items'. However in case the product is being delivered as a Pub / Sub event, this pricing item tree will be included by default. Root itemType will be always 'Standard'.
     **/
    public PricingItem getItems()
    {
        return items;
    }

    public ProductPricing setItems( PricingItem items )
    {
        this.items = items;
        return this;
    }

    /**
     * The product catalog price for a single unit (meaning amount 1.0 as a quantity). The price is excluding VAT in case company is VAT payer, otherwise price is final.
     **/
    public Double getPriceExclVat()
    {
        return priceExclVat;
    }

    public ProductPricing setPriceExclVat( Double priceExclVat )
    {
        this.priceExclVat = priceExclVat;
        return this;
    }

    /**
     * An initial (and optional) subsidiary properties of the product item. A flat, single level structure (Map of values) in JSON format that will be saved as it is given. It will be returned as part of the pricing item if defined.
     **/
    public Map<String, Object> getSubsidiary()
    {
        return subsidiary;
    }

    public ProductPricing setSubsidiary( Map<String, Object> subsidiary )
    {
        this.subsidiary = subsidiary;
        return this;
    }

    /**
     * An additional product pricing if more complex pricing structure is being required. A non empty list will define the price of this pricing priceExclVat property. Only the leaf items defines the price, the rest are being calculated recursively from the leaf item to root item.
     **/
    public List<PricingStructureTemplate> getTemplate()
    {
        return template;
    }

    public ProductPricing setTemplate( List<PricingStructureTemplate> template )
    {
        this.template = template;
        return this;
    }

    /**
     * The VAT rate codebook value to be applied to calculate the final price of the product for domestic (seller) to domestic customer sale.
     **/
    public String getVat()
    {
        return vat;
    }

    public ProductPricing setVat( String vat )
    {
        this.vat = vat;
        return this;
    }

    /**
     * The VAT rate codebook value to be applied to calculate the final price of the product  for domestic (seller) to EU based customer sale.
     **/
    public String getVatEU()
    {
        return vatEU;
    }

    public ProductPricing setVatEU( String vatEU )
    {
        this.vatEU = vatEU;
        return this;
    }

    /**
     * The VAT rate codebook value to be applied to calculate the final price of the product  for domestic (seller) to non EU based customer sale.
     **/
    public String getVatNonEU()
    {
        return vatNonEU;
    }

    public ProductPricing setVatNonEU( String vatNonEU )
    {
        this.vatNonEU = vatNonEU;
        return this;
    }

    @Override
    public boolean allNull()
    {
        // there will be always at least a default values
        return false;
    }
}
