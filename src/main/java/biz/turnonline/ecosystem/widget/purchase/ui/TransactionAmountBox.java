package biz.turnonline.ecosystem.widget.purchase.ui;

import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import com.google.gwt.dom.client.Document;
import gwt.material.design.client.base.AbstractValueWidget;
import gwt.material.design.client.base.HasLabel;
import gwt.material.design.client.constants.CssName;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.html.Label;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class TransactionAmountBox
        extends AbstractValueWidget<Transaction>
        implements HasLabel
{
    private Label label = new Label();

    private final MaterialContainer parent = new MaterialContainer();

    public TransactionAmountBox()
    {
        super( Document.get().createDivElement(), CssName.INPUT_FIELD );
        add(label);
        label.addStyleName(CssName.ACTIVE);

        setHeight( "3rem" );

        parent.setMarginTop( 5 );
    }

    @Override
    public void setValue( Transaction value )
    {
        ColumnTransactionAmount.renderAmount( parent, value );
        add( parent );
    }

    @Override
    public Transaction getValue()
    {
        return null;
    }

    @Override
    public void setLabel( String label )
    {
        this.label.setText(label);
    }

    @Override
    public String getLabel()
    {
        return label.getText();
    }
}
