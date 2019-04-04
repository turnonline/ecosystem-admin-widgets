package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.CodeBook;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class StaticCodeBook
        implements CodeBook
{
    private String code;

    private String label;

    public StaticCodeBook( String code, String label )
    {
        this.code = code;
        this.label = label;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    @Override
    public String getCode()
    {
        return code;
    }

    public void setLabel( String label )
    {
        this.label = label;
    }

    @Override
    public String getLabel()
    {
        return label;
    }
}
