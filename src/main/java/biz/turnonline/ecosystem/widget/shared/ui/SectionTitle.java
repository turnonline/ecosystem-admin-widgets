package biz.turnonline.ecosystem.widget.shared.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialSection;
import gwt.material.design.client.ui.MaterialTitle;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SectionTitle
        extends Composite
{
    private static SectionTitle.SectionTitleUiBinder binder = GWT.create( SectionTitle.SectionTitleUiBinder.class );

    interface SectionTitleUiBinder
            extends UiBinder<MaterialSection, SectionTitle>
    {
    }

    @UiField
    MaterialIcon icon;

    @UiField
    MaterialTitle title;

    public SectionTitle()
    {
        initWidget( binder.createAndBindUi( this ) );

        title.getParagraph().setPadding( 0 );
    }

    public void setIconType( IconType icon )
    {
        this.icon.setIconType( icon );
    }

    public void setTitle( String title )
    {
        this.title.setDescription( title );
    }

    public void setIconMarginLeft( double marginLeft )
    {
        icon.setMarginLeft( marginLeft );
    }

    public void setTextColor( Color color ){
        this.icon.setIconColor( color );
        this.title.setTextColor( color );
    }
}
