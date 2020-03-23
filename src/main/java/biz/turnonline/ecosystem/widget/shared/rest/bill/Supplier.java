/*
 * Billing Processor
 * TurnOnline.biz Ecosystem Billing Processor (Bookkeeping)
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package biz.turnonline.ecosystem.widget.shared.rest.bill;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Supplier
 */
public class Supplier   {
  @JsonProperty("businessName")
  private String businessName = null;

  @JsonProperty("companyId")
  private String companyId = null;

  @JsonProperty("taxId")
  private String taxId = null;

  @JsonProperty("vatId")
  private String vatId = null;

  @JsonProperty("street")
  private String street = null;

  @JsonProperty("city")
  private String city = null;

  @JsonProperty("country")
  private String country = null;

  @JsonProperty("postcode")
  private String postcode = null;

  public Supplier businessName(String businessName) {
    this.businessName = businessName;
    return this;
  }

  /**
   * Supplier business name.
   * @return businessName
   **/
  @JsonProperty("businessName")
  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public Supplier companyId(String companyId) {
    this.companyId = companyId;
    return this;
  }

  /**
   * Supplier business identification number.
   * @return companyId
   **/
  @JsonProperty("companyId")
  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public Supplier taxId(String taxId) {
    this.taxId = taxId;
    return this;
  }

  /**
   * Supplier tax payer identification number.
   * @return taxId
   **/
  @JsonProperty("taxId")
  public String getTaxId() {
    return taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  public Supplier vatId(String vatId) {
    this.vatId = vatId;
    return this;
  }

  /**
   * Supplier vat payer identification number.
   * @return vatId
   **/
  @JsonProperty("vatId")
  public String getVatId() {
    return vatId;
  }

  public void setVatId(String vatId) {
    this.vatId = vatId;
  }

  public Supplier street(String street) {
    this.street = street;
    return this;
  }

  /**
   * The street and street number.
   * @return street
   **/
  @JsonProperty("street")
  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Supplier city(String city) {
    this.city = city;
    return this;
  }

  /**
   * The address city.
   * @return city
   **/
  @JsonProperty("city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Supplier country(String country) {
    this.country = country;
    return this;
  }

  /**
   * The address ISO 3166 alpha-2 country code. It’s case insensitive.
   * @return country
   **/
  @JsonProperty("country")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Supplier postcode(String postcode) {
    this.postcode = postcode;
    return this;
  }

  /**
   * The address post code.
   * @return postcode
   **/
  @JsonProperty("postcode")
  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }


  @Override
  public boolean equals( Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Supplier supplier = (Supplier) o;
    return Objects.equals(this.businessName, supplier.businessName) &&
        Objects.equals(this.companyId, supplier.companyId) &&
        Objects.equals(this.taxId, supplier.taxId) &&
        Objects.equals(this.vatId, supplier.vatId) &&
        Objects.equals(this.street, supplier.street) &&
        Objects.equals(this.city, supplier.city) &&
        Objects.equals(this.country, supplier.country) &&
        Objects.equals(this.postcode, supplier.postcode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(businessName, companyId, taxId, vatId, street, city, country, postcode);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Supplier {\n");

    sb.append("    businessName: ").append(toIndentedString(businessName)).append("\n");
    sb.append("    companyId: ").append(toIndentedString(companyId)).append("\n");
    sb.append("    taxId: ").append(toIndentedString(taxId)).append("\n");
    sb.append("    vatId: ").append(toIndentedString(vatId)).append("\n");
    sb.append("    street: ").append(toIndentedString(street)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    postcode: ").append(toIndentedString(postcode)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString( Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

