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
 * Model definition for BankAccount.
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
public final class BankAccount
{

    /**
     * The value may be {@code null}.
     */
    
    private String bic;

    /**
     * The value may be {@code null}.
     */
    
    private String currency;

    /**
     * The value may be {@code null}.
     */
    
    private String formatted;

    /**
     * The value may be {@code null}.
     */
    
    private String iban;

    /**
     * The value may be {@code null}.
     */

    private Long id;

    /**
     * @return value or {@code null} for none
     */
    public String getBic()
    {
        return bic;
    }

    /**
     * @param bic bic or {@code null} for none
     */
    public BankAccount setBic( String bic )
    {
        this.bic = bic;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getCurrency()
    {
        return currency;
    }

    /**
     * @param currency currency or {@code null} for none
     */
    public BankAccount setCurrency( String currency )
    {
        this.currency = currency;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getFormatted()
    {
        return formatted;
    }

    /**
     * @param formatted formatted or {@code null} for none
     */
    public BankAccount setFormatted( String formatted )
    {
        this.formatted = formatted;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getIban()
    {
        return iban;
    }

    /**
     * @param iban iban or {@code null} for none
     */
    public BankAccount setIban( String iban )
    {
        this.iban = iban;
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
    public BankAccount setId( Long id )
    {
        this.id = id;
        return this;
    }

}
