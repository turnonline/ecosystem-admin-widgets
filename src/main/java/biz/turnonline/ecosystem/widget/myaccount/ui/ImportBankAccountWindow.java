/*
 *  Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package biz.turnonline.ecosystem.widget.myaccount.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Certificate;
import com.google.gwt.event.dom.client.KeyCodes;
import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ImportBankAccountWindow
        extends MaterialWindow
{
    private static final Color PRIMARY_COLOR = Color.LIGHT_BLUE;

    private static AppMessages messages = AppMessages.INSTANCE;

    private MaterialButton btnOk;

    private MaterialButton btnClose;

    private MaterialRadioButton choiceRevolut;

    private MaterialTextBox revolutClientId;

    public ImportBankAccountWindow()
    {
        super( messages.labelImportBankAccount() );

        MaterialRow panel = new MaterialRow();
        panel.setSeparator( true );
        panel.setPaddingBottom( 20 );
        panel.add( this::createContent );
        add( panel );

        add( btnOk = newBtnOk() );
        add( btnClose = newBtnClose() );

        setToolbarColor( PRIMARY_COLOR );
        setPadding( 20 );
        setCloseAnimation( new MaterialAnimation().transition( Transition.BOUNCEOUTUP ) );
        addOpenHandler( event -> btnClose.setFocus( true ) );
        addKeyUpHandler( event -> {
            if ( event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE )
            {
                close();
            }
        } );

    }

    public MaterialButton getBtnOk()
    {
        return btnOk;
    }

    public MaterialButton getBtnClose()
    {
        return btnClose;
    }

    public ImportBankAccount getValue()
    {
        ImportBankAccount model = new ImportBankAccount();

        if ( choiceRevolut.getValue() )
        {
            model.setBankCode( "REVO" );
            model.setBankAccountName( "Revolut" );

            Certificate certificate = new Certificate();
            certificate.setClientId( revolutClientId.getValue() );
            model.setCertificate( certificate );
        }

        return model;
    }

    protected MaterialButton newBtnOk()
    {
        MaterialButton btn = new MaterialButton( messages.labelOk(), IconType.CHECK, ButtonType.RAISED );
        btn.setWaves( WavesType.DEFAULT );
        btn.setBackgroundColor( PRIMARY_COLOR );
        btn.setMarginRight( 8 );
        btn.addClickHandler( event -> ImportBankAccountWindow.this.close() );

        return btn;
    }

    protected MaterialButton newBtnClose()
    {
        MaterialButton btn = new MaterialButton( messages.labelCancel(), IconType.CLOSE, ButtonType.FLAT );
        btn.setWaves( WavesType.DEFAULT );
        btn.addClickHandler( event -> ImportBankAccountWindow.this.close() );

        return btn;
    }

    private MaterialRow createContent()
    {
        MaterialRow root = new MaterialRow();
        root.setMarginBottom( 0 );
        root.setPaddingLeft( 0 );
        root.setPaddingRight( 0 );
        root.setGrid( "s12 m12" );

        MaterialColumn choices = new MaterialColumn();
        choices.setGrid( "s12 m12" );
        choices.setMarginBottom( 20 );
        choices.setMarginLeft( -7 );
        root.add( choices );

        choiceRevolut = new MaterialRadioButton();
        choiceRevolut.setText( "Revolut" );
        choiceRevolut.setValue( true );
        choiceRevolut.setName( "bankAccountImportChoice" );
        choices.add( choiceRevolut );

        MaterialColumn content = new MaterialColumn();
        content.setGrid( "s12 m6" );
        root.add( content );

        revolutClientId = new MaterialTextBox();
        revolutClientId.setLabel( messages.labelClientId() );
        revolutClientId.setHelperText( messages.tooltipRevolutClientIdTooltip() );
        content.add( revolutClientId );

        return root;
    }
}
