package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.rest.billing.ProductPublishing;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextBox;

import javax.annotation.Nullable;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class Publishing
        extends Composite
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

    @UiField( provided = true )
    ProductPictureUploader uploader;

    public Publishing( EventBus eventBus )
    {
        uploader = new ProductPictureUploader( eventBus );

        initWidget( binder.createAndBindUi( this ) );

        atName.setReturnBlankAsNull( true );
        atUri.setReturnBlankAsNull( true );
    }

    public ProductPublishing bind( @Nullable ProductPublishing publishing )
    {
        if ( publishing == null )
        {
            publishing = new ProductPublishing();
        }

        if ( publishing.getAt() != null )
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
        return publishing;
    }

    public void fill( @Nullable ProductPublishing publishing )
    {
        if ( publishing == null )
        {
            publishing = new ProductPublishing();
        }

        atName.setValue( publishing.getAt() != null ? publishing.getAt().getName() : null );
        atUri.setValue( publishing.getAt() != null ? publishing.getAt().getUri() : null );

        comingSoon.setValue( publishing.getComingSoon() != null ? publishing.getComingSoon() : false );
        published.setValue( publishing.getPublished() != null ? publishing.getPublished() : false );
        facebookLike.setValue( publishing.getFacebookLike() != null ? publishing.getFacebookLike() : false );
        googlePlus.setValue( publishing.getGooglePlus() != null ? publishing.getGooglePlus() : false );
        linkedInShare.setValue( publishing.getLinkedInShare() != null ? publishing.getLinkedInShare() : false );
        uploader.fill( publishing );
    }

    interface PublishingUiBinder
            extends UiBinder<HTMLPanel, Publishing>
    {
    }
}
