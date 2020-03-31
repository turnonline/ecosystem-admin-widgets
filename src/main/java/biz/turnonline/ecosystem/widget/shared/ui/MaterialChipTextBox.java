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

import biz.turnonline.ecosystem.widget.shared.event.ChipCloseEvent;
import biz.turnonline.ecosystem.widget.shared.event.ChipCloseEventHandler;
import biz.turnonline.ecosystem.widget.shared.event.ChipDoubleClickEvent;
import biz.turnonline.ecosystem.widget.shared.event.ChipDoubleClickEventHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialTextBox;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class MaterialChipTextBox
        extends MaterialTextBox
{
    private String letter = "?";

    public MaterialChipTextBox()
    {
        addKeyUpHandler( event -> {
            if ( event.getNativeKeyCode() == KeyCodes.KEY_ENTER || event.getNativeKeyCode() == KeyCodes.KEY_SPACE )
            {
                addChip( getText() );
            }
        } );
    }

    public List<String> getChippedValue()
    {
        List<String> values = new ArrayList<>();
        iterate( chip -> values.add( ( chip ).getText() ) );
        return values;
    }

    public void setChippedValue( List<String> value )
    {
        removeAllChips();

        if ( value != null )
        {
            value.forEach( this::addChip );
        }
        else
        {
            setText( null );
        }
    }

    public void addChip( String text )
    {
        if ( getChip( text ) != null )
        {
            return;
        }

        MaterialChip chip = new MaterialChip( text, IconType.CLOSE );
        chip.setMarginRight( 5 );
        chip.setMarginBottom( 5 );
        chip.setLetter( letter );
        chip.setLetterBackgroundColor( Color.BLUE );
        chip.setLetterColor( Color.WHITE );
        chip.addCloseHandler( event -> fireEvent( new ChipCloseEvent( chip.getText() ) ) );
        chip.addDoubleClickHandler( event -> fireEvent( new ChipDoubleClickEvent( chip.getText() ) ) );
        chip.getElement().setAttribute( "style", "margin: 0 5px 5px 0; cursor: default; user-select: none;" );

        add( chip );

        setText( "" );
    }

    public void removeChip( String text )
    {
        iterate( chip -> {
            if ( chip.getText().equals( text ) )
            {
                chip.removeFromParent();
            }
        } );
    }

    public void removeAllChips()
    {
        iterate( Widget::removeFromParent );
    }

    public MaterialChip getChip( String text )
    {
        for ( int i = 0; i < getWidgetCount(); i++ )
        {
            Widget widget = getWidget( i );
            if ( widget instanceof MaterialChip )
            {
                MaterialChip chip = ( MaterialChip ) widget;
                if ( chip.getText().equals( text ) )
                {
                    return chip;
                }
            }
        }

        return null;
    }

    public void iterate( Action action )
    {
        for ( int i = 0; i < getWidgetCount(); i++ )
        {
            Widget widget = getWidget( i );
            if ( widget instanceof MaterialChip )
            {
                action.doAction( ( MaterialChip ) widget );
            }
        }
    }

    public HandlerRegistration addChipCloseHandler( ChipCloseEventHandler handler )
    {
        return addHandler( handler, ChipCloseEvent.TYPE );
    }

    public HandlerRegistration addChipDoubleClickHandler( ChipDoubleClickEventHandler handler )
    {
        return addHandler( handler, ChipDoubleClickEvent.TYPE );
    }

    @Override
    public void setEnabled( boolean enabled )
    {
        super.setEnabled( enabled );
        iterate( chip -> {
            chip.setLetterBackgroundColor( enabled ? Color.BLUE : Color.GREY_DARKEN_3 );
            chip.getIcon().setVisible( enabled );
        } );
    }

    public void setLetter( String letter )
    {
        this.letter = letter;
    }

    @FunctionalInterface
    private interface Action
    {
        void doAction( MaterialChip chip );
    }
}
