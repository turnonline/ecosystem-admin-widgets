package biz.turnonline.ecosystem.widget.shared.rest.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model definition for Merchant.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the TurnOnline.biz Payment Processor. For a detailed
 * explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
public final class Merchant
{
    /**
     * The value may be {@code null}.
     */
    @JsonProperty( "category" )
    private String category;

    /**
     * The value may be {@code null}.
     */
    @JsonProperty( "city" )
    private String city;

    /**
     * The value may be {@code null}.
     */
    @JsonProperty( "name" )
    private String name;

    /**
     * @return value or {@code null} for none
     */
    public String getCategory()
    {
        return category;
    }

    /**
     * @param category category or {@code null} for none
     */
    public Merchant setCategory( String category )
    {
        this.category = category;
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
    public Merchant setCity( String city )
    {
        this.city = city;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name name or {@code null} for none
     */
    public Merchant setName( String name )
    {
        this.name = name;
        return this;
    }
}
