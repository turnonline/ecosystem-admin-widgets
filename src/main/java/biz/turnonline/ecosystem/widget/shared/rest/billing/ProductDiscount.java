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

import java.util.List;
import java.util.Objects;

public final class ProductDiscount
{
    private List<String> codes;

    private Boolean enabled;

    private Double off;

    private String rule;

    private String unit;

    public List<String> getCodes()
    {
        return codes;
    }

    public ProductDiscount setCodes( List<String> codes )
    {
        this.codes = codes;
        return this;
    }

    public Boolean getEnabled()
    {
        return enabled;
    }

    public ProductDiscount setEnabled( Boolean enabled )
    {
        this.enabled = enabled;
        return this;
    }

    public Double getOff()
    {
        return off;
    }

    public ProductDiscount setOff( Double off )
    {
        this.off = off;
        return this;
    }

    public String getRule()
    {
        return rule;
    }

    public ProductDiscount setRule( String rule )
    {
        this.rule = rule;
        return this;
    }

    public String getUnit()
    {
        return unit;
    }

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
