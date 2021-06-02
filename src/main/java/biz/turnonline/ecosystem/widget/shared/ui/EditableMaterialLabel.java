package biz.turnonline.ecosystem.widget.shared.ui;

import gwt.material.design.client.ui.MaterialLabel;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class EditableMaterialLabel
        extends MaterialLabel
{
    private String emptyValue;

    private String onFocusValue;

    public EditableMaterialLabel(  )
    {
        getElement().setAttribute( "contenteditable", "true" );

        addFocusHandler( event -> {
            if ( getText().equals( emptyValue ) )
            {
                setText( "" );
            }

            onFocusValue = getText();
        } );

        addBlurHandler( event -> {
            if ( getText().equals( "" ) )
            {
                setText( emptyValue );
            }
        } );
    }

    public void setEmptyValue( String emptyValue )
    {
        this.emptyValue = emptyValue;
    }

    public boolean isValueChanged()
    {
        return !onFocusValue.equals( getText() );
    }
}
