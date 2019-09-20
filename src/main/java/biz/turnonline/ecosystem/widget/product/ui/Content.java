package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPublishing;
import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.addins.client.richeditor.MaterialRichEditor;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Content
        extends Composite
{
    private static ContentUiBinder binder = GWT.create( ContentUiBinder.class );

    @UiField
    MaterialRichEditor editor;

    @Inject
    public Content()
    {
        initWidget( binder.createAndBindUi( this ) );
    }

    public ProductPublishing bind( @Nullable ProductPublishing publishing )
    {
        if ( publishing == null )
        {
            publishing = new ProductPublishing();
        }

        String html = editor.getHTML();
        publishing.setDescription( Strings.isNullOrEmpty( html ) ? null : html );
        return publishing;
    }

    public void fill( @Nullable ProductPublishing publishing )
    {
        if ( publishing == null )
        {
            editor.clear();
        }
        else
        {
            editor.setHTML( publishing.getDescription() );
        }
    }

    interface ContentUiBinder
            extends UiBinder<HTMLPanel, Content>
    {
    }
}
