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

import biz.turnonline.ecosystem.widget.shared.rest.CodeBook;

public final class VatRate
        implements CodeBook
{
    private String code;

    private String domicile;

    private String label;

    private String locale;

    private Double value;

    /**
     * The VAT rate code. It's case insensitive.
     **/
    public String getCode()
    {
        return code;
    }

    public VatRate setCode( String code )
    {
        this.code = code;
        return this;
    }

    /**
     * The ISO 3166 alpha-2 country code. The country of the VAT rate that belongs to.
     **/
    public String getDomicile()
    {
        return domicile;
    }

    public VatRate setDomicile( String domicile )
    {
        this.domicile = domicile;
        return this;
    }

    /**
     * The codebook value, VAT rate short name.
     **/
    public String getLabel()
    {
        return label;
    }

    public VatRate setLabel( String label )
    {
        this.label = label;
        return this;
    }

    /**
     * The label language. ISO 639 alpha-2 or alpha-3 language code.
     **/
    public String getLocale()
    {
        return locale;
    }

    public VatRate setLocale( String locale )
    {
        this.locale = locale;
        return this;
    }

    /**
     * The VAT rate as a number value.
     **/
    public Double getValue()
    {
        return value;
    }

    public VatRate setValue( Double value )
    {
        this.value = value;
        return this;
    }
}
