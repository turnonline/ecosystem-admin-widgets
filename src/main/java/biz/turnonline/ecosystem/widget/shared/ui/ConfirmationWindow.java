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

package biz.turnonline.ecosystem.widget.shared.ui;

import biz.turnonline.ecosystem.widget.shared.AppMessages;
import com.google.gwt.event.dom.client.KeyCodes;
import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.HeadingSize;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.client.ui.html.Heading;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class ConfirmationWindow
        extends MaterialWindow
{
    private static final Color PRIMARY_COLOR = Color.LIGHT_GREEN;

    private static AppMessages messages = AppMessages.INSTANCE;

    private Heading question = new Heading( HeadingSize.H6 );

    private MaterialIcon icon;

    private MaterialButton btnOk;

    private MaterialButton btnClose;

    public ConfirmationWindow()
    {
        super( messages.labelConfirmation() );

        icon = new MaterialIcon( IconType.HELP );
        icon.setGrid( "s2 m2" );
        icon.setPadding( 0 );
        icon.setIconColor( PRIMARY_COLOR );
        icon.setIconSize( IconSize.MEDIUM );

        question.getElement().setAttribute( "style", "display:inline;padding: 20px 0 10px 5px;top: -5px;position: relative;" );
        question.setGrid( "s8 m8" );

        MaterialRow panel = new MaterialRow();
        panel.setSeparator( true );
        panel.add( icon );
        panel.add( question );
        panel.setPaddingBottom( 20 );
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

    public void open( String text )
    {
        this.question.setText( text );
        open();
    }

    public void open( Question question )
    {
        String text = question.selectedRecords() > 1 ? question.msgMultipleRecords() : question.msgOneRecord();
        this.question.setText( text );
        open();
    }

    public MaterialButton getBtnOk()
    {
        return btnOk;
    }

    public MaterialButton getBtnClose()
    {
        return btnClose;
    }

    public void setIconType( IconType iconType )
    {
        icon.setIconType( iconType );
    }

    protected MaterialButton newBtnOk()
    {
        MaterialButton btn = new MaterialButton( messages.labelOk(), IconType.CHECK, ButtonType.RAISED );
        btn.setWaves( WavesType.DEFAULT );
        btn.setBackgroundColor( PRIMARY_COLOR );
        btn.setMarginRight( 8 );
        btn.addClickHandler( event -> ConfirmationWindow.this.close() );

        return btn;
    }

    protected MaterialButton newBtnClose()
    {
        MaterialButton btn = new MaterialButton( messages.labelCancel(), IconType.CLOSE, ButtonType.FLAT );
        btn.setWaves( WavesType.DEFAULT );
        btn.addClickHandler( event -> ConfirmationWindow.this.close() );

        return btn;
    }

    public interface Question
    {
        int selectedRecords();

        default String name()
        {
            return null;
        }

        default String msgOneRecord()
        {
            return messages.msgConfirmOneRecordDelete( name() );
        }

        default String msgMultipleRecords()
        {
            return messages.msgConfirmMultipleRecordsDelete( selectedRecords() );
        }
    }
}
