package org.ctoolkit.turnonline.widget.product.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.addins.client.richeditor.MaterialRichEditor;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.Product;
import org.ctoolkit.turnonline.widget.shared.rest.productbilling.ProductPublishing;
import org.ctoolkit.turnonline.widget.shared.ui.HasModel;

import javax.inject.Inject;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Content
        extends Composite
        implements HasModel<Product>
{
    private static ContentUiBinder binder = GWT.create( ContentUiBinder.class );

    interface ContentUiBinder
            extends UiBinder<HTMLPanel, Content>
    {
    }

    @UiField
    MaterialRichEditor editor;

    @Inject
    public Content()
    {
        initWidget( binder.createAndBindUi( this ) );
    }

    @Override
    public void bind( Product product )
    {
        product.getPublishing().setDescription( editor.getHTML() );
    }

    @Override
    public void fill( Product product )
    {
        ProductPublishing publishing = getProductPublishing( product );
        editor.setHTML( publishing.getDescription() );
    }

    private ProductPublishing getProductPublishing( Product product )
    {
        ProductPublishing publishing = product.getPublishing();
        if ( publishing == null )
        {
            publishing = new ProductPublishing();
            product.setPublishing( publishing );
        }

        return publishing;
    }
}
