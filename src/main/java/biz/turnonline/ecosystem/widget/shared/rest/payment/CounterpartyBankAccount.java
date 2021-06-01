package biz.turnonline.ecosystem.widget.shared.rest.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class CounterpartyBankAccount
{
    @JsonProperty( "bic" )
    private String bic;

    @JsonProperty( "iban" )
    private String iban;

    @JsonProperty( "name" )
    private String name;

    public String getBic()
    {
        return bic;
    }

    public void setBic( String bic )
    {
        this.bic = bic;
    }

    public String getIban()
    {
        return iban;
    }

    public void setIban( String iban )
    {
        this.iban = iban;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }
}
