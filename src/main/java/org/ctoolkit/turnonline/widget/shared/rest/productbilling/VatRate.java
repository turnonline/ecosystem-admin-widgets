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

package org.ctoolkit.turnonline.widget.shared.rest.productbilling;

/**
 * Model definition for VatRate.
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
public final class VatRate
{

    /**
     * The value may be {@code null}.
     */
    
    private String code;

    /**
     * The value may be {@code null}.
     */
    
    private String domicile;

    /**
     * The value may be {@code null}.
     */
    
    private String label;

    /**
     * The value may be {@code null}.
     */
    
    private String locale;

    /**
     * @return value or {@code null} for none
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code code or {@code null} for none
     */
    public VatRate setCode( String code )
    {
        this.code = code;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getDomicile()
    {
        return domicile;
    }

    /**
     * @param domicile domicile or {@code null} for none
     */
    public VatRate setDomicile( String domicile )
    {
        this.domicile = domicile;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * @param label label or {@code null} for none
     */
    public VatRate setLabel( String label )
    {
        this.label = label;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getLocale()
    {
        return locale;
    }

    /**
     * @param locale locale or {@code null} for none
     */
    public VatRate setLocale( String locale )
    {
        this.locale = locale;
        return this;
    }

}
