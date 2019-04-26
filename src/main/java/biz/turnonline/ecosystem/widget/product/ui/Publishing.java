package biz.turnonline.ecosystem.widget.product.ui;

import biz.turnonline.ecosystem.widget.shared.rest.productbilling.Product;
import biz.turnonline.ecosystem.widget.shared.rest.productbilling.ProductPublishing;
import biz.turnonline.ecosystem.widget.shared.ui.HasModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.addins.client.fileuploader.base.UploadResponse;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextBox;
import org.ctoolkit.gwt.client.facade.UploadItemsResponse;
import org.fusesource.restygwt.client.ServiceRoots;

import javax.inject.Inject;

import static biz.turnonline.ecosystem.widget.shared.Configuration.PRODUCT_BILLING_API_ROOT;

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
    MaterialFileUploader uploader;

    @Inject
    public Publishing()
    {
        initWidget( binder.createAndBindUi( this ) );

        String apiUrl = ServiceRoots.get( PRODUCT_BILLING_API_ROOT );
        String uploadUrl = apiUrl + "storage-upload";
        uploader.setUrl( uploadUrl );
        GWT.log( "Upload Url: " + uploadUrl );

        uploader.addSuccessHandler( event -> {
            UploadResponse response = event.getResponse();
            if ( response.getCode() == 401 )
            {
                GWT.log( "Unauthorized" );
                return;
            }

            if ( response.getCode() != 201 )
            {
                GWT.log( "Response code: " + response.getCode() );
                return;
            }

            UploadItemsResponse json = JsonUtils.safeEval( response.getBody() );
            if ( json.getItems().length() > 0 )
            {
                String servingUrl = json.getItems().get( 0 ).getServingUrl();
                GWT.log( "Serving URL: " + servingUrl );
            }
        } );
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
