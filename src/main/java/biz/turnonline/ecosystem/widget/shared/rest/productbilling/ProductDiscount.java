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

import java.util.Objects;

/**
 * Model definition for ProductDiscount.
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
public final class ProductDiscount
{

    /**
     * The value may be {@code null}.
     */
    
    private java.util.List<String> codes;

    /**
     * The value may be {@code null}.
     */
    
    private Boolean enabled;

    /**
     * The value may be {@code null}.
     */
    
    private Double off;

    /**
     * The value may be {@code null}.
     */
    
    private String rule;

    /**
     * The value may be {@code null}.
     */
    
    private String unit;

    /**
     * @return value or {@code null} for none
     */
    public java.util.List<String> getCodes()
    {
        return codes;
    }

    /**
     * @param codes codes or {@code null} for none
     */
    public ProductDiscount setCodes( java.util.List<String> codes )
    {
        this.codes = codes;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Boolean getEnabled()
    {
        return enabled;
    }

    /**
     * @param enabled enabled or {@code null} for none
     */
    public ProductDiscount setEnabled( Boolean enabled )
    {
        this.enabled = enabled;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Double getOff()
    {
        return off;
    }

    /**
     * @param off off or {@code null} for none
     */
    public ProductDiscount setOff( Double off )
    {
        this.off = off;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getRule()
    {
        return rule;
    }

    /**
     * @param rule rule or {@code null} for none
     */
    public ProductDiscount setRule( String rule )
    {
        this.rule = rule;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getUnit()
    {
        return unit;
    }

    /**
     * @param unit unit or {@code null} for none
     */
    public ProductDiscount setUnit( String unit )
    {
        this.unit = unit;
        return this;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( !( o instanceof ProductDiscount ) ) return false;
        ProductDiscount discount = ( ProductDiscount ) o;
        return Objects.equals( codes, discount.codes ) &&
                Objects.equals( enabled, discount.enabled ) &&
                Objects.equals( off, discount.off ) &&
                Objects.equals( rule, discount.rule ) &&
                Objects.equals( unit, discount.unit );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( codes, enabled, off, rule, unit );
    }
}