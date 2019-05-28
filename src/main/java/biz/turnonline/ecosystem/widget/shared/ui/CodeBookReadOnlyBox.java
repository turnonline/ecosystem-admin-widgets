package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.rest.CodeBook;
import biz.turnonline.ecosystem.widget.shared.rest.CodeBookRestFacade;
import com.google.gwt.user.client.ui.InlineLabel;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class CodeBookReadOnlyBox<T extends CodeBook>
        extends InlineLabel
{
    private String code;

    public CodeBookReadOnlyBox( String code, Class<T> clazz )
    {
        super( code );
        this.code = code;

        CodeBookRestFacade.getCodeBook( clazz, response -> {
            if ( !response.getItems().isEmpty() )
            {
                response.getItems()
                        .stream()
                        .filter( t -> t.getCode().equals( CodeBookReadOnlyBox.this.code ) )
                        .findFirst()
                        .ifPresent( codeBook -> setText( codeBook.getLabel() ) );
            }
        } );
    }
}
