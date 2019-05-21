package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.Product;
import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPublishing;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.inject.Inject;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Publishing
        extends Composite
        implements HasModel<Product>
{
    private static PublishingUiBinder binder = GWT.create( PublishingUiBinder.class );

    // -- domain

    @UiField
    MaterialTextBox atName;

    @UiField
    MaterialTextBox atUri;

    // -- settings

    @UiField
    MaterialSwitch comingSoon;

    @UiField
    MaterialSwitch published;

    @UiField
    MaterialSwitch facebookLike;

    @UiField
    MaterialSwitch googlePlus;

    @UiField
    MaterialSwitch linkedInShare;

    // -- pictures

    @UiField
    ProductPictureUploader uploader;

    @Inject
    public Publishing()
    {
        initWidget( binder.createAndBindUi( this ) );
    }

    @Override
    public void bind( Product product )
    {
        ProductPublishing publishing = product.getPublishing();

        if (publishing.getAt() != null)
        {
            publishing.getAt().setUri( atUri.getValue() );
            publishing.getAt().setName( atName.getValue() );
        }

        publishing.setComingSoon( comingSoon.getValue() );
        publishing.setPublished( published.getValue() );
        publishing.setFacebookLike( facebookLike.getValue() );
        publishing.setGooglePlus( googlePlus.getValue() );
        publishing.setLinkedInShare( linkedInShare.getValue() );

        uploader.bind( publishing );
    }

    @Override
    public void fill( Product product )
    {
        ProductPublishing publishing = getProductPublishing( product );

        atName.setValue( publishing.getAt() != null ? publishing.getAt().getName() : null);
        atUri.setValue( publishing.getAt() != null ? publishing.getAt().getUri() : null);

        comingSoon.setValue( publishing.getComingSoon() != null ? publishing.getComingSoon() : false );
        published.setValue( publishing.getPublished() != null ? publishing.getPublished() : false );
        facebookLike.setValue( publishing.getFacebookLike() != null ? publishing.getFacebookLike() : false );
        googlePlus.setValue( publishing.getGooglePlus() != null ? publishing.getGooglePlus() : false );
        linkedInShare.setValue( publishing.getLinkedInShare() != null ? publishing.getLinkedInShare() : false );
        uploader.fill( publishing );
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

    interface PublishingUiBinder
            extends UiBinder<HTMLPanel, Publishing>
    {
    }
}
