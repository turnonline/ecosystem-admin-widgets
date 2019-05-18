/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package biz.turnonline.ecosystem.widget.invoice;

import biz.turnonline.ecosystem.widget.invoice.place.EditInvoice;
import biz.turnonline.ecosystem.widget.invoice.place.Invoices;
import biz.turnonline.ecosystem.widget.invoice.presenter.EditInvoicePresenter;
import biz.turnonline.ecosystem.widget.invoice.presenter.InvoicesPresenter;
import biz.turnonline.ecosystem.widget.shared.presenter.Presenter;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import javax.inject.Inject;
import java.util.HashMap;

/**
 * App controller maps place to a presenter.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class InvoiceController
        implements ActivityMapper
{
    private static HashMap<String, Presenter> presenters = null;

    @Inject
    public InvoiceController( InvoicesPresenter invoicesPresenter,
                              EditInvoicePresenter editInvoicePresenter )
    {
        if ( presenters == null )
        {
            presenters = new HashMap<>();

            presenters.put( Invoices.class.getName(), invoicesPresenter );
            presenters.put( EditInvoice.class.getName(), editInvoicePresenter );
        }
    }

    @Override
    public Activity getActivity( final Place place )
    {
        Presenter presenter = presenters.get( place.getClass().getName() );
        if ( presenter == null )
        {
            presenter = presenters.get( InvoiceEntryPoint.DEFAULT_PLACE.getClass().getName() );
        }

        return presenter;
    }
}
