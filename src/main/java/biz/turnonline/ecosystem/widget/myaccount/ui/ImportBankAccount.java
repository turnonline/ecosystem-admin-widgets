package biz.turnonline.ecosystem.widget.myaccount.ui;

import biz.turnonline.ecosystem.widget.shared.rest.payment.Certificate;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class ImportBankAccount
{
    private String bankCode;

    private String bankAccountName;

    private Certificate certificate;

    public String getBankCode()
    {
        return bankCode;
    }

    public void setBankCode( String bankCode )
    {
        this.bankCode = bankCode;
    }

    public String getBankAccountName()
    {
        return bankAccountName;
    }

    public void setBankAccountName( String bankAccountName )
    {
        this.bankAccountName = bankAccountName;
    }

    public Certificate getCertificate()
    {
        return certificate;
    }

    public void setCertificate( Certificate certificate )
    {
        this.certificate = certificate;
    }
}
