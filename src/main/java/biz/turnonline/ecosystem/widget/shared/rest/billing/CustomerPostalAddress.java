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
 * on 2019-07-31 at 14:32:05 UTC
 * Modify at your own risk.
 */

package biz.turnonline.ecosystem.widget.shared.rest.billing;

/**
 * Model definition for CustomerPostalAddress.
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
public final class CustomerPostalAddress
{
    /**
     * The value may be {@code null}.
     */
    private String businessName;

    /**
     * The value may be {@code null}.
     */
    private String city;

    /**
     * The value may be {@code null}.
     */
    private String country;

    /**
     * The value may be {@code null}.
     */
    private String firstName;

    /**
     * The value may be {@code null}.
     */
    private String lastName;

    /**
     * The value may be {@code null}.
     */
    private String postcode;

    /**
     * The value may be {@code null}.
     */
    private String prefix;

    /**
     * The value may be {@code null}.
     */
    private String street;

    /**
     * The value may be {@code null}.
     */
    private String suffix;

    /**
     * @return value or {@code null} for none
     */
    public String getBusinessName()
    {
        return businessName;
    }

    /**
     * @param businessName businessName or {@code null} for none
     */
    public CustomerPostalAddress setBusinessName( String businessName )
    {
        this.businessName = businessName;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getCity()
    {
        return city;
    }

    /**
     * @param city city or {@code null} for none
     */
    public CustomerPostalAddress setCity( String city )
    {
        this.city = city;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * @param country country or {@code null} for none
     */
    public CustomerPostalAddress setCountry( String country )
    {
        this.country = country;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName firstName or {@code null} for none
     */
    public CustomerPostalAddress setFirstName( String firstName )
    {
        this.firstName = firstName;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName lastName or {@code null} for none
     */
    public CustomerPostalAddress setLastName( String lastName )
    {
        this.lastName = lastName;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getPostcode()
    {
        return postcode;
    }

    /**
     * @param postcode postcode or {@code null} for none
     */
    public CustomerPostalAddress setPostcode( String postcode )
    {
        this.postcode = postcode;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * @param prefix prefix or {@code null} for none
     */
    public CustomerPostalAddress setPrefix( String prefix )
    {
        this.prefix = prefix;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getStreet()
    {
        return street;
    }

    /**
     * @param street street or {@code null} for none
     */
    public CustomerPostalAddress setStreet( String street )
    {
        this.street = street;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getSuffix()
    {
        return suffix;
    }

    /**
     * @param suffix suffix or {@code null} for none
     */
    public CustomerPostalAddress setSuffix( String suffix )
    {
        this.suffix = suffix;
        return this;
    }
}
