package biz.turnonline.ecosystem.widget.myaccount.ui;

import biz.turnonline.ecosystem.widget.myaccount.event.SaveWhyEvent;
import biz.turnonline.ecosystem.widget.shared.rest.account.Account;
import biz.turnonline.ecosystem.widget.shared.rest.account.AccountBusiness;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.EventBus;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.incubator.client.alert.Alert;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

import static biz.turnonline.ecosystem.widget.shared.Preconditions.checkNotNull;

/**
 * Panel that manages a descriptions that should convince an user to pin the store while shopping in the Marketplace mobile app
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class WhyPanel
        extends Composite
        implements TakesValue<Account>
{
    private final EventBus bus;

    private static WhyPanelUiBinder binder = GWT.create( WhyPanelUiBinder.class );

    @UiField
    MaterialTextArea descriptionSk;

    @UiField
    MaterialTextArea descriptionCs;

    @UiField
    MaterialTextArea descriptionEn;

    @UiField
    Alert info;

    private Account account;

    public WhyPanel( @Nonnull EventBus bus )
    {
        this.bus = checkNotNull( bus );

        initWidget( binder.createAndBindUi( this ) );

        initDescription( descriptionSk );
        initDescription( descriptionCs );
        initDescription( descriptionEn );
    }

    @Override
    public void setValue( Account account )
    {
        this.account = account;

        descriptionSk.setValue( null );
        descriptionCs.setValue( null );
        descriptionEn.setValue( null );

        if ( account.getBusiness() != null && account.getBusiness().getWhy() != null )
        {
            Map<String, String> why = account.getBusiness().getWhy();

            descriptionSk.setValue( why.get( "sk" ) );
            descriptionCs.setValue( why.get( "cs" ) );
            descriptionEn.setValue( why.get( "en" ) );
        }
    }

    @Override
    public Account getValue()
    {
        if ( isAtLeastOne() )
        {
            if ( account.getBusiness() == null )
            {
                account.setBusiness( new AccountBusiness() );
            }

            Map<String, String> why = new HashMap<>();

            bind( why, descriptionSk, "sk" );
            bind( why, descriptionCs, "cs" );
            bind( why, descriptionEn, "en" );

            account.getBusiness().setWhy( why );
        }

        return this.account;
    }

    @UiHandler( "btnSave" )
    public void onSaveClick( ClickEvent event )
    {
        bus.fireEvent( new SaveWhyEvent( getValue() ) );
    }

    @Override
    protected void onLoad()
    {
        super.onLoad();
        info.getElement().getStyle().setPosition( Style.Position.STATIC );
    }

    private void initDescription( MaterialTextArea description )
    {
        description.getValueBoxBase().getElement().setAttribute( "maxLength", "120" );
        description.setReturnBlankAsNull( true );
    }

    private void bind( Map<String, String> why, MaterialTextArea description, String language )
    {
        if ( description.getValue() != null )
        {
            why.put( language, description.getValue() );
        }
    }

    private boolean isAtLeastOne()
    {
        return descriptionSk.getValue() != null || descriptionCs.getValue() != null || descriptionEn.getValue() != null;
    }

    interface WhyPanelUiBinder
            extends UiBinder<HTMLPanel, WhyPanel>
    {
    }
}
